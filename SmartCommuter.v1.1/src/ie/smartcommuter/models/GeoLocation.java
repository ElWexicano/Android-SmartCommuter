package ie.smartcommuter.models;

/**
 * This class stores GeoLocation methods.
 * @author Shane Bryan Doyle
 */
public class GeoLocation {
	
	/**
	 * This method is used to convert MicroDegrees to Degrees.
	 * @param microDegrees
	 * @return
	 */
	public static Double microDegreesToDegrees(int microDegrees) {
		return (microDegrees / 1e6);
	}
	
	/**
	 * This method is used to convert Degrees to MicroDegrees.
	 * @param degrees
	 * @return
	 */
	public static Integer degreesToMicroDegrees(double degrees) {
		return (int) (degrees * 1e6);
	}
	
	/**
	 * This method is used to convert Degrees to Radians.
	 * @param degrees
	 * @return
	 */
	public static Double degreesToRadians(double degrees) {
		return (degrees * Math.PI / 180);
	}
	
	/**
	 * This method is used to get the distance in kilometres
	 *  between two points.
	 * @param lat1
	 * @param lon1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static Double distanceBetweenPoints(double lat1, double lon1, double lon2, double lat2) {
		
		double earthRadius = 6378.7;
		
		double distance = earthRadius * Math.acos(
				Math.sin(lat1)*Math.sin(lat2) + 
				Math.cos(lat1)*Math.cos(lat2) * 
				Math.cos(lon2)-Math.cos(lon1));
		
		return distance;
	}
}