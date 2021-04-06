package dao;

import javax.sql.DataSource;

public class UtilisateurDAO extends AbstractDAO{
    
    public UtilisateurDAO(DataSource dataSource){
        super(dataSource);
    }
}