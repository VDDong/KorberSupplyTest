package KorberSupply.PruebaTecnica.POJO;

public class Article {
	private int id;
	private String description;
	private double weight; // Using the wrapper classes to allow for null in case we try to inadvertently
							// updates the object with null values
	private double volume;
	private boolean active;

	public Article(int id, String description, double weight, double volume, boolean active) {
		this.id = id;
		this.description = description;
		this.weight = weight;
		this.volume = volume;
		this.active = active;
	}
	
	public Article(String description, double weight, double volume, boolean active) {
		this.description = description;
		this.weight = weight;
		this.volume = volume;
		this.active = active;
	}

	public Article() {} //For Jackson
	 
	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (!description.isEmpty() && description != null) {
			this.description = description;
		}
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}