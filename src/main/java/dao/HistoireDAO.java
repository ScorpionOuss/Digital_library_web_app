package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.DataSource;

import beans.Histoire;
import beans.Utilisateur;

public class HistoireDAO extends AbstractDAO {
	 
	public HistoireDAO(DataSource dataSource) {
		super(dataSource);
	}

	public Histoire getHistoire(String title) throws DAOException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * from Story where title = ? ");
			st.setString(1, title);
			res = st.executeQuery();
            if (! res.next()) {
            	throw new DAOException("Histoire introuvable"); 
            }
            Histoire histoire = new Histoire();
            histoire.setCreator(res.getString("Creator"));
            histoire.setTitle(title);
            histoire.setDescription(res.getString("Description"));
            histoire.setImage(res.getString("Image"));
            histoire.setPublicEc(res.getInt("publicEc") == 1);
            histoire.setPublicLec(res.getInt("publicLec") == 1);
            histoire.setFirstParagraph(res.getInt("firstParagraph"));
            return histoire;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * returns the id of the paragraph that must be modified 
	 * @param title
	 * @param creator
	 * @param publicLec
	 * @param publicEc
	 * @param image
	 * @param description
	 * @return
	 */
	public int addHistoire(String title, String creator,boolean publicLec, boolean publicEc, String image, String description){
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try{
			conn = getConnexion();
			/* Get the id */
			st = conn.prepareStatement("SELECT seqPar.nextval as idPar from dual");
			res = st.executeQuery();
			res.next();
			int idPar = res.getInt("idPar");
			ResClose.silencedClosing(res, st);
			/* create the story */
			st = conn.prepareStatement("INSERT INTO STORY(title, creator, publicLec, publicEc, firstParagraph, image, description)" + 
					"values(?, ?, ?, ?, ?, ?, ?)");
			st.setString(1, title);
			st.setString(2, creator);
			int lec = (publicLec) ? 1: 0;
			st.setInt(3, lec);
			int ec = (publicEc) ? 1 : 0; 
			st.setInt(4, ec);
			st.setInt(5, idPar);
			st.setString(6, image);
			st.setString(7, description);
			st.executeUpdate();
			/* Create the first paragraph */
			st = conn.prepareStatement("INSERT INTO PARAGRAPH(titleStory, idParagraph, author, validated) " + 
					"values(?, ?, ?, 1)");
			st.setString(1, title);
			st.setInt(2, idPar);
			st.setString(3, creator);
			st.executeUpdate();
			return idPar; 
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * a story is available for read if there is a conclusion 
	 * @param title
	 * @return
	 */
	private boolean availableForRead(String title, Connection conn, PreparedStatement st, ResultSet res) {
		try {
			st = conn.prepareStatement("SELECT * from Paragraph where titleStory = ? " + 
				"and idParagraph NOT IN (SELECT idParagraph from BodyParagraph where titleStory = ?)");
			st.setString(1, title);
			st.setString(2, title);
			res = st.executeQuery();
			return res.next();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}
	}
	
	/** 
	 * This must not be called before verifying if the story can be displayed or not (there is definitely a conclusion) 
	 * @param title
	 * @param path : to store the IDs of the paragraphs leading to the conclusion if there is only one path
	 * @return
	 */
	public boolean uniqueDisplay(String title, LinkedList<Integer> path) {
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try{
			conn = getConnexion();
			/* search if there is more that one conclusion */
			st = conn.prepareStatement("SELECT COUNT(*) AS ConcNb from Paragraph where titleStory = ? " + 
					"and idParagraph NOT IN (SELECT idParagraph from BodyParagraph where titleStory = ?)");
			st.setString(1, title);
			st.setString(2, title);
			res = st.executeQuery();
			res.next();
			if (res.getInt("ConcNb") > 1) { /* More than one conclusion */
				return false; /* mean normal display */
			}
			/* Close : for warning :( ? */
			ResClose.silencedClosing(res, st);
			/* Else we must verify if the unique existing conclusion can have more than one path, in case of no 
			 * 	search the path and store it 
			 */
			/* retrieve the ID of the first paragraph for the loop */
			st = conn.prepareStatement("SELECT firstParagraph from Story where title = ?");
			st.setString(1, title);
			res = st.executeQuery();
			res.next();
			int stopWhen = res.getInt("firstParagraph");
			/* first : retrieve the conclusion */
			st = conn.prepareStatement("SELECT idParagraph from Paragraph where titleStory = ?"
					+ "and idParagraph NOT IN (SELECT idParagraph from BodyParagraph where titleStory = ?)");
			st.setString(1, title);
			st.setString(2, title);
			res = st.executeQuery();
			res.next();
			int idLastPar = res.getInt("idParagraph");
			while (idLastPar != stopWhen) {
				/* add it to the linkedList */
				path.addFirst(idLastPar);
				/* Verify if it is a next paragraph */
				st = conn.prepareStatement("SELECT idParagraph from BodyParagraph where titleNext = ? and titleStory = ? and idNextPar = ? ");
				st.setString(1, title);
				st.setString(2,  title);
				st.setInt(3, idLastPar);
				res = st.executeQuery();
				if (res.next()) { /* if it is a next paragraph : the path is always unique */
					idLastPar = res.getInt("idParagraph");
					continue;
				}
				/* Close : for warning :( ? */
				ResClose.silencedClosing(res, st);
				
				/* search the number of the choices associated */
				st = conn.prepareStatement("SELECT COUNT(*) as NbChoices from Choice where assocStory = ? and assocPar = ? ");
				st.setString(1, title);
				st.setInt(2, idLastPar);
				res = st.executeQuery();
				res.next();
				int nbAssoChoices = res.getInt("NbChoices");
				if (nbAssoChoices > 1) {  /* There is definitely a choice */
					return false; 
				}
				/* one choice then : must retrieve the previous paragraph */
				st = conn.prepareStatement("SELECT prevPar from Choice where assocStory = ? and assocPar = ? ");
				st.setString(1, title);
				st.setInt(2, idLastPar);
				res = st.executeQuery();
				res.next();
				idLastPar = res.getInt("prevPar");
			}
			path.addFirst(stopWhen);
			return true;
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * to publish a story (reading mode) : change the corresponding boolean in the database
	 * @param title
	 */
	public void publish_story(String title) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("UPDATE Story set publicEc = 1 where title = ? ");
			st.setString(1, title);
			st.executeQuery();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
	
	/**
	 * to unpublish a story (reading mode)  : change the corresponding boolean in the database 
	 * @param title
	 */
	public void unpublish_story(String title) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("UPDATE Story set publicEc = 0 where title = ? ");
			st.setString(1, title);
			st.executeQuery();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
		ResClose.silencedClosing(st, conn);
		}
	}

	public LinkedList<String> listofAuthors(String story){
		LinkedList<String> authors = new LinkedList<String>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT DISTINCT author from Paragraph where titleStory = ?");
			st.setString(1, story);
			res = st.executeQuery();
			while(res.next()) {
				authors.add(res.getString("author"));
			}
			return authors; 
		} catch (SQLException e){
				throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
	
	public LinkedList<Histoire> listOfStoriesToEdit(Utilisateur user){
		String editor = user.getUserName();
		LinkedList<Histoire> stories = new LinkedList<Histoire>();
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("select * from story where publicEc = 1 or creator = ? UNION " + 
					"select distinct S.* from story S join invited I on S.title = I.titleStory where invitedUser = ?");
			st.setString(1, editor);
			st.setString(2, editor);
			res = st.executeQuery();
			while (res.next()) {
				Histoire histoire = new Histoire();
				histoire.setCreator(res.getString("Creator"));
				histoire.setTitle(res.getString("title"));
				histoire.setDescription(res.getString("Description"));
				histoire.setImage(res.getString("Image"));
				histoire.setPublicEc(res.getInt("publicLec") == 1);
				histoire.setPublicLec(res.getInt("PublicLec") == 1);
				histoire.setFirstParagraph(res.getInt("firstParagraph"));
				stories.add(histoire);
			}
			return stories;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	public LinkedList<Histoire> listOfStoriesToRead(boolean isConnected){
		LinkedList<Histoire> stories = new LinkedList<Histoire>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * from Story");
			res = st.executeQuery();
			while (res.next()) {
				String title = res.getString("title");
				if (availableForRead(title, conn, st, res)) {
					boolean publicLec = (res.getInt("publicLec") == 1);
					/* if it is public or the user is connected */
					if (publicLec || isConnected) {
						Histoire histoire = new Histoire();
						histoire.setCreator(res.getString("Creator"));
						histoire.setTitle(title);
						histoire.setDescription(res.getString("Description"));
						histoire.setImage(res.getString("Image"));
						histoire.setPublicEc(res.getInt("publicEc") == 1);
						histoire.setPublicLec(publicLec);
						histoire.setFirstParagraph(res.getInt("firstParagraph"));
						stories.add(histoire);
					}
				}	
			}
			return stories;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}

	public void addInvited(String story, String userInvited) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("INSERT INTO Invited(titleStory, invitedUser) values(?, ?)");
			st.setString(1, story);
			st.setString(2, userInvited);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}

	/* Return the list of invited people to edit story */
	public LinkedList<String> getInvited(String story){
		LinkedList<String> invited = new LinkedList<String>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT invitedUser from INVITED where titleStory = ? ");
			st.setString(1, story);
			res = st.executeQuery();
			while (res.next()) {
				invited.add(res.getString("invitedUser"));
			}
			return invited; 
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * 
	 * @param title
	 * @return
	 */
	public boolean verifyTitle(String title) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("select distinct TITLE from story");
			res = st.executeQuery();
			while(res.next()){
				if(title.contentEquals(res.getString("title"))) {
					return false;
				}
			}
		} catch(SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
		return true;
	}
}

