package forms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import beans.Utilisateur;
import dao.UtilisateurDAO;

/**
 * handle the sign up 
 * @author mounsit
 *
 */
public final class InscriptionForm {
    private static final String CHAMP_USERNAME  = "username";
    private static final String CHAMP_PASS   = "motdepasse";
    private static final String CHAMP_CONF   = "confirmation";


    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * allows a user to sign up after verifying his submission over the data base 
     * @param request
     * @param dataSource
     * @return an object of class Utilisateur containing the signed up user informations 
     */
    public Utilisateur inscrireUtilisateur( HttpServletRequest request, DataSource dataSource) {
        String userName = getValeurChamp( request, CHAMP_USERNAME );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String confirmation = getValeurChamp( request, CHAMP_CONF );

        Utilisateur utilisateur = new Utilisateur();

        try {
        	validationUserName(userName, dataSource);
        } catch ( Exception e ) {
            setErreur( CHAMP_USERNAME, e.getMessage() );
        }
        utilisateur.setUserName( userName);

        try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
//            setErreur( CHAMP_CONF, null );
        }
        //utilisateur.setMotDePasse( motDePasse );



        if ( erreurs.isEmpty() ) {
        	UtilisateurDAO userDao = new UtilisateurDAO(dataSource);
        	//inscrire
        	String hashPass = hashPassword(motDePasse);
            utilisateur.setMotDePasse( hashPass );
            
        	userDao.insertUser(utilisateur);
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";
        }

        return utilisateur;
    }
    
    /**
     * validate the userName submitted by the user by verifying that it does not already exist in the data base 
     * @param userName
     * @param dataSource
     * @throws Exception if the user name already exists in the data base 
     */
    private void validationUserName(String userName, DataSource dataSource) throws Exception {

    	UtilisateurDAO userDao = new UtilisateurDAO(dataSource);
    	LinkedList<String> users = userDao.getUsers();
    	verifyIn(users, userName);	
	}
    
    private void verifyIn(LinkedList<String> invites, String inv) throws Exception {
		for (String invited : invites) {
			if(invited.contentEquals(inv)) {
				throw new Exception("userName invalide");
			}
		}
	}
    
    /**
     * validate the password given by the user by checking and comparing the two fields containing the password and 
     * its confirmation.
     * @param motDePasse
     * @param confirmation
     * @throws Exception if the password is too short or there is difference between it and its confirmation 
     */
	private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new Exception( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 3 ) {
                throw new Exception( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir et confirmer votre mot de passe." );
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
    
    /**
     * Encode the password to avoid identification by real passwords 
     * @param password
     * @return password hashed 
     */
    public String hashPassword(String password){
        String generatedPass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPass = sb.toString();
         } catch (NoSuchAlgorithmException e) 
            {
                e.printStackTrace();
            }
        return generatedPass;
    }

}