package forms;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;

import beans.Histoire;
import beans.Paragraphe;

public final class WriteParagraphForm {

    private static final String CHAMP_Histoire    = "paragraph";


    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    
    public Paragraphe creerParagraphe( HttpServletRequest request, DataSource dataSource ) {
    	
    	
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);
    	
    	
    	Paragraphe histoire = new Paragraphe();
    	
    	
    	try {
    		ValidateParagraph(paragraph);
    	} catch ( Exception e ) {
            setErreur( CHAMP_Histoire, e.getMessage() );
        }
    	
    	//ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
    	//int nParag = parDAO.addParagraphe(title, paragraph, validated, author, isConclusion); 
    	
    	if ( erreurs.isEmpty() ) {
            resultat = "Histoire créée avec succès";
        } else {
            resultat = "Échec de la création de l'histoire.";
        }
    	
    	return histoire;
    }
    
    

    private void ValidateParagraph(String paragraph) {
		// TODO Auto-generated method stub
		
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