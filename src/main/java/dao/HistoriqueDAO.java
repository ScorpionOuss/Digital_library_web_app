package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import beans.Choix;
import beans.Historique;

public class HistoriqueDAO extends AbstractDAO {

	protected HistoriqueDAO(DataSource dataSource) {
		super(dataSource);
	}
	
	/* Can be used to add a new history or modify an old one */
	public void addHistoryToDB(Historique history) {
		/* If there is already a history : modify it otherwise create it */
		String his ="";
		boolean notFirst = false; 
		for (Choix choice : history.getHisChoices()) {
			if (!notFirst) {
				his = his + choice.getIdChoice().toString();
			}
			else {
				his = his + "-" + choice.getIdChoice().toString();
			}
		}
		
		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * FROM HISTORY where reader = ? and titleStory = ? ");
			st.setString(1, history.getUser());
			st.setString(2, history.getStory());
			res = st.executeQuery();
			/* If there is no history */
			String statement; 
			if (!res.next()) {
				statement = "INSERT INTO HISTORY(history, reader, titleStory) values(?, ?, ?)";
			} else {
				statement = "UPDATE HISTORY set history = ? where reader = ? and titleStory = ? ";
			}
			ResClose.silencedClosing(st);
			st = conn.prepareStatement(statement);
			st.setString(1, his);
			st.setString(2, history.getUser());
			st.setString(3, history.getStory());
			st.executeUpdate();
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
	
	/* We'll choose to return null if  there is no history in the database 
	 * For the choice get that only the id and the text are necessary for the history 
	 * we don't care about other things here */
	public Historique GetHistoryFromDB(String story, String reader) {

		Connection conn = null; 
		PreparedStatement st = null; 
		ResultSet res = null; 
		try {
			conn = getConnexion();
			st = conn.prepareStatement("SELECT * FROM HISTORY where reader = ? and titleStory = ? ");
			st.setString(1, reader);
			st.setString(2, story);
			res = st.executeQuery();
			/* if there is no history */
			if (!res.next()) {
				return null; 
			}
			String his = res.getString("history");
			/* yeah now : we must retrieve the IDs of choices from the string */
			Historique history = new Historique(reader, story);
			String[] ids = his.split("-");
			for (String id : ids) {
				/* parse to an integer */
				int idChoice = Integer.parseInt(id);
				/* After that we have to look for the corresponding text */
				st = conn.prepareStatement("SELECT text from Choice where idChoice = ? ");
				st.setInt(1, idChoice);
				res = st.executeQuery();
				/* definitely a result */
				res.next();
				Choix choice = new Choix();
				choice.setIdChoice(idChoice);
				choice.setText(res.getString("text"));
				history.addChoiceToHis(choice);
			}
			return history;
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
}
