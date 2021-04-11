package beans;

public class Choix {
	
	private Integer idChoice; 
	private String text; 
	private Boolean locked; 
	private boolean isMasked = false; /* Pay attention when to Use it it's not stored in the DB but calculated 
	during the display of paragraphs */
	
	public boolean getIsMasked() {
		return isMasked;
	}

	public void setMasked(boolean isMasked) {
		this.isMasked = isMasked;
	}

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
	
	public Boolean getIsLocked() {
		return locked;
	}
	
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
}
