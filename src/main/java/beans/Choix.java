package beans;

/**
 * It is a model class in order to represent a paragraph's choices
 * @author mounsit kaddami yan perez
 *
 */
public class Choix {
	
	private Integer idChoice; 
	private String text; 
	private int locked; 
	private boolean isMasked = false; /* Pay attention when to Use it it's not stored in the DB but calculated 
	during the display of paragraphs */
	private Integer assocPar; 
	
	/**
	 * getter for the id that identifies the paragraph to which the choice leads 
	 * @return the id of the associated paragraph  
	 */
	public Integer getAssocPar() {
		return assocPar;
	}
	
	/**
	 * this method is to add the id of the associated paragraph 
	 * @param assocPar
	 */
	public void setAssocPar(Integer assocPar) {
		this.assocPar = assocPar;
	}

	/**
	 * the choice is hidden in reading mode if it does not lead to a conclusion  
	 * @return boolean that indicated if the choice must be hidden or not 
	 */
	public boolean getIsMasked() {
		return isMasked;
	}
	
	/**
	 * to indicate if this choice is hidden or not, a choice is hidden if it does not lead to a conclusion
	 * @param isMasked
	 */
	public void setIsMasked(boolean isMasked) {
		this.isMasked = isMasked;
	}
	
	/**
	 * getter for the choice states 
	 * @return string : the choice states
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * setter for the choice states
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * setter for the id that identifies the choice in the data base
	 * @param idChoice
	 */
	public void setIdChoice(Integer idChoice) {
		this.idChoice = idChoice;
	}
	
	/**
	 * to get the id of the choice in the data base 
	 * @return
	 */
	public Integer getIdChoice() {
		return idChoice;
	} 
	
	/**
	 * the locked integer has 3 values : 1 if the choice is locked, 0 if the choice is not locked, or 2 if 
	 * the choice is already written and validated 
	 * @return a value that indicated if the choice is validated or locked or unlocked
	 */
	public int getLocked() {
		return locked;
	}
	
	/**
	 * to indicate if the choice is locked , unlocked or validated 
	 * @param locked is 1 if the choice is locked, 0 is unlocked and 2 if it's already written and validated
	 */
	public void setLocked(int locked) {
		assert(locked == 1 || locked == 2 || locked == 0);
		this.locked = locked;
	}
	
}
