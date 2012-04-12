package ie.smartcommuter.models;

/**
 * This class is used to represent a single
 * stage of a journey.
 * @author Shane Bryan Doyle
 */
public class Stage {
	
	private String distance;
	private String duration;
	private Address startLocation;
	private Address endLocation;
	private String instructions;
	
	public Stage() {}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Address getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Address startLocation) {
		this.startLocation = startLocation;
	}

	public Address getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Address endLocation) {
		this.endLocation = endLocation;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = (android.text.Html.fromHtml(instructions).toString()).replaceAll("\\s+", " ");
		
		if(getInstructions().contains("Destination will be")) {
			this.instructions = this.instructions.replace(" Destination will be", ". Destination will be");
		}
	}
	
}