package forms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Choix;
import beans.Histoire;
import beans.Paragraphe;
import dao.ChoixDAO;
import dao.ParagrapheDAO;

/**
 * to handle the paragraphs modifications by their owners 
 * note that this modification form is only for the paragraphs already written and validated 
 * @author mounsit kaddami yan perez 
 *
 */
public final class ModifyForm {

    private static final String CHAMP_Histoire    = "paragraph";

	private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    /**
     * allows to modify a paragraph in the data base according to what the user inserted in the given
     * form 
     * @param request
     * @param dataSource
     * @param title : identifies the paragraph to modify
     * @param idP : identifies the paragraph to modify
     */
    public void modifierParagraph(HttpServletRequest request, DataSource dataSource, String title, int idP) {
    
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);

    	try {
    		ValidateParagraph(paragraph);
    	} catch ( Exception e ) {
            setErreur( CHAMP_Histoire, e.getMessage() );
        }
    	
    	if ( erreurs.isEmpty() ) {
            resultat = "Modification avec succès!";
            ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
            parDAO.modifyText(title, idP, paragraph);
        } else {
            resultat = "Échec de la modification!";
        }
    }
	
    /**
     * validate the paragraph's text inserted by the user 
     * the text is validated if it is not too short (less that 20 characters) 
     * @param paragraph
     * @throws Exception
     */
    private void ValidateParagraph(String paragraph) throws Exception {
    	if ( paragraph != null && paragraph.length() < 20 ) {
            throw new Exception( "Le contenu du paragraphe doit contenir au moins 20 caractères." );
        }
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
     * this is a useful method used to retrieve the value of a certain field in the form 
     * by normalizing into null if there is no content in the corresponding field 
     * @param request
     * @param nomChamp
     * @return
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}