package beans;

import java.util.LinkedList;

import javax.sql.DataSource;

import dao.ChoixDAO;

public class Historique {
	private String user; 
	private String story; 
	LinkedList<Choix> hisChoices = new LinkedList<Choix>();  /* The order is necessary */
	
	
	public Historique(String story) { 
		this.story = story; 
	}
	
	public Historique(String user, String story) {
		this.user = user; 
		this.story = story; 
	}
	
	public Historique(String user, String story, LinkedList<Choix> hisChoices) {
		this.user = user; 
		this.story = story; 
		this.hisChoices = hisChoices;
	}
	
	public void addChoiceToHis(Choix choice) {
		hisChoices.add(choice);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public LinkedList<Choix> getHisChoices() {
		return hisChoices;
	}

	public void setHisChoices(LinkedList<Choix> hisChoices) {
		this.hisChoices = hisChoices;
	}
	
	/* To know if a paragraph has been read or not : to use in case if there is a condition 
	 * on the access to a certain choice : used after the method getConditionAccess on ChoixDAO 
	 */
	public boolean hasBeenRead (int idParagraph, DataSource dataSource) {
		/* In case it is needed */
		ChoixDAO choixDAO = new ChoixDAO(dataSource);
		/* loop over all the choices in the history  */
		for (Choix choice : hisChoices) {
			/* get the associated paragraph */
			if (choice.getAssocPar() == null) {
				/* In this case we'll search in the data base */
				/* This is not very optimal */
				choice.setAssocPar(choixDAO.retreiveCorrPar(choice.getIdChoice()).getIdParagraph());
			}
			
			if (choice.getAssocPar() == idParagraph) {
				return true;
			}
		}
		return false;
	}
}
