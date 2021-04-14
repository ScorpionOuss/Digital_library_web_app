package forms;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Histoire;
import beans.Paragraphe;
import dao.ChoixDAO;

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

    
    public Paragraphe creerParagraphe( HttpServletRequest request, DataSource dataSource, String author ) {
    	
    	
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);
    	String[] choix = request.getParameterValues(CHAMP_CHOIX);
    	
    	Paragraphe paragraphe = new Paragraphe();
    	
    	/**Récupération des informations à propos de l'histoire*/
    	HttpSession session =  request.getSession();
    	Histoire story =  (Histoire) session.getAttribute(donneeHistoire);
    	String title = story.getTitle();
    	
    			
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
    
    

    private void ValidateParagraph(String paragraph) throws Exception {
    	if ( paragraph != null && paragraph.length() < 20 ) {
            throw new Exception( "Le contenu du paragraphe doit contenir au moins 20 caractères." );
        }
	}



	/*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
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