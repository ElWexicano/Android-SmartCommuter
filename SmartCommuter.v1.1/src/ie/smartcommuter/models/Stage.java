package ie.smartcommuter.models;

/**
 * This class is used to represent a single stage of a journey.
 * 
 * @author Shane Doyle
 */
public class Stage {

	private String mDistance;
	private String mDuration;
	private Address mStartLocation;
	private Address mEndLocation;
	private String mInstructions;

	public String getDistance() {
		return mDistance;
	}

	public void setDistance(String distance) {
		this.mDistance = distance;
	}

	public String getDuration() {
		return mDuration;
	}

	public void setDuration(String duration) {
		this.mDuration = duration;
	}

	public Address getStartLocation() {
		return mStartLocation;
	}

	public void setStartLocation(Address startLocation) {
		this.mStartLocation = startLocation;
	}

	public Address getEndLocation() {
		return mEndLocation;
	}

	public void setEndLocation(Address endLocation) {
		this.mEndLocation = endLocation;
	}

	public String getInstructions() {
		return mInstructions;
	}

	public void setInstructions(String instructions) {
		this.mInstructions = (android.text.Html.fromHtml(instructions)
				.toString()).replaceAll("\\s+", " ");

		if (getInstructions().contains(Constants.DESTINATION_WILL_BE)) {
			this.mInstructions = this.mInstructions.replace(
					(" " + Constants.DESTINATION_WILL_BE), ". "
							+ Constants.DESTINATION_WILL_BE);
		}
	}

}