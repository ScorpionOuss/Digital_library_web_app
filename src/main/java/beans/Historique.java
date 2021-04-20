package beans;

import java.util.LinkedList;

import javax.sql.DataSource;

import dao.ChoixDAO;

/**
 * It is a model class in order to represent history
 * @author mounsit kaddami yan perez 
 *
 */
public class Historique {
	private String user; 
	private String story; 
	LinkedList<Choix> hisChoices = new LinkedList<Choix>();  /* The order is necessary */
	
	/**
	 * constructor
	 * @param story
	 */
	public Historique(String story) { 
		this.story = story; 
	}
	
	/**
	 * constructor
	 * @param user
	 * @param story
	 */
	public Historique(String user, String story) {
		this.user = user; 
		this.story = story; 
	}
	
	/**
	 * constructor
	 * @param user
	 * @param story
	 * @param hisChoices
	 */
	public Historique(String user, String story, LinkedList<Choix> hisChoices) {
		this.user = user; 
		this.story = story; 
		this.hisChoices = hisChoices;
	}
	
	/**
	 * add a choice to the history 
	 * @param choice
	 */
	public void addChoiceToHis(Choix choice) {
		hisChoices.add(choice);
	}

	/**
	 * getter for the user name of the reader to who the history is associated 
	 * @return
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * setter for the user name of the reader to who the history is associated 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * getter for the title of the story to which the history is associated 
	 * @return
	 */
	public String getStory() {
		return story;
	}

	/**
	 * setter for the title of the story to which the history is associated
	 * @param story
	 */
	public void setStory(String story) {
		this.story = story;
	}

	/**
	 * getter for the list of the choices that the history contains 
	 * @return
	 */
	public LinkedList<Choix> getHisChoices() {
		return hisChoices;
	}

	/**
	 * setter for the list of the choices that the history contains 
	 * @param hisChoices
	 */
	public void setHisChoices(LinkedList<Choix> hisChoices) {
		this.hisChoices = hisChoices;
	}
	
	
	/**
	 * To know if a paragraph has been read or not : to use in case if there is a condition 
	 * on the access to a certain choice : used after the method getConditionAccess on ChoixDAO 
	 * @param idParagraph
	 * @param dataSource
	 * @return a boolean indicating if the paragraph has been read or not 
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
	
	/**
	 * this method allows to remove the last choice indicated in the history, it is actually used 
	 * when the user chooses to get back to a previous choice, so all the history must be modified 
	 * @return
	 */
	public Integer removeLastChoice() {
		if (hisChoices.isEmpty()) {
			return null; 
		}
		else {
			int idChoice = hisChoices.removeLast().getIdChoice();
			return idChoice;
		}
	}
}
