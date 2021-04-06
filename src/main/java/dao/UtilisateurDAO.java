package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import beans.Utilisateur;

public class UtilisateurDAO extends AbstractDAO{
    
    public UtilisateurDAO(DataSource dataSource){
        super(dataSource);
    }

    
    /**
     * 
     * @param user
     * @return
     */
	public Boolean connexion(Utilisateur user){
	    String pseudonyme = user.getUserName();
	    String motDePasse = user.getMotDePasse();
	    
	    
	    try (
	            Connection conn = getConnexion();
	            PreparedStatement st = conn.prepareStatement
	            ("select username, password from Users where username =? ");
	        ){
	            st.setString(1,pseudonyme);
	            ResultSet resultSet = st.executeQuery();
	            if (resultSet.next()){
	                String password = resultSet.getString(2);
	                if (password.equals(motDePasse)){
	                    return true;}
	            }
	            return false;
	    } catch (SQLException e){
	        throw new DAOException("Erreur BD " + e.getMessage(), e);
	    }	
	}
}