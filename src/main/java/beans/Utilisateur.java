package beans;

public class Utilisateur {

    private String userName;
    private String motDePasse;
    private String nom;

    public void setUserName(String email) {
	this.userName = email;
    }
    public String getUserName() {
	return userName;
    }

    public void setMotDePasse(String motDePasse) {
	this.motDePasse = motDePasse;
    }
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