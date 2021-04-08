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
		try {
			Connection conn = getConnexion();
			PreparedStatement st = conn.prepareStatement("SELECT * from Paragraph where titleStory = ? " + 
				"and idParagraph = ? ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			ResultSet res = st.executeQuery();
			if (!res.next()) {
				throw new DAOException("Erreur : paragraphe introuvable");
			}
			
			Paragraphe paragraph = new Paragraphe();
			paragraph.setIdParagraph(idPar);
			paragraph.setStory(titleStory);
			paragraph.setAuthor(res.getString("author"));
			paragraph.setText(res.getString("text"));
			paragraph.setIsValidated(res.getInt("validated") == 1);
			/* See if there is a next paragraph */
			st = conn.prepareStatement("SELECT idNextPar from BodyParagraph where titleStory = ? and titleNext = ? and idParagraph = ?");
			st.setString(1, titleStory);
			st.setString(2, titleStory);
			st.setInt(3, idPar);
			/* The result can be null */
			res = st.executeQuery();
			int next = res.getInt("idNextPar");
			paragraph.setNextParagraph((res.wasNull()? null : next));
			/* retrieve the choices */
			st = conn.prepareStatement("SELECT * from Choice where prevParStory = ? and prevPar = ? ");
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
		try {
			Connection conn = getConnexion();
			PreparedStatement st = conn.prepareStatement("INSERT INTO Paragraphe(titleStory, idParagraph, author, text, validated)" + 
					"values(?, seqPar.nextval, ?, ?, ?); SELECT seqPar.currval as idPar from dual; ");
			st.setString(1, story);
			st.setString(2, author);
			st.setString(3, text);
			st.setInt(4, (validated)? 1:0);
			ResultSet res = st.executeQuery();
			res.next();
			int idPar = res.getInt("idPar");
			/* if it's not a conclusion : add it to bodyParagraph */
			if (!isConclusion) {
				st = conn.prepareStatement("INSERT INTO BodyParagraph(titleStory, idParagraph) values(? , ?) ");
				st.setString(1, story);
				st.setInt(2, idPar);
			}
			return idPar; 
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}
	}
	
	
	public void modifyText(String titleStory, int idPar, String newText) {
		try {
			Connection conn = getConnexion();
			PreparedStatement st = conn.prepareStatement("UPDATE Paragraph set text = ? where titleStory = ? and idParagraph = ?  ");
			st.setString(1, newText);
			st.setString(2, titleStory);
			st.setInt(3, idPar);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}
	}
	
	
	public void validateParagraphe(String titleStory, int idPar) {
		
		try {
			Connection conn = getConnexion();
			PreparedStatement st = conn.prepareStatement("UPDATE Paragraph set validated = 1 where titleStory = ? and idParagraph = ?  ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			st.executeUpdate();
			
			/* Validate the corresponding choice also */
			st= conn.prepareStatement("UPDATE Choice set locked = 2 where assocStory = ? and assocPar = ? ");
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			st.executeUpdate();
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}
	}
	
	public void associateNextParagraph(String titleStory, int idPar, String titleNext, int idParNext) {
		try {
			/* Must verify that the paragraph is in body paragraphs table otherwise add it */
			Connection conn = getConnexion();
			PreparedStatement st = conn.prepareStatement("SELECT * from BodyParagraph where titleStory = ? and idParagraph = ?"); 
			st.setString(1, titleStory);
			st.setInt(2, idPar);
			ResultSet res = st.executeQuery();
			String statement; 
			if (res.next()) {
				statement = "UPDATE BodyParagraph set titleNext = ? and idNextPar = ? where titleStory = ? and idParagraph = ? ";
			}
			else {
				statement = "Insert into BodyParagraph(titleNext, idNextPar, titleStory, idParagraph) values(?, ?, ?, ?)";	
			}
			st = conn.prepareStatement(statement);
			st.setString(1, titleNext);
			st.setInt(2, idParNext);
			st.setString(3, titleStory);
			st.setInt(4, idPar);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}
	}
	
	public void deleteParagraphe(String title, int idParagraph) {
		try {
			Connection conn = getConnexion();
			PreparedStatement st = conn.prepareStatement("DELETE from Paragraph where titleStory = ? and idParagraph = ? ");
			st.setString(1, title);
			st.setInt(2, idParagraph);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		}
	}
}
