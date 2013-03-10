package ie.smartcommuter.models;

/**
 * This is a class is to create instances of Station Data ie real time
 * information.
 * 
 * @author Shane Doyle
 */
public class StationData {

	private String mRoute;
	private String mDestination;
	private String mExpectedTime;
	private String mIsArrivalOrDeparture;

	public String getExpectedTime() {
		return mExpectedTime;
	}

	public void setExpectedTime(String expectedTime) {
		this.mExpectedTime = expectedTime;
	}

	public String getDestination() {
		return mDestination;
	}

	public void setDestination(String destination) {
		this.mDestination = destination;
	}

	public String getRoute() {
		return mRoute;
	}

	public void setRoute(String route) {
		this.mRoute = route;
	}

	public String getIsArrivalOrDeparture() {
		return mIsArrivalOrDeparture;
	}

	public void setIsArrivalOrDeparture(String isArrivalOrDeparture) {
		this.mIsArrivalOrDeparture = isArrivalOrDeparture;
	}

}