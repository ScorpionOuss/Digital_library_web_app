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

/**
 * This is a DAO class that interacts with the data base concerning the choices treatments 
 * @author mounsit kaddami yan perez 
 *
 */
public class ChoixDAO extends AbstractDAO {

	public ChoixDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	/**
	 * this method allows to get all the information stored in the data base about a choice identified
	 * by an the integer given in parameters 
	 * @param idChoice : an integer that identifies the choice in the data base 
	 * @return Choix : an object that groups the informations found about the choice 
	 */
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
	 * This allows to add to the data base a choice that follows a certain paragraph
	 * the id returned must be stored in the corresponding paragraph list of choices 
	 * @param story :  the story to which the paragraph and the choice belongs 
	 * @param idParagraph : the id of the paragraph that precedes the choice
	 * @param text : the choice states 
	 * @return the id of the choice inserted in the data base 
	 */
	public int addChoice(String story, int idParagraph, String text) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		try {
			conn = getConnexion();
			/* Get the id */
			st = conn.prepareStatement("SELECT seqChoice.nextval as idChoice from dual");
			res = st.executeQuery();
			res.next();
			int idChoice = res.getInt("idChoice");
			ResClose.silencedClosing(res, st);
			st = conn.prepareStatement("INSERT INTO Choice(idChoice, prevParStory, prevPar, text)" + 
					"values(?, ?, ?, ?) ");
			st.setInt(1, idChoice);
			st.setString(2, story);
			st.setInt(3, idParagraph);
			st.setString(4, text);
			st.executeUpdate();
			return idChoice;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * This allows to add to the data base a choice that follows a certain paragraph
	 * @param story : the story to which the paragraph and the choice belongs 
	 * @param idParagraph: the id of the paragraph that precedes the choice
	 * @param text : the choice states
	 * @param assocStory : the story to which the paragraph that the choice leads to belongs
	 * @param assocPar : the paragraph to which the choice must lead 
	 * @return  the id of the choice inserted in the data base 
	 */
	public int addChoice(String story, int idParagraph, String text, String assocStory, int assocPar) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("INSERT INTO Choice(idChoice, prevParStory, prevPar, text, assocStory, assocPar) " + 
					"values(seqChoice.nextval, ?, ?, ?, ?, ?); SELECT seqChoice.currval as idChoice from dual; ");
			st.setString(1, story);
			st.setInt(2, idParagraph);
			st.setString(3, text);
			st.setString(4, assocStory);
			st.setInt(5, assocPar);
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
	 * it is about verifying if the choice leads to a conclusion or not 
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
	
	/**
	 * A recursive version that allows to implements a memoization algorithm to verify if a choice 
	 * must be hidden or not in reading mode 
	 * @param idChoice : the id of choice which must be verified 
	 * @param memoizeMap : a map that allows the implementation of the memoization algorithm
	 * @return
	 */
	// Just about the non validated paragraphs : we assume that these paragraphs either they have no choices or if they 
	// have it definitely they are not associated to a paragraph 
	// But if they don't have any choice : conclusion : Yeah : must verify that its validated 
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
			/* We'll just make sure that it's validated */
			st = conn.prepareStatement("SELECT validated from Paragraph where idParagraph = ? and titleStory = ?");
			st.setInt(1, idPar);
			st.setString(2, story);
			res = st.executeQuery();
			res.next();
			if (res.getInt("validated") == 0) { /* It's not validated */
				memoizeMap.put(idChoice, true);
				return true;
			}
			/* And now search for the next choices */ 
			/* the getNextChoices anlyzes also the case of a conclusion */
			LinkedList<Integer> nextChoices = getNextChoices(story, idPar, conn);
			/* if the list is empty : means that : either the paragraph is a conclusion */
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
	 * The purpose from this method is to get the next choices to analyze : it is destined to the recurision
	 * algorithm that verifies if a choice is hidden or not 
	 * @param story
	 * @param idPar
	 * @param conn: the connection object to the SQL data base 
	 * @return a list of the choices that follows the paragraph identified by the parameters values 
	 */
	private LinkedList<Integer> getNextChoices(String story, int idPar, Connection conn){
		PreparedStatement st = null; 
		ResultSet res = null ;
		try {
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

	/**
	 * this method allows to retrieve the data stored about the paragraph associated to a certain choice 
	 * identified by the given parameter 
	 * @param idChoice : the id of the choice 
	 * @return an object paragraphe that groups the data about the associated paragraph
	 */
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
	 * lock the choice in the data base and return the associated paragraph id to update after with the appropriate text 
	 * Or null if the user has no right to lock 
	 * @param idChoice
	 * @param lockedBy : the userName of the user who asked to lock the choice  
	 * @return
	 */
	public Integer lockChoice(int idChoice, String lockedBy) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		/* modify lock and associate it with a paragraph */
		try {
			conn = getConnexion();
			/* Verify if the user is locking another choice */
			st = conn.prepareStatement("SELECT idChoice, idParagraph from Choice JOIN Paragraph ON assocPar = idParagraph " + 
			" and assocStory = titleStory where locked =1 and author = ? ");
			st.setString(1, lockedBy);
			res = st.executeQuery();
			/* If the user is locking something */
			if (res.next()) {
				/* If we're asking to lock the same choice : is just o tolerance because of the servlet 
				 * implementation :) 
				 */
				if (idChoice == res.getInt("idChoice")) {
					return res.getInt("idParagraph");
				} else {
					return null; 
				}
			}
			
			ResClose.silencedClosing(res, st);
			st = conn.prepareStatement("SELECT prevParStory from Choice where idChoice = ?");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			res.next(); // definitely there is a row 
			String story = res.getString("prevParStory");
			
			ParagrapheDAO assocParDao = new ParagrapheDAO(this.dataSource);
			/* Create a paragraph with an empty text */
			int idPar = assocParDao.addParagraphe(story, "", false, lockedBy, true); 
			
			/* To suppress the warnings */
			ResClose.silencedClosing(res, st);
			st = conn.prepareStatement("UPDATE Choice set locked = 1 , assocPar = ?, assocStory = ? where idChoice = ?");
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
	
	/**
	 * this method allows to unlock the choice if the writer does not want no longer write the choice's 
	 * paragraph, it also do some cleaning by deleting the paragraph written by the user before that.
	 * @param idChoice : the id of the choice to unlock 
	 */
	public void unlockChoice(int idChoice) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		/* change locked and set the associated paragraph to Null  : delete the paragraph */
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT assocStory, assocPar from Choice where idChoice = ?");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			res.next();
			String story = res.getString("assocStory");
			int idPar = res.getInt("assocPar");
			ResClose.silencedClosing(res, st);
			st = conn.prepareStatement("UPDATE Choice set locked = 0 ,assocPar = NULL ,assocStory = NULL where idChoice = ?");
			st.setInt(1, idChoice);
			st.executeUpdate();
			ParagrapheDAO assocParDao = new ParagrapheDAO(this.dataSource);
			assocParDao.deleteParagraphe(story, idPar);
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * This allows to verify if there is an access condition over the choice identified by the given parameter
	 * @param idChoice : the id of the choice 
	 * @return the id of the paragraph that represents the access condition or null if there is no access condition 
	 */
	public Integer getAccessCondition(int idChoice) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT idParagraph from AccessCondition where idChoice = ?");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			/* If there is no result */
			if (!res.next()) {
				return null;
			}
			else {
				return res.getInt("idParagraph");
			}
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * 
	 * @param idChoice
	 * @return the userName of the user who locked the choice or wrote it
	 */
	public String lockedOrDoneBy(int idChoice) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT author from Choice Join Paragraph On idParagraph = assocPar and "+ 
			" assocStory = titleStory where idChoice = ?");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			/* If there is no result : the choice is not locked or validated */
			if (!res.next()) {
				return null;
			}
			else {
				return res.getString("author");
			}
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/**
	 * this method allows to define the paragraph to which the choice must lead 
	 * @param idChoice
	 * @param idParagraph
	 * @param story
	 */
	public void associateParagraph(int idChoice, int idParagraph, String story) {
		Connection conn = null; 
		PreparedStatement st = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("UPDATE Choice set assocPar = ?, assocStory = ? where idChoice = ?");
			st.setInt(1, idParagraph);
			st.setString(2, story);
			st.setInt(3, idChoice);
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
	
	/**
	 * This method allows to retrieve recursively the list of the paragraphs that the user can choose 
	 * as an access condition over the choice he is writing 
	 * @param stopIn : allows to define the stop condition of the recursion 
	 * @param stopSt : allows to define the stop condition of the recursion 
	 * @param insertIn : the list of the paragraphs to fill 
	 * @param prevPar 
	 * @param prevStory
	 * @param conn : Data Base connection 
	 */
	public void listParConditionRecur(int stopIn, String stopSt, Map<String, Integer> insertIn, int prevPar, 
			String prevStory, Connection conn) {
		if (stopIn == prevPar && stopSt.equals(prevStory)) {
			return;
		}
		/* Retrieve the previous paragraphs */
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			st = conn.prepareStatement("SELECT prevParStory, prevPar from Choice where assocStory = ? and assocPar = ?");
			st.setString(1, prevStory);
			st.setInt(2, prevPar);
			res = st.executeQuery();
			while (res.next()) {
				String story = res.getString("prevParStory");
				int par = res.getInt("prevPar");
				insertIn.put(story, par);
				/* Recursion */
				listParConditionRecur(stopIn, stopSt, insertIn, par, story, conn);
			}	
		} catch(SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st);
		}	
	}
	
	/**
	 * allows to retrieve the list of the paragraphs that the user can choose between them to define 
	 * his access condition 
	 * @param idChoice
	 * @return a list of paragraphs that the user can choose one of them to define his 
	 * access condition
	 */
	public Map<String, Integer> listParForCondition(int idChoice){
		/* Stop if the previous paragraph is the first paragraph of the story */
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null;
		try {
			Map<String, Integer> listPars = new HashMap<String, Integer>();
			conn = getConnexion();
			/* Retrieve the previous paragraph of the choice */
			st = conn.prepareStatement("SELECT prevParStory, prevPar from Choice where idChoice = ? ");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			String story = res.getString("prevParStory");
			int prevPar = res.getInt("prevPar");
			/* Close */
			ResClose.silencedClosing(res, st);
			/* Must know where to stop */
			st = conn.prepareStatement("SELECT idFirstParagraph from story where title = ?");
			st.setString(1, story);
			res = st.executeQuery();
			res.next();
			int stopIn = res.getInt("idFirstParagraph");
			/* The previous paragraph does not need to be listed as a condition , also as the first one*/
			/* Get paragraphs which finished by a choice leading to this paragraph */
			listParConditionRecur(stopIn, story, listPars, prevPar, story, conn);
			return listPars;
		} catch(SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}	
	}

	/**
	 * allows to retrieve the id of the choice which is locked by the user identified by the given parameter
	 * it is unique by definition 
	 * @param user
	 * @return the id of the locked choice if it exists or null if there is no choice locked by the given user 
	 */
	public Integer getChoiceLockedBy(String user) {
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null ;
		
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT IdChoice from Choice Join Paragraph On assocPar =  idParagraph and " + 
					"assocStory = titleStory where author = ? and locked = 1");
			st.setString(1, user);;
			res = st.executeQuery();
			/* If there is no result */
			if (!res.next()) {
				return null;
			}
			else {
				return res.getInt("idChoice");
			}
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}

	/**
	 * Allows to store in the data base an access condition over a given choice 
	 * @param idChoice : the id of the choice over which must establish an access condition 
	 * @param idParagraph : it defines the condition 
	 * @param story : it defines the condition 
	 */
	public void addAccessCondition(int idChoice, int idParagraph, String story) {
		Connection conn = null; 
		PreparedStatement st = null; 
	
		try {
			conn = getConnexion();
			st = conn.prepareStatement("INSERT into AccessCondition(idChoice, idParagraph, titleStory) values(?, ?, ?)");
			st.setInt(1, idChoice);
			st.setInt(2, idParagraph);
			st.setString(3, story);
			st.executeUpdate();
			
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(st, conn);
		}
	}
}
