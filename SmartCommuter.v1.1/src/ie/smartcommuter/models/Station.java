package ie.smartcommuter.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

/**
 * This is a class is to create instances
 * of Public Transport Stations.
 * @author Shane Bryan Doyle
 */
public class Station implements Serializable {
	
	private static final String NAMESPACE = "http://service.smartcommuter.ie";
	private static final String URL = "http://www.hawke-wit.net/axis2/services/RealTime?wsdl";
	private static final String METHOD_NAME = "getStationData";
	private static final String SOAP_ACTION = "RealTime";
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String apiCode;
	private Address address;
	private Company company;
	private List<StationData> arrivals;
	private List<StationData> departures;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<StationData> getDepartures() {
		return departures;
	}
	public void setDepartures(List<StationData> stationData) {
		this.departures = stationData;
	}
	public List<StationData> getArrivals() {
		return arrivals;
	}
	public void setArrivals(List<StationData> stationData) {
		this.arrivals = stationData;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getStationType() {
		String type = "";
		
		if(getCompany().getName().equalsIgnoreCase("Bus Éireann")) {
			type = "BusEireann";
		} else if(getCompany().getName().equalsIgnoreCase("Dublin Bus")) {
			type = "DublinBus";
		} else if(getCompany().getName().equalsIgnoreCase("Irish Rail")) {
			type = "IrishRail";
		} else if(getCompany().getName().equalsIgnoreCase("JJ Kavanagh & Sons")) {
			type = "JJKavanagh";
		} else {
			type = "Luas";
		}
		
		return type;
	}
	
	
	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * This method is used to get Real Time Data for a 
	 * Public Transport Station.
	 */
	public void getRealTimeData() {
		
		List<StationData> arrivals = new ArrayList<StationData>();
		List<StationData> departures = new ArrayList<StationData>();
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("stationType", getStationType());
		request.addProperty("stationApiCode", getApiCode());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
		
		SoapObject response = null;

        try {
        	androidHttpTransport.debug = true;
        	androidHttpTransport.call(SOAP_ACTION,envelope);
	        response = (SoapObject)envelope.bodyIn;
        } catch(Exception e) {
        	e.printStackTrace();
        }

    	int count = 0;
    	
    	if(response!=null) {
    		count = response.getPropertyCount();
    	}
    	
    	for(int i = 0; i < count; i++) {
    		SoapObject obj = (SoapObject) response.getProperty(i);
    		
    		StationData data = soapObjectToStationData(obj);
    		
    		if(data.getIsArrivalOrDeparture().equals("Arrival")) {
    			arrivals.add(data);
    		} else {
    			departures.add(data);
    		}
    	}
    	
    	setArrivals(arrivals);
    	setDepartures(departures);
	}
	
    /**
     * This method is used to convert a Soap Object to a
     * Station Data Object.
     * @param soapObj
     * @return
     */
    private static StationData soapObjectToStationData(SoapObject soapObj) {
    	StationData stationData = new StationData();
    	
    	stationData.setDestination(soapObj.getProperty("destination").toString());
    	stationData.setRoute(soapObj.getProperty("route").toString());
    	stationData.setExpectedTime(soapObj.getProperty("expectedTime").toString());
    	stationData.setIsArrivalOrDeparture(soapObj.getProperty("isArrivalOrDeparture").toString());
    	
    	return stationData;
    }
}
