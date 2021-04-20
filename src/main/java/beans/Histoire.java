package beans;

/**
 * It is a model class in order that represent the stories 
 * @author mounsit kaddami yan perez 
 *
 */
public class Histoire {
	private String title;
	private String creator;
	private Integer firstParagraph;
	private Boolean publicEc; 
	private Boolean publicLec;
	private String image;
	private String description;
	
	/**
	 * getter for the story's title
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * setter for the story's title 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * getter for the story's creator (owner) 
	 * @return
	 */
	public String getCreator() {
		return creator;
	}
	
	/**
	 * setter for the story's creator 
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	/**
	 * getter for the id of the first paragraph of the story in the data base 
	 * @return
	 */
	public Integer getFirstParagraph() {
		return firstParagraph;
	}
	
	/**
	 * setter for the id of the first paragraph of the story in the data base 
	 * @param firstParagraph
	 */
	public void setFirstParagraph(Integer firstParagraph) {
		this.firstParagraph = firstParagraph;
	}
	
	/**
	 * getter that indicates if the story is public in writing mode or not 
	 * @return
	 */
	public Boolean getPublicEc() {
		return publicEc;
	}
	
	/**
	 * setter to indicate if the story is public in writing mode or not 
	 * @param publicEc
	 */
	public void setPublicEc(Boolean publicEc) {
		this.publicEc = publicEc;
	}
	
	/**
	 * getter that indicates if the story is public in reading mode or not 
	 * @return
	 */
	public Boolean getPublicLec() {
		return publicLec;
	}
	
	/**
	 * setter that indicates if the story is public in reading mode or not 
	 * @param publicLec
	 */
	public void setPublicLec(Boolean publicLec) {
		this.publicLec = publicLec;
	}
	
	/**
	 * getter for the URL of the image of the story 
	 * @return
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * setter for the URL of the image of the story 
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * getter for the description of the story 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * setter of the description of the story 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
