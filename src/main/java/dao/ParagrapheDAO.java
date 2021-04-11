package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.DataSource;

import beans.Choix;
import beans.Paragraphe;

public class ParagrapheDAO extends AbstractDAO {

	public ParagrapheDAO(DataSource dataSource) {
		super(dataSource);
	}
	
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
			/* See if there is a next paragraph */
			st = conn.prepareStatement("SELECT idNextPar from BodyParagraph where titleStory = ? and titleNext = ? and idParagraph = ?");
			st.setString(1, titleStory);
			st.setString(2, titleStory);
			st.setInt(3, idPar);
			/* The result can be null */
			res = st.executeQuery();
			if (res.next()) {
				int next = res.getInt("idNextPar");
				paragraph.setNextParagraph((res.wasNull()? null : next));
				return paragraph; /* There is no choices : exit */
			}
			/* retrieve the choices */
			st = conn.prepareStatement("SELECT * from Choice where prevParStory = ? and prevPar = ? ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			res = st.executeQuery();
			LinkedList<Choix> choices = new LinkedList<Choix>();
			while (res.next()) {
				Choix choice = new Choix();
				choice.setIdChoice(res.getInt("idChoice"));
				choice.setLocked(res.getInt("locked") == 1);
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
	 * return the id of the paragraph created in order to be used after for associating a next paragraph or choices 
	 * @param story
	 * @param text
	 * @param validated
	 * @param author
	 * @return
	 */
	public int addParagraphe(String story, String text, boolean validated, String author, boolean isConclusion) {
		Connection conn = null;
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("INSERT INTO Paragraphe(titleStory, idParagraph, author, text, validated)" + 
					"values(?, seqPar.nextval, ?, ?, ?); SELECT seqPar.currval as idPar from dual; ");
			st.setString(1, story);
			st.setString(2, author);
			st.setString(3, text);
			st.setInt(4, (validated)? 1:0);
			res = st.executeQuery();
			res.next();
			int idPar = res.getInt("idPar");
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
	
	public void associateNextParagraph(String titleStory, int idPar, String titleNext, int idParNext) {
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
			String statement; 
			if (res.next()) {
				statement = "UPDATE BodyParagraph set titleNext = ? and idNextPar = ? where titleStory = ? and idParagraph = ? ";
			}
			else {
				statement = "Insert into BodyParagraph(titleNext, idNextPar, titleStory, idParagraph) values(?, ?, ?, ?)";	
			}
			/* To suppress the warnings */
			ResClose.silencedClosing(st);
			st = conn.prepareStatement(statement);
			st.setString(1, titleNext);
			st.setInt(2, idParNext);
			st.setString(3, titleStory);
			st.setInt(4, idPar);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	public void deleteParagraphe(String title, int idParagraph) {
		Connection conn = null;
		PreparedStatement st = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("DELETE from Paragraph where titleStory = ? and idParagraph = ? ");
			st.setString(1, title);
			st.setInt(2, idParagraph);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}

	/* For the unique display */
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
}
