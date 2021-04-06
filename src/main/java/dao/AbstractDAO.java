package dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Classe abstraite permettant de factoriser du code pour les DAO
 * basées sur JDBC
 */
public abstract class AbstractDAO {

    protected final DataSource dataSource;
    
    protected AbstractDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
/**
 * Établissement de la connexion
 * @return connection
 * @throws java.sql.SQLException
 */
    protected Connection getConnexion() throws SQLException {
        return dataSource.getConnection();
    }
}