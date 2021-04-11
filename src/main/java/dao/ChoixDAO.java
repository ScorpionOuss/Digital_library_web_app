package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
			choice.setLocked(res.getInt("locked") == 1);
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
			/* See if the choice has an associate paragraph which is a conclusion */
			st = conn.prepareStatement("SELECT C.assocStory as story, C.assocPar as idPar "
					+ "from Choice C JOIN Bodyparagraph B ON C.assocStory = B.titleStory and C.assocPar = B.idParagraph " +
					"where idChoice = ? ");
			st.setInt(1, idChoice);
			res = st.executeQuery();
			if (!res.next()) { /* means the choice is associated to a conclusion */
 				memoizeMap.put(idChoice, false);
				return false;
			}
			/* Else search for the next corresponding choices */
			String assocStory = res.getString("story");
			int assocPar = res.getInt("idPar");
			/* To suppress The warnings */
			ResClose.silencedClosing(res, st);
			st = conn.prepareStatement("SELECT idChoice from Choice where prevParStory = ? and prevPar = ? ");
			st.setString(1, assocStory);
			st.setInt(2, assocPar);
			res = st.executeQuery();
			boolean isMasked = true;
			while(res.next()) { /* the choice is masked if all of the next choices are masked */
				isMasked = isMasked && isMaskedRecur(res.getInt("idChoice"), memoizeMap);
			}
			memoizeMap.put(idChoice, isMasked);
			return isMasked; 
			
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
