package ie.smartcommuter.models;

import java.io.Serializable;

import android.location.Location;

/**
 * This is a class is to create instances
 * of a Station Address.
 * @author Shane Bryan Doyle
 */
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	private String location;
	private int latitude;
	private int longitude;
	
	public Address() {}
	
	public Address(Location location) {
		setLatitude(Double.toString(location.getLatitude()));
		setLongitude(Double.toString(location.getLongitude()));
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = GeoLocation.degreesToMicroDegrees(Double.parseDouble(latitude));
	}
	
	public int getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = GeoLocation.degreesToMicroDegrees(Double.parseDouble(longitude));
	}
	
	public Double distanceFromAddress(Address address) {
		
		double lat1 = GeoLocation.microDegreesToDegrees(this.getLatitude());
		double lat2 = GeoLocation.microDegreesToDegrees(address.getLatitude());
		double lon1 = GeoLocation.microDegreesToDegrees(this.getLongitude());
		double lon2 = GeoLocation.microDegreesToDegrees(address.getLongitude());
		
		return GeoLocation.distanceBetweenPoints(lat1, lon1, lon2, lat2);
	}
}
