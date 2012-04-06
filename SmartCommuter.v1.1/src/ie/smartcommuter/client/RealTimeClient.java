package ie.smartcommuter.client;

import ie.smartcommuter.models.Station;
import ie.smartcommuter.models.StationData;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

public class RealTimeClient {

	 private static final String NAMESPACE = "http://www.hawke-wit.net/axis2/services/RealTime/";
	 private static final String URL = "http://www.hawke-wit.net/axis2/services/RealTime?wsdl"; 
	 private static final String METHOD_NAME = "getStationData";
	 private static final String SOAP_ACTION = "http://www.hawke-wit.net/axis2/services/RealTime/getStationData";
	
	public static List<StationData> getStationData(Station station) {
        SoapObject response = InvokeMethod(URL, station);
        return getStationsFromSoap(response);
	}
	
	public static SoapObject InvokeMethod(String URL, Station station) {
        SoapObject request = GetSoapObject();
        request.addAttribute("stationType", station.getStationType());
        request.addAttribute("stationApiCode", station.getApiCode());
        SoapSerializationEnvelope envelope = GetEnvelope(request);
        return  MakeCall(URL,envelope, NAMESPACE, METHOD_NAME);
    }
	
	public static SoapObject GetSoapObject() {
        return new SoapObject(NAMESPACE, METHOD_NAME);
    }
	
    public static SoapSerializationEnvelope GetEnvelope(SoapObject Soap) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(Soap);
        return envelope;
    }
    
    public static SoapObject MakeCall(String URL, SoapSerializationEnvelope Envelope, String NAMESPACE, String METHOD_NAME) {
    	AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
        try {
	        androidHttpTransport.call(SOAP_ACTION, Envelope);
	        SoapObject response = (SoapObject)Envelope.getResponse();
	        return response;
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    public static List<StationData> getStationsFromSoap(SoapObject soap) {
    	
    	List<StationData> realtimeInfo = new ArrayList<StationData>();
    	
    	int count = 0;
    	
    	if(soap!=null) {
    		count = soap.getPropertyCount();
    	}
    	
    	for(int i = 0; i < count; i++) {
    		SoapObject soapObj = (SoapObject)soap.getProperty(i);
    		StationData data = soapObjectToStationData(soapObj);
    		realtimeInfo.add(data);
    	}
    	
		return realtimeInfo;
    }
    
    private static StationData soapObjectToStationData(SoapObject soapObj) {
    	StationData stationData = new StationData();
    	
    	stationData.setDestination(soapObj.getProperty("destination").toString());
    	stationData.setRoute(soapObj.getProperty("route").toString());
    	stationData.setExpectedTime(soapObj.getProperty("expectedTime").toString());
    	stationData.setIsArrivalOrDeparture(soapObj.getProperty("isArrivalOrDeparture").toString());
    	
    	return stationData;
    }
    
}
