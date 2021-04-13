package beans;

public class Choix {
	
	private Integer idChoice; 
	private String text; 
	private int locked; 
	private boolean isMasked = false; /* Pay attention when to Use it it's not stored in the DB but calculated 
	during the display of paragraphs */
	private Integer assocPar; 
	
	public Integer getAssocPar() {
		return assocPar;
	}

	public void setAssocPar(Integer assocPar) {
		this.assocPar = assocPar;
	}

	public boolean getIsMasked() {
		return isMasked;
	}

	public void setIsMasked(boolean isMasked) {
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
	
	public int getLocked() {
		return locked;
	}
	
	public void setLocked(int locked) {
		assert(locked == 1 || locked == 2 || locked == 0);
		this.locked = locked;
	}
	
}
