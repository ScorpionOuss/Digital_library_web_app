
package dao;

/**
 * Handle data base exceptions 
 * @author mounsit kaddami yan perez 
 *
 */
public class DAOException extends RuntimeException {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message,Throwable cause) {
        super(message, cause);
    }

}
