package ie.smartcommuter.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is used to store directions for 
 * a journey.
 * @author Shane Bryan Doyle
 */
public class Directions {
	private String distance;
	private String duration;
	private Address startLocation;
	private Address endLocation;
	private List<Stage> stages;
	private String summary;
	
	public Directions() { }

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Address getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Address startLocation) {
		this.startLocation = startLocation;
	}

	public Address getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Address endLocation) {
		this.endLocation = endLocation;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * This method is used to generate the directions
	 * for the address.
	 */
	public void generate() {
		
		try {
			JSONObject jsonObject = new JSONObject(readDirections());
			
			if(jsonObject.getString("status").contains("OK")) {
				
				JSONArray jsonArray = jsonObject.getJSONArray("routes");
				jsonObject = jsonArray.getJSONObject(0);
				
				String summary = "Walking via ";
				summary += jsonObject.getString("summary");
				setSummary(summary);
				
				jsonArray = jsonObject.getJSONArray("legs");
				jsonObject = jsonArray.getJSONObject(0);
				
				setDistance(jsonObject.getJSONObject("distance").get("text").toString());
				setDuration(jsonObject.getJSONObject("duration").get("text").toString());
				
				
				Address startAddress = jsonObjectToAddress(jsonObject.getJSONObject("start_location"));
				startAddress.setLocation(jsonObject.getString("start_address"));
				setStartLocation(startAddress);
				
				Address endAddress = jsonObjectToAddress(jsonObject.getJSONObject("end_location"));
				endAddress.setLocation(jsonObject.getString("end_address"));
				setEndLocation(endAddress);
				
				JSONArray steps = jsonObject.getJSONArray("steps");
				
				List<Stage> listStages = new ArrayList<Stage>();
				
				for(int i = 0; i < steps.length(); i++) {
					Stage stage = new Stage();
					
					JSONObject step = (JSONObject) steps.get(i);
					
					Address start = jsonObjectToAddress(step.getJSONObject("start_location"));
					Address end = jsonObjectToAddress(step.getJSONObject("end_location"));
					
					stage.setInstructions(step.getString("html_instructions").toString());
					
					stage.setDistance(step.getJSONObject("distance").getString("text"));
					stage.setDuration(step.getJSONObject("duration").getString("text"));
					stage.setStartLocation(start);
					stage.setEndLocation(end);
					
					listStages.add(stage);
				}
				
				if(listStages.size()!=0) {
					setStages(listStages);
				}
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

    /**
     * This method is used to read the directions
     * from the google directions web service
     * @return
     */
	private String readDirections() {
        String lat1, lat2, lon1, lon2;
        lat1 = Double.toString(GeoLocation.microDegreesToDegrees(getStartLocation().getLatitude()));
        lat2 = Double.toString(GeoLocation.microDegreesToDegrees(getEndLocation().getLatitude()));
        lon1 = Double.toString(GeoLocation.microDegreesToDegrees(getStartLocation().getLongitude()));
        lon2 = Double.toString(GeoLocation.microDegreesToDegrees(getEndLocation().getLongitude()));
        
        StringBuilder url = new StringBuilder();
        url.append("http://maps.googleapis.com/maps/api/directions/json?origin=")
        	.append(lat1).append(",").append(lon1).append("&destination=")
        	.append(lat2).append(",").append(lon2).append("&mode=walking&sensor=true");

        HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url.toString());
		
		StringBuilder builder = new StringBuilder();
		
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
    }
	
	/**
	 * This method is used to convert a JSONObject to 
	 * an address.
	 * @param obj
	 * @return
	 */
	private Address jsonObjectToAddress(JSONObject obj) {
		Address address = new Address();
		
		try {
			address.setLatitude(obj.getString("lat").toString());
			address.setLongitude(obj.getString("lng").toString());
			address.setLocation("");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return address;
	}
	
}
