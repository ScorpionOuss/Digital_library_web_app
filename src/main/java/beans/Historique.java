package beans;

import java.util.LinkedList;

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
	
	
}
