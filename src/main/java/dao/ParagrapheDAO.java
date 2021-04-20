package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.DataSource;

import beans.Choix;
import beans.Paragraphe;

/**
 * This is a DAO class that interacts with the data base concerning the paragraphs treatments
 * @author mounsit kaddami yan perez 
 *
 */
public class ParagrapheDAO extends AbstractDAO {

	public ParagrapheDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	/**
	 * this method allows to get all the information stored in the data base about a paragraph identified
	 * by the given parameters
	 * @param titleStory
	 * @param idPar
	 * @return
	 */
	public Paragraphe getParagraphe(String titleStory, int idPar) {
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * from Paragraph where titleStory = ? " + 
				"and idParagraph = ? ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			res = st.executeQuery();
			if (!res.next()) {
				throw new DAOException("Erreur : paragraphe introuvable");
			}
			Paragraphe paragraph = new Paragraphe();
			paragraph.setIdParagraph(idPar);
			paragraph.setStory(titleStory);
			paragraph.setAuthor(res.getString("author"));
			paragraph.setText(res.getString("text"));
			paragraph.setIsValidated(res.getInt("validated") == 1);
			/* Close */
			ResClose.silencedClosing(res, st);
			/* retrieve the choices */
			st = conn.prepareStatement("SELECT * from Choice where prevParStory = ? and prevPar = ? ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			res = st.executeQuery();
			LinkedList<Choix> choices = new LinkedList<Choix>();
			while (res.next()) {
				Choix choice = new Choix();
				choice.setIdChoice(res.getInt("idChoice"));
				choice.setLocked(res.getInt("locked"));
				choice.setText(res.getString("text"));
				choices.add(choice);
			}
			paragraph.setChoices(choices);
			return paragraph;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}  finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}

	/**
	 * allows to add a paragraph in the data base 
	 * @param p
	 * @param validated
	 * @param isConclusion
	 * @return the id (integer) of the paragraph which is created
	 */
	public int addParagraphe(Paragraphe p, boolean validated, boolean isConclusion) {
		return addParagraphe(p.getStory(), p.getText(), validated, p.getAuthor(),isConclusion);
	}
	
	/**
	 * return the id of the paragraph created in order to be used after for associating a next paragraph or choices 
	 * @param story
	 * @param text
	 * @param validated
	 * @param author
	 * @return the id (integer) of the paragraph which is created 
	 */
	public int addParagraphe(String story, String text, boolean validated, String author, boolean isConclusion) {
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			conn = getConnexion();
			/* Get the id */
			st = conn.prepareStatement("SELECT seqPar.nextval as id from dual");
			res = st.executeQuery();
			res.next();
			int idPar = res.getInt("id");
			ResClose.silencedClosing(res, st);
			st = conn.prepareStatement("INSERT INTO Paragraph(titleStory, idParagraph, author, text, validated)" + 
					"values(?, ?, ?, ?, ?)");
			st.setString(1, story);
			st.setInt(2, idPar);
			st.setString(3, author);
			st.setString(4, text);
			st.setInt(5, (validated)? 1:0);
			st.executeUpdate();
			/* Close : warning ? */
			ResClose.silencedClosing(st);
			/* if it's not a conclusion : add it to bodyParagraph */
			if (!isConclusion) {
				st = conn.prepareStatement("INSERT INTO BodyParagraph(titleStory, idParagraph) values(? , ?) ");
				st.setString(1, story);
				st.setInt(2, idPar);
			}
			return idPar; 
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}  finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	
	/** 
	 * modify the text of a paragraph identified by the given parameters 
	 * @param titleStory
	 * @param idPar
	 * @param newText
	 */
	public void modifyText(String titleStory, int idPar, String newText) {
		Connection conn = null;
		PreparedStatement st = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("UPDATE Paragraph set text = ? where titleStory = ? and idParagraph = ?  ");
			st.setString(1, newText);
			st.setString(2, titleStory);
			st.setInt(3, idPar);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
	
	
	/**
	 * this method allows to validate the paragraph in the data base also as the choice that leads to this paragraph
	 * @param titleStory
	 * @param idPar
	 */
	public void validateParagraphe(String titleStory, int idPar) {
		Connection conn = null;
		PreparedStatement st = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("UPDATE Paragraph set validated = 1 where titleStory = ? and idParagraph = ?  ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			st.executeUpdate();
			/* Just to suppress the warnings ????? */
			ResClose.silencedClosing(st);
			/* Validate the corresponding choice also */
			st= conn.prepareStatement("UPDATE Choice set locked = 2 where assocStory = ? and assocPar = ? ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			st.executeUpdate();
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
	
	/**
	 * this method allows to define a paragraph as a next for a given paragraph, it stores in fact the 
	 * next paragraph in the paragraph  as a choice with a default text 'Paragraphe suivante' 
	 * @param titleStory : defines the current paragraph
	 * @param idPar : defines the current paragraph
	 * @param titleNext : defines the paragraph which the user had chosen as a next for the paragraph he's writing 
	 * @param idParNext : defines the paragraph which the user had chosen as a next for the paragraph he's writing
	 * @return
	 */
	public int associateNextParagraph(String titleStory, int idPar, String titleNext, int idParNext) {
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			/* Must verify that the paragraph is in body paragraphs table otherwise add it */
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * from BodyParagraph where titleStory = ? and idParagraph = ?"); 
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			res = st.executeQuery();
			if (!res.next()) { /* If the paragraph is not yet a body paragraph */
				PreparedStatement st2;
				st2 = conn.prepareStatement("Insert into BodyParagraph(titleStory, idParagraph) values(?, ?)");
				st2.setString(1, titleStory);
				st2.setInt(2, idPar);
				st2.executeUpdate();
				ResClose.silencedClosing(st2);
			}
			/* To suppress the warnings */
			ResClose.silencedClosing(st);
			/* We'll insert the next paragraph as a choice */
			/* Create the choice */
			ChoixDAO choixDAO = new ChoixDAO(dataSource);
			int idChoice =  choixDAO.addChoice(titleStory, idPar, "Paragraphe suivante", titleNext, idParNext);
			return idChoice;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * delete the paragraph from the data base : the paragraph is deleted only if it has not any choice 
	 * that's written or in process of writing 
	 * @param title
	 * @param idParagraph
	 * @return boolean that indicates if the paragraph is deleted or not 
	 */
	public boolean deleteParagraphe(String title, int idParagraph) {
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			conn = getConnexion();
			/* Before deleting must verify that the paragraph is a conclusion or does not have any
			 * written choices */
			st = conn.prepareStatement("SELECT * from choice where prevPar = ? and prevParStory = ? and locked != 0");
			st.setInt(1, idParagraph);
			st.setString(2, title);
			res = st.executeQuery();
			/* if there is a row : means that we can not delete the paragraph */
			if (res.next()) {
				return false;
			}
			ResClose.silencedClosing(res, st);
			/* First of all : before deleting must dissociate it from the associated choice */
			st = conn.prepareStatement("UPDATE Choice set locked = 0 ,assocPar = NULL ,assocStory = NULL where " + 
					"assocStory = ? and assocPar = ? ");
			st.setString(1, title);
			st.setInt(2, idParagraph);
			st.executeUpdate();
			st = conn.prepareStatement("DELETE from Paragraph where titleStory = ? and idParagraph = ? ");
			st.setString(1, title);
			st.setInt(2, idParagraph);
			st.executeUpdate();
			return true; 
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}

	/**
	 * this method takes a list of paragraphs and groups there texts in one text and stores the names of all the authors 
	 * @param titleStory
	 * @param idPars : the list of the paragraphs to convert into on paragraph 
	 * @return an object of class Paragraphe that stores all the paragraphs texts and authors  
	 */
	public Paragraphe turnIntoOneParagraph(String titleStory, LinkedList<Integer> idPars) {
		Paragraphe assocPar = new Paragraphe();
		assocPar.setStory(titleStory);
		Paragraphe intermPar; 
		String newText; String newAuth; 
		for (int i : idPars) {
				intermPar = getParagraphe(titleStory, i);
				newText = (assocPar.getText()!=null) ? assocPar.getText() + "<br>" : "";
				newAuth = (assocPar.getAuthor()!=null)? assocPar.getAuthor() + " - " : "";
				assocPar.setText(newText + intermPar.getText());
				assocPar.setAuthor(newAuth+ intermPar.getAuthor());
		}
		return assocPar; 
	}
	
	/**
	 * allows to define a paragraph is the data base as a body paragraph 
	 * @param idParagraph
	 * @param title
	 */
	public void declareAsBodyParagraph(int idParagraph, String title) {
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * from BodyParagraph where idParagraph = ? and titleStory = ? ");
			st.setInt(1, idParagraph);
			st.setString(2, title);
			res = st.executeQuery();
			if (!res.next()) {
				ResClose.silencedClosing(st);
				st = conn.prepareStatement("INSERT INTO BodyParagraph(idParagraph, titleStory) values(?, ?)");
				st.setInt(1, idParagraph);
				st.setString(2, title);
				st.executeUpdate();
			}
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
	
	/**
	 * allows to retrieve all the paragraphs written by a given user 
	 * @param user
	 * @return list of the paragraphs written by a certain user 
	 */
	public LinkedList<Paragraphe> parWroteBy(String user){
		LinkedList<Paragraphe> paragraphs = new LinkedList<Paragraphe>();
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * from Paragraph where author = ? ");
			st.setString(1, user);
			res = st.executeQuery();
			while (res.next()) {
				Paragraphe p = new Paragraphe();
				p.setIdParagraph(res.getInt("idParagraph"));
				p.setStory(res.getString("titleStory"));
				p.setAuthor(user);
				p.setText(res.getString("text"));
				p.setIsValidated(res.getInt("validated") == 1);
				paragraphs.add(p);
			}
			return paragraphs;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
}
