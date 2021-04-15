package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.DataSource;

import beans.Utilisateur;

public class UtilisateurDAO extends AbstractDAO{
    
    public UtilisateurDAO(DataSource dataSource){
        super(dataSource);
    }
    
    public LinkedList<String> getUsers(){
		LinkedList<String> users = new LinkedList<String>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			conn = getConnexion();
			st = conn.prepareStatement("select username from users");
			res = st.executeQuery();
			while (res.next()) {
				users.add(res.getString("username"));
			}
			return users; 
		} catch (SQLException e){
			throw new DAOException("Erreur BD " + e.getMessage(), e);
		} finally {
			ResClose.silencedClosing(res, st, conn);
		}
	}
    

    
    /**
     * 
     * @param user
     * @return
     */
	public Boolean connexion(Utilisateur user){
	    String pseudonyme = user.getUserName();
	    String motDePasse = user.getMotDePasse();
	    ResultSet resultSet = null; 
	    Connection conn = null; 
	    PreparedStatement st = null; 
	    try {
	       conn = getConnexion();
	       st = conn.prepareStatement("select username, password from Users where username =? ");
	       st.setString(1,pseudonyme);
	       resultSet = st.executeQuery();
	       if (resultSet.next()){
	    	   String password = resultSet.getString(2);
	           if (password.equals(motDePasse)){
	        	   return true;}
	       }
	       return false;
	    } catch (SQLException e){
	        throw new DAOException("Erreur BD " + e.getMessage(), e);
	    } finally {
	    	ResClose.silencedClosing(resultSet, st, conn);
	    }
	}
}