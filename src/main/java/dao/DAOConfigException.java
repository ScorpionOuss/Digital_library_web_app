package dao;


public class DAOConfigException extends RuntimeException {
    /*
     * Constructeurs
     */
    public DAOConfigException( String message ) {
        super( message );
    }

    public DAOConfigException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOConfigException( Throwable cause ) {
        super( cause );
    }
}
