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
 * To handle the connection of the user over the application 
 * @author mounsit kaddami yan perez 
 *
 */
public final class ConnexionForm {
    private static final String CHAMP_USERNAME  = "username";
    private static final String CHAMP_PASS   = "motdepasse";
    private static final String CHAMP_CONX  = "connexion";

    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    /**
     * allows the user to be logged in after verifying his given submissions over the data base 
     * @param request
     * @param userDAO
     * @param dataSource
     * @return
     */
    public Utilisateur connecterUtilisateur( HttpServletRequest request, UtilisateurDAO userDAO, DataSource dataSource ) {
        /* Récupération des champs du formulaire */
        String userName = getValeurChamp( request, CHAMP_USERNAME );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );

        Utilisateur utilisateur = new Utilisateur();

        /* Validation du champ email. */
        try {
            validationUserName( userName, dataSource );
        } catch ( Exception e ) {
            setErreur( CHAMP_USERNAME, e.getMessage() );
        }
        utilisateur.setUserName( userName );

        /* Validation du champ mot de passe. */
        try {
            validationMotDePasse( motDePasse );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        //utilisateur.setMotDePasse( motDePasse );

        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
        	resultat = "Succès de la connexion.";
        	
            String hashPass = hashPassword(motDePasse);
            utilisateur.setMotDePasse( hashPass );
            
        	try {
        	connexionUser(utilisateur, userDAO);
            resultat = "Succès de la connexion.";
        	}catch (Exception e) {
				setErreur(CHAMP_CONX, e.getMessage());
			}
        } else {
            resultat = "Échec de la connexion.";
        }

        return utilisateur;
    }

    /**
     * validate the user name inserted in the form by verifying if it exists in the data base or not
     * @param userName
     * @param dataSource
     * @throws Exception
     */
    private void validationUserName(String userName, DataSource dataSource) throws Exception {

    	UtilisateurDAO userDao = new UtilisateurDAO(dataSource);
    	LinkedList<String> users = userDao.getUsers();
    	verifyIn(users, userName);	
	}
    
    
    private void verifyIn(LinkedList<String> invites, String inv) throws Exception {
		for (String invited : invites) {
			if(invited.contentEquals(inv)) {
				return;
			}
		}
		throw new Exception("userName invalide");

	}

    /**
     * verifying the inserted password by checking if it is not null and its length is more than 3 characters 
     */
    private void validationMotDePasse( String motDePasse ) throws Exception {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new Exception( "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir votre mot de passe." );
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
            return valeur;
        }
    }
    
    /**
     * 
     * @param user
     * @param userDao
     * @throws Exception
     */
    private void connexionUser(Utilisateur user, UtilisateurDAO userDao) throws Exception{
        if(!userDao.connexion(user)){
            throw new Exception("Pseudonyme ou mot de passe incorrecte");
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