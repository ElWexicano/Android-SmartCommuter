package ie.smartcommuter.models;

import ie.smartcommuter.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * This is a class is to create instances of Public Transport Stations.
 * 
 * @author Shane Doyle
 */
public class Station implements Serializable {

	private static final long serialVersionUID = 1L;
	private int mID;
	private String mName;
	private String mApiCode;
	private Address mAddress;
	private Company mCompany;
	private List<StationData> mArrivals;
	private List<StationData> mDepartures;

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getApiCode() {
		return mApiCode;
	}

	public void setApiCode(String apiCode) {
		this.mApiCode = apiCode;
	}

	public Address getAddress() {
		return mAddress;
	}

	public void setAddress(Address address) {
		this.mAddress = address;
	}

	public List<StationData> getDepartures() {
		return mDepartures;
	}

	public void setDepartures(List<StationData> stationData) {
		this.mDepartures = stationData;
	}

	public List<StationData> getArrivals() {
		return mArrivals;
	}

	public void setArrivals(List<StationData> stationData) {
		this.mArrivals = stationData;
	}

	public int getId() {
		return mID;
	}

	public void setId(int id) {
		this.mID = id;
	}

	public Company getCompany() {
		return mCompany;
	}

	public void setCompany(Company company) {
		this.mCompany = company;
	}

	public String getStationType() {
		String type = "";

		if (getCompany().getName().equalsIgnoreCase(
				Constants.Companies.BUS_EIREANN)) {
			type = Constants.Companies.BUS_EIREANN.replaceAll(" ", "");
		} else if (getCompany().getName().equalsIgnoreCase(
				Constants.Companies.DUBLIN_BUS)) {
			type = Constants.Companies.DUBLIN_BUS.replaceAll(" ", "");
		} else if (getCompany().getName().equalsIgnoreCase(
				Constants.Companies.IRISH_RAIL)) {
			type = Constants.Companies.IRISH_RAIL.replaceAll(" ", "");
		} else if (getCompany().getName().equalsIgnoreCase(
				Constants.Companies.JJ_KAVANAGH)) {
			type = Constants.Companies.JJ_KAVANAGH_TYPE;
		} else {
			type = Constants.Companies.LUAS;
		}

		return type;
	}

	public int getStationLogo() {

		int drawable = R.drawable.img_bus_eireann;

		if (this.getCompany().getName().equals(Constants.Companies.BUS_EIREANN)) {
			drawable = R.drawable.img_bus_eireann;
		} else if (this.getCompany().getName()
				.equals(Constants.Companies.DUBLIN_BUS)) {
			drawable = R.drawable.img_dublin_bus;
		} else if (this.getCompany().getName()
				.equals(Constants.Companies.IRISH_RAIL)) {
			drawable = R.drawable.img_irish_rail;
		} else if (this.getCompany().getName()
				.equals(Constants.Companies.JJ_KAVANAGH)) {
			drawable = R.drawable.img_jj_kavanagh;
		} else if (this.getCompany().getName().equals(Constants.Companies.LUAS)) {
			drawable = R.drawable.img_luas;
		}

		return drawable;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * This method is used to get Real Time Data for a Public Transport Station.
	 */
	public void getRealTimeData() {

		List<StationData> arrivals = new ArrayList<StationData>();
		List<StationData> departures = new ArrayList<StationData>();

		SoapObject request = new SoapObject(Constants.WebService.NAMESPACE,
				Constants.WebService.METHOD_NAME);
		request.addProperty(Constants.WebService.PROPERTY_STATION_TYPE,
				getStationType());
		request.addProperty(Constants.WebService.PROPERTY_STATION_API_CODE,
				getApiCode());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport;

		SoapObject response = null;

		try {
			androidHttpTransport = new HttpTransportSE(
					Constants.WebService.URL, Constants.WebService.TIMEOUT);
			androidHttpTransport.debug = true;
			androidHttpTransport.call(Constants.WebService.SOAP_ACTION,
					envelope);

			if (!(envelope.bodyIn instanceof SoapEnvelope)) {
				response = (SoapObject) envelope.bodyIn;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		int count = 0;

		if (response != null) {
			count = response.getPropertyCount();
		}

		for (int i = 0; i < count; i++) {
			SoapObject obj = (SoapObject) response.getProperty(i);

			StationData data = soapObjectToStationData(obj);

			if (data.getIsArrivalOrDeparture().equals("Arrival")) {
				arrivals.add(data);
			} else {
				departures.add(data);
			}
		}

		setArrivals(arrivals);
		setDepartures(departures);
	}

	/**
	 * This method is used to convert a Soap Object to a Station Data Object.
	 * 
	 * @param soapObj
	 * @return
	 */
	private static StationData soapObjectToStationData(SoapObject soapObj) {
		StationData stationData = new StationData();

		stationData.setDestination(soapObj.getProperty("destination")
				.toString());
		stationData.setRoute(soapObj.getProperty("route").toString());
		stationData.setExpectedTime(soapObj.getProperty("expectedTime")
				.toString());
		stationData.setIsArrivalOrDeparture(soapObj.getProperty(
				"isArrivalOrDeparture").toString());

		return stationData;
	}
}
