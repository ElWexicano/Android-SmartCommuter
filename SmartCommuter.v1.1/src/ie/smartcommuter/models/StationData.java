package ie.smartcommuter.models;

/**
 * This is a class is to create instances
 * of Station Data ie real time information.
 * @author Shane Bryan Doyle
 */
public class StationData {
	
	private String route;
	private String destination;
	private String expectedTime;
	private String isArrivalOrDeparture;
	
	public String getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getIsArrivalOrDeparture() {
		return isArrivalOrDeparture;
	}
	public void setIsArrivalOrDeparture(String isArrivalOrDeparture) {
		this.isArrivalOrDeparture = isArrivalOrDeparture;
	}
	
	public void printStationData(){
		System.out.print("Route: "+getRoute());
		System.out.print(", Destination: "+getDestination());
		System.out.print(", Expected Time: "+getExpectedTime());
		System.out.print(", Type: "+getIsArrivalOrDeparture()+"\n");
	}

}
