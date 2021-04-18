package forms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import dao.HistoireDAO;
import dao.UtilisateurDAO;

public class AutorisationForm {

    private static final String CHAMP_CHECK = "typecand";
    private static final String CHAMP_INVITED = "userid";

    
    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    
    public String[] autoriserAcces( HttpServletRequest request, DataSource dataSource, String title ) {
    	String[] checkBox = request.getParameterValues(CHAMP_CHECK);
    	String[] invited = request.getParameterValues(CHAMP_INVITED);

    	boolean publicEc;

    	if(checkBox[0].contentEquals("public")) {
    		publicEc = true;
    	}
    	else {
    		assert(checkBox[0].contentEquals("invite"));
    		publicEc = false;
    	}
    	
    	if (invited != null) {
	    	for(String inv: invited) {
		    	try {
		    		ValidateInvited(inv, title, dataSource);
		    	} catch ( Exception e ) {
		            setErreur( CHAMP_INVITED, e.getMessage() );
		            break;
		        }
	    	}
    	}
    	
    	HistoireDAO stroryDAO = new HistoireDAO(dataSource);

    	/*Vérification erreurs*/ 
    	if ( erreurs.isEmpty() ) {
    		if (publicEc == false) {
        		if(invited != null) {
	        		for (String inv:invited) {
	        			stroryDAO.addInvited(title, inv);
	        		}
        		}
        	}
            resultat = "Histoire créée avec succès";
        } else {
            resultat = "Échec de la création de l'histoire.";
    	}
    	return invited;
    }
    
	/*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    private void ValidateInvited(String invited, String title, DataSource dataSource) throws Exception {
    	UtilisateurDAO usersDAO = new UtilisateurDAO(dataSource);
    	LinkedList<String> invites = usersDAO.getUsers();
    	verifyIn(invites, invited);
	}
    
    //À modifier.
	private void verifyIn(LinkedList<String> invites, String inv) throws Exception {
		for (String invited : invites) {
			if(invited.contentEquals(inv)) {
				return;
			}
		}
		throw new Exception("userName invalide");
	}
}
