package dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * an abstract class that allows to factor the DAO code based on JDBC 
 * @author mounsit kaddami yan perez 
 *
 */
public abstract class AbstractDAO {

    protected final DataSource dataSource;
    
    protected AbstractDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    /**
     * Establishing a connection to the DB
     * @return connection
     * @throws java.sql.SQLException
     */
    protected Connection getConnexion() throws SQLException {
        return dataSource.getConnection();
    }
}