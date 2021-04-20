package forms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import dao.HistoireDAO;
import dao.UtilisateurDAO;

/**
 * to handle the access rights to edit a story
 * @author mounsit kaddami mateo perez 
 *
 */
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
    
    /**
     * allows to change the status of a story (public/ private for editing) according to what the users 
     * submitted in the form 
     * @param request : contains the form's answers
     * @param dataSource
     * @param title : title of the story 
     * @return
     */
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
    	
    	HistoireDAO storyDAO = new HistoireDAO(dataSource);

    	/*Vérification erreurs*/ 
    	if ( erreurs.isEmpty() ) {
    		/* publish story */
    		if (publicEc) {
    			storyDAO.publishForWriting(title);
    		}
    		else {
    			/* Unpublish */
    			storyDAO.unpublishForWriting(title);
        		if(invited != null) {
	        		for (String inv:invited) {
	        			storyDAO.addInvited(title, inv);
	        		}
        		}
        	}
            resultat = "Droits modifiés par succès";
        } else {
            resultat = "Échec de modification de droits";
    	}
    	return invited;
    }
    
	
    /**
     * add an error message that corresponds to a certain field in the form 
     * @param champ
     * @param message
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    /**
     * verify that the userName to invite inserted by the user already exists in the data base 
     * @param invited
     * @param title
     * @param dataSource
     * @throws Exception
     */
    private void ValidateInvited(String invited, String title, DataSource dataSource) throws Exception {
    	UtilisateurDAO usersDAO = new UtilisateurDAO(dataSource);
    	LinkedList<String> invites = usersDAO.getUsers();
    	verifyIn(invites, invited);
	}
    
    /**
     * verify if the userName of an invited exists in a list of userNames
     * @param invites
     * @param inv
     * @throws Exception
     */
	private void verifyIn(LinkedList<String> invites, String inv) throws Exception {
		for (String invited : invites) {
			if(invited.contentEquals(inv)) {
				return;
			}
		}
		throw new Exception("userName invalide");
	}
}
