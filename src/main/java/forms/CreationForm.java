package forms;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;

import beans.Histoire;
import dao.HistoireDAO;
import dao.ParagrapheDAO;
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

    
    public Histoire creerHistoire( HttpServletRequest request, DataSource dataSource, String author ) {
    	
    	String title = getValeurChamp( request, CHAMP_TITRE );
    	String imageUrl = getValeurChamp(request, CHAMP_IMAGE_URL);
    	String annee = getValeurChamp(request, CHAMP_ANNEE);
    	String presentation = getValeurChamp(request, CHAMP_Description);
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);
    	
    	/***À gérer***/
    	boolean publicEc = true;
    	boolean publicLec = true;
    	
    	Histoire histoire = new Histoire();
    	
    	try {
            validationTitle( title, dataSource );
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
    	
    	HistoireDAO stroryDAO = new HistoireDAO(dataSource);
    	ParagrapheDAO paragraphDAO = new ParagrapheDAO(dataSource);
    	int idP = stroryDAO.addHistoire(title, author, publicLec, publicEc, imageUrl, presentation);
    	paragraphDAO.modifyText(title, idP, paragraph);
    	stroryDAO.publish_story(title);
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
    
    

    private void ValidateParagraph(String paragraph) throws Exception {
    	if ( paragraph != null && paragraph.length() < 20 ) {
            throw new Exception( "Le contenu du paragraphe doit contenir au moins 20 caractères." );
        }
	}

	private void validateDescription(String presentation) throws Exception {
    	if ( presentation != null && presentation.length() < 10 ) {
            throw new Exception( "Le contenu de la description doit contenir au moins 10 caractères." );
        }
	}

	private void validateAnnee(String annee) {
		// TODO Auto-generated method stub
		
	}

	private void validateURL(String imageUrl) {
		// TODO Auto-generated method stub

	}

	private void validationTitle(String titre, DataSource dataSource) throws Exception {
		HistoireDAO story = new HistoireDAO(dataSource);
		if(story.verifyTitle(titre)== false) {
			throw new Exception("Le titre choisi existe déjà!");
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