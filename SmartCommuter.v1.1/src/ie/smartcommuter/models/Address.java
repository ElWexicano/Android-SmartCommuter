package ie.smartcommuter.models;

import java.io.Serializable;

import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 * This is a class is to create instances of a Station Address.
 * 
 * @author Shane Doyle
 */
@SuppressWarnings("serial")
public class Address implements Serializable {

	private String mLocation;
	private int mLatitude;
	private int mLongitude;

	public Address() {
	}

	public Address(Location location) {
		setLatitude(Double.toString(location.getLatitude()));
		setLongitude(Double.toString(location.getLongitude()));
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		this.mLocation = location;
	}

	public int getLatitude() {
		return mLatitude;
	}

	public void setLatitude(String latitude) {
		this.mLatitude = GeoLocation.degreesToMicroDegrees(Double
				.parseDouble(latitude));
	}

	public int getLongitude() {
		return mLongitude;
	}

	public void setLongitude(String longitude) {
		this.mLongitude = GeoLocation.degreesToMicroDegrees(Double
				.parseDouble(longitude));
	}

	public Double distanceFromAddress(Address address) {

		double lat1 = GeoLocation.microDegreesToDegrees(this.getLatitude());
		double lat2 = GeoLocation.microDegreesToDegrees(address.getLatitude());
		double lon1 = GeoLocation.microDegreesToDegrees(this.getLongitude());
		double lon2 = GeoLocation.microDegreesToDegrees(address.getLongitude());

		return GeoLocation.distanceBetweenPoints(lat1, lon1, lon2, lat2);
	}

	public GeoPoint toGeoPoint() {
		return new GeoPoint(this.getLatitude(), this.getLongitude());
	}
}
