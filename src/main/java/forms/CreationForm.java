package forms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import javax.servlet.http.HttpServletRequest;

import beans.Histoire;
import beans.Paragraphe;
import dao.ChoixDAO;
import dao.HistoireDAO;
import dao.ParagrapheDAO;
import dao.UtilisateurDAO;
import jdk.internal.dynalink.beans.StaticClass;
public final class CreationForm {
    private static final String CHAMP_TITRE  = "nom";
    private static final String CHAMP_IMAGE_URL   = "site";
    private static final String CHAMP_ANNEE   = "annee";
    private static final String CHAMP_Description    = "presentation";
    private static final String CHAMP_Histoire    = "paragraph";
    private static final String CHAMP_CHOIX = "choix";
    private static final String CHAMP_INVITED = "userid";
    private static final String CHAMP_CHECK = "typecand";



    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    
    public String[] creerHistoire( HttpServletRequest request, DataSource dataSource, String author ) {
    	
    	String title = getValeurChamp( request, CHAMP_TITRE );
    	String imageUrl = getValeurChamp(request, CHAMP_IMAGE_URL);
    	String annee = getValeurChamp(request, CHAMP_ANNEE);
    	String presentation = getValeurChamp(request, CHAMP_Description);
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);
    	String[] choix = request.getParameterValues(CHAMP_CHOIX);
    	String[] invited = request.getParameterValues(CHAMP_INVITED);
    	String[] checkBox = request.getParameterValues(CHAMP_CHECK);
    	
    	/***À gérer***/
    	boolean publicEc;
    	boolean publicLec = true;
    	
    	if(checkBox[0] == "public") {
    		publicEc = true;
    	}
    	else {
    		assert(checkBox[1] == "invite");
    		publicEc = false;
    	}
    	
    	
    	Histoire histoire = new Histoire();
    	
    	/**Without validation**/
    	histoire.setPublicEc(publicEc);
    	histoire.setPublicLec(publicLec);
    	histoire.setCreator(author);
    	
    	
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

    	if (choix != null) {
	    	for(String ch: choix) {
		    	try {
		    		ValidateChoix(ch);
		    	} catch ( Exception e ) {
		            setErreur( CHAMP_CHOIX, e.getMessage() );
		        }
	    	}
    	}
    	
    	if (invited != null) {
	    	for(String inv: invited) {
		    	try {
		    		ValidateInvited(inv, title, dataSource);
		    	} catch ( Exception e ) {
		            setErreur( CHAMP_INVITED, e.getMessage() );
		        }
	    	}
    	}
    	
    	/*Vérification erreurs*/ 
    	if ( erreurs.isEmpty() ) {
    		
    		/*Création de l'histoire et du premier paragraphe associé*/
        	HistoireDAO stroryDAO = new HistoireDAO(dataSource);
        	ParagrapheDAO paragraphDAO = new ParagrapheDAO(dataSource);
        	int idP = stroryDAO.addHistoire(title, author, publicLec, publicEc, imageUrl, presentation);
        	paragraphDAO.modifyText(title, idP, paragraph);
        	stroryDAO.publish_story(title);
        	histoire.setFirstParagraph(idP);
        	
        	/* Création des choix associés au paragraphe*/
        	ChoixDAO choiceDAO = new ChoixDAO(dataSource);
        	for (String choice:choix) {
        		choiceDAO.addChoice(title, idP, choice);
        	}
        	
        	/*Gestion des invités*/
        	// TO-DO
        	if (publicEc == false) {
        		for (String inv:invited) {
        			stroryDAO.addInvited(title, inv);
        		}
        	}
            resultat = "Histoire créée avec succès";
        } else {
            resultat = "Échec de la création de l'histoire.";
        }
    	
    	return invited;
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

	private void ValidateChoix(String choix) {
		// TODO Auto-generated method stub
		
	}

	private void ValidateParagraph(String paragraph) throws Exception {
    	if (paragraph.length() < 20 ) {
            throw new Exception( "Le contenu du paragraphe doit contenir au moins 20 caractères." );
        }
	}

	private void validateDescription(String presentation) throws Exception {
    	if (presentation.length() < 10 ) {
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
		if( titre == null || story.verifyTitle(titre)== false) {
			throw new Exception("Titre non renseigné où existe déjà!");
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