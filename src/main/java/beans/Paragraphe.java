package beans;

import java.util.LinkedList;

/**
 * It is a model class in order to represent paragraphs
 * @author mounsit kaddami yan perez
 *
 */
public class Paragraphe {
	
	private Integer idParagraph;
	private String story; 
	private String text; 
	private String author;
	private Boolean isValidated;
	private LinkedList<Choix> choices = null;
	
	/**
	 * getter for the id of the paragraph in the data base 
	 * @return
	 */
	public Integer getIdParagraph() {
		return idParagraph;
	}
	
	/**
	 * setter for the id of the paragraph in the data base
	 * @param idParagraph
	 */
	public void setIdParagraph(Integer idParagraph) {
		this.idParagraph = idParagraph;
	}
	
	/**
	 * getter for the title of the story's paragraph
	 * @return
	 */
	public String getStory() {
		return story;
	}
	
	/**
	 * setter for the title of the story's paragraph
	 * @param story
	 */
	public void setStory(String story) {
		this.story = story;
	}
	
	/**
	 * getter for the paragraph's text
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * setter for the paragraph's text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * getter for the boolean indicating if the paragraph is validated or not 
	 * @return
	 */
	public Boolean getIsValidated() {
		return isValidated;
	}
	
	/**
	 * setter for the boolean indicating if the paragraph is validated or not 
	 * @param isValidated
	 */
	public void setIsValidated(Boolean isValidated) {
		this.isValidated = isValidated;
	}
	
	/**
	 * getter for the choices that follow the paragraph 
	 * @return
	 */
	public LinkedList<Choix> getChoices() {
		return choices;
	}
	
	/**
	 *  setter for the choices that follow the paragraph 
	 * @param choices
	 */
	public void setChoices(LinkedList<Choix> choices) {
		this.choices = choices;
	}
	
	/**
	 * getter for the userName of the author of the paragraph
	 * @return
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * setter for the userName of the author of the paragraph
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * allows to verify if the paragraph is a conclusion or not 
	 * @return
	 */
	public boolean isConclusion() {
		return (choices == null|| choices.size() == 0);
	}
	
}
