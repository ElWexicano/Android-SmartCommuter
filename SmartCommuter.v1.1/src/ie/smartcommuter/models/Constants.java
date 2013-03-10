package ie.smartcommuter.models;

/**
 * This class is used to store Constants for the app.
 * General rule of thumb is to put related constants
 * in their own class and all the rest just go in the
 * constants class.
 * 
 * @author Shane Doyle
 */
public class Constants {

	public static final String DIRECTIONS_MODE = "Walking";

	public static final double EARTH_RADIUS = 6378.7;

	public static final String ARRIVAL = "Arrival";
	public static final String DEPARTURE = "Departure";
	public static final String DESTINATION_WILL_BE = "Destination will be";

	public static final String STATUS = "status";
	public static final String OK = "OK";
	public static final String ROUTES = "routes";
	public static final String VIA = "via";
	public static final String LEGS = "legs";
	public static final String DISTANCE = "distance";
	public static final String DURATION = "duration";
	public static final String TEXT = "text";
	public static final String STEPS = "steps";
	public static final String START_LOCATION = "start_location";
	public static final String START_ADDRESS = "start_address";
	public static final String END_LOCATION = "end_location";
	public static final String END_ADDRESS = "end_address";
	public static final String SUMMARY = "summary";
	public static final String HTML_INSTRUCTIONS = "html_instructions";
	public static final String LAT = "lat";
	public static final String LNG = "lng";

	// Database Related Constants.
	public static class Database {
		public static final String NAME = "smartDB.db";
		public static final String SQL_FILE_NAME = "smartDB.sql";
		public static final int VERSION = 1;
	}

	// URL Related Constants.
	public static class URLs {
		public static final String GOOGLE_DIRECTIONS_API = "http://maps.googleapis.com/maps/api/directions/json?origin=";
	}

	public static class WebService {
		public static final String URL = "http://smartcommuterws.cloudfoundry.com/services/RealTimeWebService?wsdl";
		public static final String NAMESPACE = "http://ie.smartcommuter.service.RealTimeWebService/";
		public static final String METHOD_NAME = "getStationData";
		public static final String SOAP_ACTION = "RealTimeWebService";
		public static final int TIMEOUT = 20000;
		public static final long serialVersionUID = 1L;
		public static final String PROPERTY_STATION_TYPE = "stationType";
		public static final String PROPERTY_STATION_API_CODE = "stationApiCode";
	}

	public static class Companies {
		public static final String BUS_EIREANN = "Bus Eireann";
		public static final String DUBLIN_BUS = "Dublin Bus";
		public static final String IRISH_RAIL = "Irish Rail";
		public static final String JJ_KAVANAGH = "JJ Kavanagh & Sons";
		public static final String JJ_KAVANAGH_TYPE = "JJKavanagh";
		public static final String LUAS = "Luas";
	}
}