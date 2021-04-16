package beans;

import java.util.LinkedList;

public class Paragraphe {
	
	private Integer idParagraph;
	private String story; 
	private String text; 
	private String author;
	private Boolean isValidated;
	private LinkedList<Choix> choices = null;
	
	public Integer getIdParagraph() {
		return idParagraph;
	}
	public void setIdParagraph(Integer idParagraph) {
		this.idParagraph = idParagraph;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getIsValidated() {
		return isValidated;
	}
	public void setIsValidated(Boolean isValidated) {
		this.isValidated = isValidated;
	}
	public LinkedList<Choix> getChoices() {
		return choices;
	}
	public void setChoices(LinkedList<Choix> choices) {
		this.choices = choices;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isConclusion() {
		return (choices == null|| choices.size() == 0);
	}
	
}
