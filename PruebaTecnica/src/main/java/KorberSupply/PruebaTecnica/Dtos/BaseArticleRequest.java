package KorberSupply.PruebaTecnica.Dtos;

public class BaseArticleRequest {
	private String description;
	private Double weight; //Using the wrapper classes to allow for null in case we try to inadvertently updates the object with null values
	private Double volume;
	private Boolean active;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
}