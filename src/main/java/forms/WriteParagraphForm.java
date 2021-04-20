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
 * To handle adding a new paragraph or modifying a non validated paragraph in a story 
 * @author mounsit kaddami yan perez 
 *
 */
public final class WriteParagraphForm {

    private static final String CHAMP_Histoire    = "paragraph";
    private static final String CHAMP_CHOIX = "choix";
    private static final String donneeHistoire = "donneeHis";
    
    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * validate the form submitted by the user and store the inserted informations in an object of class
     * Paragraphe
     * @param request
     * @param dataSource
     * @param author
     * @return an object of class Paragraphe that contains the informations submitted by the user well structured 
     */
    public Paragraphe creerParagraphe( HttpServletRequest request, DataSource dataSource, String author) {
    	
    	
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);
    	String[] choix = request.getParameterValues(CHAMP_CHOIX);
    	
    	Paragraphe paragraphe = new Paragraphe();
    	
    	/**Récupération des informations à propos de l'histoire*/
    	HttpSession session =  request.getSession();
    	Histoire story =  (Histoire) session.getAttribute(donneeHistoire);
    	String title = story.getTitle();
    		
    	/* Modify the object paragraph */
    	paragraphe.setAuthor(author);
    	paragraphe.setStory(title);
    	paragraphe.setText(paragraph);
    	
    	/*  Create the choices */
    	if (choix != null) {
    		LinkedList<Choix> choices = new LinkedList<Choix>();
    		for (String ch : choix) {
    			Choix choixObj = new Choix();
    			choixObj.setText(ch);
    			choices.add(choixObj);
    		}
    		paragraphe.setChoices(choices);
    	}
    	
    	try {
    		ValidateParagraph(paragraph);
    	} catch ( Exception e ) {
            setErreur( CHAMP_Histoire, e.getMessage() );
        }
      	
    	if ( erreurs.isEmpty() ) {
            resultat = "Histoire créée avec succès";
        } else {
            resultat = "Échec de la création de l'histoire.";
        }
    	
    	return paragraphe;
    }
    
    
    /**
     * verify if the paragraph is valid : there is absolutely a text and it contains more that 20 characters 
     * @param paragraph
     * @throws Exception if the paragraph's text is not valid
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