package beans;

public class Choix {
	
	private Integer idChoice; 
	private String text; 
	private Boolean locked; 
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setIdChoice(Integer idChoice) {
		this.idChoice = idChoice;
	}

	public Integer getIdChoice() {
		return idChoice;
	} 
	
	public Boolean isLocked() {
		return locked;
	}
	
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	
}
