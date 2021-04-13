package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;

import beans.Choix;
import beans.Paragraphe;

public class ChoixDAO extends AbstractDAO {

	public ChoixDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	public Choix getChoice(int idChoice) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * from Choice where idChoice = ? ");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			res.next();
			Choix choice = new Choix();
			choice.setIdChoice(idChoice);
			choice.setLocked(res.getInt("locked")); /* definitely 0/1/2 */
			choice.setText(res.getString("text"));
			return choice; 
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * the id returned must be stored in the corresponding paragraph list of choices 
	 * @param story
	 * @param idParagraph
	 * @param text
	 * @return
	 */
	public int addChoice(String story, int idParagraph, String text) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("INSERT INTO Choice(idChoice, prevParStory, prevPar, text)" + 
					"values(seqChoice.nextval, ?, ?, ?); SELECT seqChoice.currval as idChoice from dual; ");
			st.setString(1, story);
			st.setInt(2, idParagraph);
			st.setString(3, text);
			res = st.executeQuery();
			res.next();
			int idChoice = res.getInt("idChoice");
			return idChoice;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * see if the choice must not be displayed in reading mode
	 * @param idChoice
	 * @return
	 */
	public boolean isMasked(int idChoice) {
		/* search for the associated paragraph : if it's conclusion that return false 
		 * otherwise search for the next choices and evaluate if they are also masked or not 
		 */
		Map<Integer, Boolean> memoizeMap = new HashMap<Integer, Boolean>();
		return isMaskedRecur(idChoice, memoizeMap);
	}
	
	private boolean isMaskedRecur(int idChoice, Map<Integer, Boolean> memoizeMap) {
		/* search if the answer is in the map */
		if (memoizeMap.containsKey(idChoice)) {
			return memoizeMap.get(idChoice);
		}
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		try {
			conn = getConnexion();
			/* See if the choice has an associate paragraph and after see if it is a conclusion 
			 * We can not use a join because the associated paragraph can be null : so we must eliminate this case*/
			st = conn.prepareStatement("SELECT assocStory as story, assocPar as idPar from Choice where idChoice = ?");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			/* the result is either a row with null values or not null values */
			res.next();
			int idPar = res.getInt("idPar");
			if (res.wasNull()) { /* The choice is not associated to any paragraph : so masked */
				memoizeMap.put(idChoice, true);
				return true; 
			}
			String story = res.getString("story");
			/* To suppress the warnings */
			ResClose.silencedClosing(res, st);
			/* And now search for the next choices */ 
			/* the getNextChoices anlyzes also the case of a conclusion */
			LinkedList<Integer> nextChoices = getNextChoices(story, idPar, conn);
			/* if the list is empty : means that : either the paragraph is a conclusion or that all the way to 
			 * the conclusion there is only next paragraphs : the last paragraph is necessarly a conclusion 
			 * because of the condition : a non conclusion paragraph has a least on choice*/
			if (nextChoices.size() == 0) {
				memoizeMap.put(idChoice, false);
				return false;
			}
			
			boolean isMasked = true; 
			for (int idC : nextChoices) {
				isMasked = isMasked && isMaskedRecur(idC, memoizeMap);
			}
			memoizeMap.put(idChoice, isMasked);
			return isMasked; 
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * The purpose from this method is to facilitate the recursion on the choices, it is used to ignore
	 * the paragraphs that have no next choices but a next paragraph 
	 * @param story
	 * @param idPar
	 * @return
	 */
	private LinkedList<Integer> getNextChoices(String story, int idPar, Connection conn){
		PreparedStatement st = null; 
		ResultSet res = null ;
		try {
			st = conn.prepareStatement("SELECT titleNext, idNextPar from BodyParagraph where " + 
					"titleStory = ? and idParagraph = ? ");
			st.setString(1, story);
			st.setInt(2, idPar);
			res = st.executeQuery();
			/* if it's a conclusion */
			if (!res.next()) { /* return an empty list */
				return new LinkedList<Integer>();
			}
			/* if there is a next paragraph */
			int nextPar = res.getInt("idNextPar");
			if (!res.wasNull()) {
				return getNextChoices(res.getString("titleNext"), nextPar, conn);
			}
			ResClose.silencedClosing(res, st);
			/* search if there is choices */
			st = conn.prepareStatement("SELECT idChoice from Choice where prevParStory = ? and prevPar = ?");
			st.setString(1, story);
			st.setInt(2, idPar);
			res = st.executeQuery();
			LinkedList<Integer> listOfChoices = new LinkedList<Integer>();
			while (res.next()) {
				listOfChoices.add(res.getInt("idChoice"));
			}
			return listOfChoices; 
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}

	public Paragraphe retreiveCorrPar(int idChoice) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT assocStory, assocPar from Choice where idChoice = ? ");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			res.next(); /* definitely there is a paragraph */
			String story = res.getString("assocStory");
			int idPar = res.getInt("assocPar");
			ParagrapheDAO assoParDAO = new ParagrapheDAO(this.dataSource);
			return assoParDAO.getParagraphe(story, idPar);
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * will return the associated paragraph to update after with the appropriate text 
	 * @param idChoice
	 * @param lockedBy
	 * @return
	 */
	public int lockChoice(int idChoice, String lockedBy) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		/* modify lock and associate it with a paragraph */
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT prevParStory from Choice where idChoice = ?");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			String story = res.getString("prevParStory");
			
			ParagrapheDAO assocParDao = new ParagrapheDAO(this.dataSource);
			/* Create a paragraph with an empty text */
			int idPar = assocParDao.addParagraphe(story, "", false, lockedBy, true); 
			
			/* To suppress the warnings */
			ResClose.silencedClosing(res, st);
			st = conn.prepareStatement("UPDATE Choice set locked = 1 and assocPar = ? and assocStory = ? where idChoice = ?");
			st.setInt(1, idPar);
			st.setString(2, story);
			st.setInt(3, idChoice);
			st.executeUpdate();
			return idPar;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	public void unlockChoice(int idChoice) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		/* change locked and set the associated paragraph to Null  : delete the paragraph */
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT assocStory, assocPar from Choice where idChoice = ? ;" + 
					"UPDATE Choice set locked = 0 and assocPar = NULL and assocStory = NULL where idChoice = ?");
			st.setInt(1, idChoice);
			st.setInt(2, idChoice);
			res = st.executeQuery();
			ParagrapheDAO assocParDao = new ParagrapheDAO(this.dataSource);
			assocParDao.deleteParagraphe(res.getString("assocStory"), res.getInt("assocPar"));
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
}
