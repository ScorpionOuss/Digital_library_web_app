package beans;

/**
 * model class representing users 
 * @author mounsit kaddami yan perez 
 *
 */
public class Utilisateur {

    private String userName;
    private String motDePasse;
    private String nom;
    
    /**
     * setter for the user name
     * @param userName
     */
    public void setUserName(String userName) {
	this.userName = userName;
    }
    
    /**
     * getter for the user name 
     * @return
     */
    public String getUserName() {
	return userName;
    }

    /**
     * setter for the password 
     * @param motDePasse
     */
    public void setMotDePasse(String motDePasse) {
	this.motDePasse = motDePasse;
    }
    
    /**
     * getter for the password 
     * @return
     */
    public String getMotDePasse() {
	return motDePasse;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }
    public String getNom() {
	return nom;
    }
}