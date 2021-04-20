package forms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.sql.DataSource;

import javax.servlet.http.HttpServletRequest;

import beans.Histoire;
import dao.ChoixDAO;
import dao.HistoireDAO;
import dao.ParagrapheDAO;
import dao.UtilisateurDAO;

/**
 * To handle the creation of a new story 
 * @author mounsit kaddami yan perez 
 *
 */
public final class CreationForm {
    private static final String CHAMP_TITRE  = "nom";
    private static final String CHAMP_IMAGE_URL   = "site";
    private static final String CHAMP_ANNEE   = "annee";
    private static final String CHAMP_Description    = "presentation";
    private static final String CHAMP_Histoire    = "paragraph";
    private static final String CHAMP_CHOIX = "choix";
    private static final String CHAMP_INVITED = "userid";
    private static final String CHAMP_CHECK = "typecand";
    private static final String CHAMP_Read = "etatLecture";


    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * allows to add a story in the data base from the form submitted by the user, the insertion 
     * is done after verifying all the form fields 
     * @param request
     * @param dataSource
     * @param author
     * @return
     */
    public String[] creerHistoire( HttpServletRequest request, DataSource dataSource, String author ) {
    	
    	String title = getValeurChamp( request, CHAMP_TITRE );
    	String imageUrl = getValeurChamp(request, CHAMP_IMAGE_URL);
    	String annee = getValeurChamp(request, CHAMP_ANNEE);
    	String presentation = getValeurChamp(request, CHAMP_Description);
    	String paragraph = getValeurChamp(request, CHAMP_Histoire);
    	String lectureEnable = getValeurChamp(request, CHAMP_Read);
    	String[] choix = request.getParameterValues(CHAMP_CHOIX);
    	String[] invited = request.getParameterValues(CHAMP_INVITED);
    	String[] checkBox = request.getParameterValues(CHAMP_CHECK);
    	
    	/***À gérer***/
    	boolean publicEc;
    	boolean publicLec = true;
    	
    	if (lectureEnable == null) {
    		publicLec = false;
    	}
    	
    	if(checkBox[0].contentEquals("public")) {
    		publicEc = true;
    	}
    	else {
    		assert(checkBox[0].contentEquals("invite"));
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
		            break;
		        }
	    	}
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
    	
    	/*Vérification erreurs*/ 
    	if ( erreurs.isEmpty() ) {
    		
    		/*Création de l'histoire et du premier paragraphe associé*/
        	HistoireDAO stroryDAO = new HistoireDAO(dataSource);
        	ParagrapheDAO paragraphDAO = new ParagrapheDAO(dataSource);
        	int idP = stroryDAO.addHistoire(title, author, publicLec, publicEc, imageUrl, presentation);
        	paragraphDAO.modifyText(title, idP, paragraph);
        	histoire.setFirstParagraph(idP);
        	
        	/* Création des choix associés au paragraphe*/
        	ChoixDAO choiceDAO = new ChoixDAO(dataSource);
        	if (choix != null) {
        		/*  the paragraph is a body paragraph */
        		paragraphDAO.declareAsBodyParagraph(idP, title);
        		/* Creation des choix associés au paragraphe */
	        	for (String choice:choix) {
	        		choiceDAO.addChoice(title, idP, choice);
	        	}
        	}
        	/*Gestion des invités*/
        	// TO-DO
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
    	
    	return checkBox;
    }
    
    
    /**
     * verify if the given invited user names inserted by the user already exist in the data base or not 
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
     * @throws Exception : if the user name does not belong the the list given in parameter 
     */
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

	/**
	 * verify if the paragraph is valid : there is absolutely a text and it contains more that 10 characters 
	 * @param paragraph
	 * @throws Exception if the paragraph's text is not valid
	 */
	private void ValidateParagraph(String paragraph) throws Exception {
    	if (paragraph == null || paragraph.length() < 10 ) {
            throw new Exception("Le contenu du paragraphe doit contenir au moins 20 caractères.");
        }
	}

	/**
	 * verify if the story's description given by the user is valid : there is absolutely a description and 
	 * it contains at least 5 characters.
	 * @param presentation
	 * @throws Exception if the given description is not valid 
	 */
	private void validateDescription(String presentation) throws Exception {
    	if (presentation == null || presentation.length() < 5) {
            throw new Exception("Le contenu de la description doit contenir au moins 10 caractères.");
        }
	}

	private void validateAnnee(String annee) {
		// TODO Auto-generated method stub
		
	}

	private void validateURL(String imageUrl) {
		// TODO Auto-generated method stub

	}

	/**
	 * validate the story's title : the title is valid if it is not a null value and it does not 
	 * exist in the data base before 
	 * @param titre
	 * @param dataSource
	 * @throws Exception if the title is not valid 
	 */
	private void validationTitle(String titre, DataSource dataSource) throws Exception {
		HistoireDAO story = new HistoireDAO(dataSource);
		if( titre == null || story.verifyTitle(titre)== false) {
			throw new Exception("Titre non renseigné où existe déjà!");
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