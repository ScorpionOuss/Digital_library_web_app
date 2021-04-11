package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResClose {
	
	public static void silencedClosing( ResultSet resultSet ) {
	    if ( resultSet != null ) {
	        try {
	            resultSet.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
	        }
	    }
	}

	
	public static void silencedClosing( Statement statement ) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
	        }
	    }
	}

	public static void silencedClosing( Connection connexion ) {
	    if ( connexion != null ) {
	        try {
	            connexion.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
	        }
	    }
	}

	public static void silencedClosing( Statement statement, Connection connexion ) {
		silencedClosing( statement );
		silencedClosing( connexion );
	}

	public static void silencedClosing( ResultSet resultSet, Statement statement) {
		silencedClosing( resultSet );
		silencedClosing( statement );
	}
	
	public static void silencedClosing( ResultSet resultSet, Statement statement, Connection connexion ) {
		silencedClosing( resultSet );
		silencedClosing( statement );
		silencedClosing( connexion );
	}
}
