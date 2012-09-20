package ie.smartcommuter.models;

import ie.smartcommuter.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;


/**
 * This is a class is to create instances
 * of Public Transport Stations.
 * @author Shane Bryan Doyle
 */
public class Station implements Serializable {
	
	private static final String NAMESPACE = "http://ie.smartcommuter.service.RealTimeWebService/";
	private static final String URL = "http://smartcommuterws.cloudfoundry.com/services/RealTimeWebService?wsdl";
	private static final String METHOD_NAME = "getStationData";
	private static final String SOAP_ACTION = "RealTimeWebService";
	private static final int TIMEOUT = 20000;
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
	
	/**
	 * Meth
	 * @return
	 */
	public int getStationLogo() {
		
		int drawable = R.drawable.img_bus_eireann;
		
		if(this.getCompany().getName().equals("Bus Éireann")) {
			drawable = R.drawable.img_bus_eireann;
		} else if(this.getCompany().getName().equals("Dublin Bus")) {
			drawable = R.drawable.img_dublin_bus;
		} else if(this.getCompany().getName().equals("Irish Rail")) {
			drawable = R.drawable.img_irish_rail;
		} else if(this.getCompany().getName().equals("JJ Kavanagh & Sons")) {
			drawable = R.drawable.img_jj_kavanagh;
		} else if(this.getCompany().getName().equals("Luas")) {
			drawable = R.drawable.img_luas;
		}
		
		return drawable;
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
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport;

		SoapObject response = null;

    	try {
        	androidHttpTransport = new HttpTransportSE(URL,TIMEOUT);
        	androidHttpTransport.debug = true;
			androidHttpTransport.call(SOAP_ACTION, envelope);
			
			
		    if (envelope.bodyIn instanceof SoapFault) {
		        String str= ((SoapFault) envelope.bodyIn).faultstring;
		        Log.i("", str);

		        // Another way to travers through the SoapFault object
		    /*  Node detailsString =str= ((SoapFault) envelope.bodyIn).detail; 
		        Element detailElem = (Element) details.getElement(0) 
		                     .getChild(0); 
		        Element e = (Element) detailElem.getChild(2);faultstring; 
		        Log.i("", e.getName() + " " + e.getText(0)str); */
		    } else {
		        response = (SoapObject)envelope.bodyIn;
		        Log.d("WS", String.valueOf(response));
		    }
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
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
