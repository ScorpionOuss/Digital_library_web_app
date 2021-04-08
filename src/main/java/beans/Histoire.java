package beans;

public class Histoire {
	private String title;
	private String creator;
	private Integer firstParagraph;
	private Boolean publicEc; 
	private Boolean publicLec;
	private String image;
	private String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Integer getFirstParagraph() {
		return firstParagraph;
	}
	public void setFirstParagraph(Integer firstParagraph) {
		this.firstParagraph = firstParagraph;
	}
	public Boolean getPublicEc() {
		return publicEc;
	}
	public void setPublicEc(Boolean publicEc) {
		this.publicEc = publicEc;
	}
	public Boolean getPublicLec() {
		return publicLec;
	}
	public void setPublicLec(Boolean publicLec) {
		this.publicLec = publicLec;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
