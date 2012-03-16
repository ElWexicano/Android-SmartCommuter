package ie.smartcommuter.models;

import java.util.List;

public class Station {
	
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
}
