package forms;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;

import beans.Histoire;
import beans.Paragraphe;
import beans.Utilisateur;
import dao.ParagrapheDAO;
import sun.java2d.pipe.ValidatePipe;

public final class CreationForm {
    private static final String CHAMP_TITRE  = "name";
    private static final String CHAMP_IMAGE_URL   = "site";
    private static final String CHAMP_ANNEE   = "annee";
    private static final String CHAMP_Description    = "presentation";
    private static final String CHAMP_Histoire    = "paragraph";


    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    
    public Histoire creerHistoire( HttpServletRequest request, DataSource dataSource ) {
    	
    	String title = getValeurChamp( request, CHAMP_TITRE );
    	String imageUrl = getValeurChamp(request, CHAMP_IMAGE_URL);
    	String annee = getValeurChamp(request, CHAMP_ANNEE);
    	String presentation = getValeurChamp(request, CHAMP_Description);
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);
    	
    	/***À gérer***/
    	boolean validated = false;
    	String author = null;
    	boolean isConclusion = false;
    	
    	Histoire histoire = new Histoire();
    	
    	try {
            validationTitle( title );
        } catch ( Exception e ) {
            setErreur( CHAMP_TITRE, e.getMessage() );
        }
    	histoire.setTitle(title);
    	
    	try {
    		validateURL(imageUrl);
    	} catch ( Exception e ) {
    		setErreur( CHAMP_IMAGE_URL, e.getMessage() );
        }
    	histoire.setImage(imageUrl);
    	
    	try {
    		validateAnnee(annee);
    	} catch ( Exception e ) {
            setErreur( CHAMP_ANNEE, e.getMessage() );
        }
    	//histoire.setDAte
    	
    	try {
    		validateDescription(presentation);
    	} catch ( Exception e ) {
            setErreur( CHAMP_Description, e.getMessage() );
        }
    	histoire.setDescription(presentation);
    	
    	try {
    		ValidateParagraph(paragraph);
    	} catch ( Exception e ) {
            setErreur( CHAMP_Histoire, e.getMessage() );
        }
    	
    	//ParagrapheDAO parDAO = new ParagrapheDAO(dataSource);
    	//int nParag = parDAO.addParagraphe(title, paragraph, validated, author, isConclusion); 
    	//histoire.setFirstParagraph(paragraph);
    	
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

	private void validateDescription(String presentation) {
		// TODO Auto-generated method stub
		
	}

	private void validateAnnee(String annee) {
		// TODO Auto-generated method stub
		
	}

	private void validateURL(String imageUrl) {
		// TODO Auto-generated method stub
		
	}

	private void validationTitle(String titre) {
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