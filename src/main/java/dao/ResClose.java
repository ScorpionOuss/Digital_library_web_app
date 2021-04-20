package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class allows to handle the connection and statement and reslutSet closing 
 * @author mounsit kaddami yan perez 
 *
 */
public class ResClose {
	
	/**
	 * allows to close a resultSet 
	 * @param resultSet
	 */
	public static void silencedClosing( ResultSet resultSet ) {
	    if ( resultSet != null ) {
	        try {
	            resultSet.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
	        }
	    }
	}

	/**
	 * allows to close a statement 
	 * @param statement
	 */
	public static void silencedClosing( Statement statement ) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
	        }
	    }
	}

	/**
	 * allows to close a DB connection
	 * @param connexion
	 */
	public static void silencedClosing( Connection connexion ) {
	    if ( connexion != null ) {
	        try {
	            connexion.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
	        }
	    }
	}

	/**
	 * connection and statement closing 
	 * @param statement
	 * @param connexion
	 */
	public static void silencedClosing( Statement statement, Connection connexion ) {
		silencedClosing( statement );
		silencedClosing( connexion );
	}

	/**
	 * resuluSet and statement closing 
	 * @param resultSet
	 * @param statement
	 */
	public static void silencedClosing( ResultSet resultSet, Statement statement) {
		silencedClosing( resultSet );
		silencedClosing( statement );
	}
	
	/**
	 * reslutSet, statement and connection closing 
	 * @param resultSet
	 * @param statement
	 * @param connexion
	 */
	public static void silencedClosing( ResultSet resultSet, Statement statement, Connection connexion ) {
		silencedClosing( resultSet );
		silencedClosing( statement );
		silencedClosing( connexion );
	}
}
