package ie.smartcommuter.models;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class is used to manage database
 * connections and queries.
 * @author Shane Bryan Doyle
 */
public class DatabaseManager {

	private SQLiteDatabase db;
	private DatabaseHelper databaseHelper;
	
	public DatabaseManager(Context context) {
		databaseHelper = new DatabaseHelper(context);
	}
	
	/**
	 * This method is used to open a database connection.
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		db = databaseHelper.getWritableDatabase();
	}
	
	/**
	 * This method is used to close a database connection.
	 */
	public void close() {
		db.close();
		databaseHelper.close();
	}
	
	/**
	 * This method is used to get all stations from 
	 * the database.
	 * @return
	 */
	public List<Station> getAllStations() {
		List<Station> stations = new ArrayList<Station>();
		
		Cursor cursor = db.query("station_details", null, null, null, null, null, "station_name");
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			
			Station station = cursorToStation(cursor);
			
			if(station!=null) {
				stations.add(station);
			}
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return stations;
	}
	
	/**
	 * This method is used to get all favourite stations
	 * from the database.
	 * @return
	 */
	public List<Station> getFavouriteStations() {
		List<Station> stations = new ArrayList<Station>();
		
		Cursor cursor = db.query("favourite_station_details", null, null, null, null, null, "favourite_station_name");
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			
			Station station = cursorToStation(cursor);
			
			if(station!=null) {
				stations.add(station);
			}
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return stations;
	}
	
	/**
	 * This method is used to get all recently viewed
	 * stations from the database.
	 * @return
	 */
	public List<Station> getRecentlyViewedStations() {
		List<Station> stations = new ArrayList<Station>();
		
		Cursor cursor = db.query("recently_viewed_station_details", null, null, null, null, null, "recently_viewed_time DESC");
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			
			Station station = cursorToStation(cursor);
			
			if(station!=null) {
				stations.add(station);
			}
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return stations;
	}
	
	/**
	 * This method is used to get Stations that are
	 * near the phones location.
	 * @param address
	 * @return
	 */
	public List<Station> getNearbyStations(Address address) {
		List<Station> stations = new ArrayList<Station>();
		
		String lat = Double.toString(GeoLocation.microDegreesToDegrees(address.getLatitude()));
		String lon = Double.toString(GeoLocation.microDegreesToDegrees(address.getLongitude()));
		
		String latOrderBy = "("+lat+" - station_latitude) * ("+lat+" - station_latitude)";
		String lonOrderBy = "("+lon+" - station_longitude) * ("+lon+" - station_longitude)";
		
		Cursor cursor = db.query("station_details", null, null, null, null, null, "("+latOrderBy+" + "+lonOrderBy+")", "20");

		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			
			Station station = cursorToStation(cursor);
			
			if(station!=null) {
				stations.add(station);
			}
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return stations;
	}
	
	
	/**
	 * This method is used to get all companies
	 * that are in the database.
	 * @return
	 */
	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();
		
		Cursor cursor = db.query("company_details", null, null, null, null, null, "company_name");

		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			
			Company company = cursorToCompany(cursor);
			
			if(company!=null) {
				companies.add(company);
			}
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return companies;
	}
	
	/**
	 * This method is used to get a station from
	 * the database.
	 * @param stationId
	 * @return
	 */
	public Station getStation(int stationId) {
		Cursor cursor = db.query("station_details", null, "station_id = "+stationId, null, null, null, null);
		cursor.moveToFirst();
		Station station = cursorToStation(cursor);
		cursor.close();

		return station;
	}
	
	/**
	 * This method is used to get a company from
	 * the database.
	 * @param companyName
	 * @return
	 */
	public Company getCompany(String companyName) {
		Cursor cursor = db.query("company_details", null, "company_name = "+companyName, null, null, null, null);
		cursor.moveToFirst();
		Company company = cursorToCompany(cursor);	
		cursor.close();
		
		return company;
	}
	
	/**
	 * This method is used to add a station to the
	 * favourite stations table in the database.
	 * @param stationId
	 */
	public void addToFavouriteStations(int stationId) {
		ContentValues values = new ContentValues();
		values.put("favourite_station_id", stationId);
		db.insert("favourite_stations", null, values);
	}
	
	/**
	 * This method is used to remove a station from the
	 * favourite stations table in the database.
	 * @param stationId
	 */
	public void removeFromFavouriteStations(int stationId) {
		db.delete("favourite_stations", "favourite_station_id = "+stationId, null);
	}
	
	/**
	 * This method is used to add a station to the recently
	 * viewed station table in the database.
	 * @param stationId
	 */
	public void addToRecentlyViewedStations(int stationId) {
		ContentValues values = new ContentValues();
		values.put("recently_viewed_time", System.currentTimeMillis());
		Cursor cursor = db.query("recently_viewed_stations", null, "recently_viewed_station_id = "+stationId, null, null, null, null);
		
		if(cursor.getCount()==0) {
			values.put("recently_viewed_station_id", stationId);
			db.insert("recently_viewed_stations", null, values);
		} else {
			db.update("recently_viewed_stations", values, "recently_viewed_station_id = "+stationId, null);
		}
	}
	
	/**
	 * This class is used to get create a Station
	 * object from a cursor.
	 * @param cursor
	 * @return
	 */
	private Station cursorToStation(Cursor cursor) {
		Station station = new Station();
		Address address = new Address();
		Company company = new Company();

		if(cursor.getCount()>0) {
			station.setId(cursor.getInt(0));
			station.setName(cursor.getString(1));
			station.setApiCode(cursor.getString(2));
			
			company.setName(cursor.getString(3));
			company.setMode(cursor.getString(4));
			station.setCompany(company);
			
			address.setLocation(cursor.getString(5));
			address.setLatitude(cursor.getString(6));
			address.setLongitude(cursor.getString(7));
			station.setAddress(address);
		}
		
		return station;
	}
	
	/**
	 * This class is used to create a Company 
	 * object from a cursor.
	 * @param cursor
	 * @return
	 */
	private Company cursorToCompany(Cursor cursor) {
		Company company = new Company();
		ContactDetails details = new ContactDetails();
		
		if(cursor.getCount()>0) {
			company.setName(cursor.getString(0));
			company.setMode(cursor.getString(1));
			
			details.setEmail(cursor.getString(2));
			details.setTelephone(cursor.getString(3));
			details.setWebsite(cursor.getString(4));
			details.setFacebook(cursor.getString(5));
			details.setTwitter(cursor.getString(6));
			
			company.setDetails(details);
		}
		
		return company;
	}
	
}
