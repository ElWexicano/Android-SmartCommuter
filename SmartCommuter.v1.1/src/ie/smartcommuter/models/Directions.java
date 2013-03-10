package ie.smartcommuter.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
 * This class is used to store directions for a journey.
 * 
 * @author Shane Doyle
 */
public class Directions {
	private String mDistance;
	private String mDuration;
	private Address mStartLocation;
	private Address mEndLocation;
	private List<Stage> mStages;
	private String mSummary;
	private String mMode = Constants.DIRECTIONS_MODE;

	public Directions() {
	}

	public String getDistance() {
		return mDistance;
	}

	public void setDistance(String distance) {
		this.mDistance = distance;
	}

	public String getDuration() {
		return mDuration;
	}

	public void setDuration(String duration) {
		this.mDuration = duration;
	}

	public Address getStartLocation() {
		return mStartLocation;
	}

	public void setStartLocation(Address startLocation) {
		this.mStartLocation = startLocation;
	}

	public Address getEndLocation() {
		return mEndLocation;
	}

	public void setEndLocation(Address endLocation) {
		this.mEndLocation = endLocation;
	}

	public List<Stage> getStages() {
		return mStages;
	}

	public void setStages(List<Stage> stages) {
		this.mStages = stages;
	}

	public String getSummary() {
		return mSummary;
	}

	public void setSummary(String summary) {
		this.mSummary = summary;
	}

	public String getMode() {
		return mMode;
	}

	public void setMode(String mode) {
		this.mMode = mode;
	}

	/**
	 * This method is used to generate the directions for the address.
	 */
	public void generate() {

		try {
			JSONObject jsonObject = new JSONObject(readDirections());

			if (jsonObject.getString(Constants.STATUS).contains(Constants.OK)) {

				JSONArray jsonArray = jsonObject.getJSONArray(Constants.ROUTES);
				jsonObject = jsonArray.getJSONObject(0);

				StringBuilder summary = new StringBuilder(getMode());
				summary.append(" ").append(Constants.VIA).append(" ");
				summary.append(jsonObject.getString(Constants.SUMMARY));
				setSummary(summary.toString());

				jsonArray = jsonObject.getJSONArray(Constants.LEGS);
				jsonObject = jsonArray.getJSONObject(0);

				setDistance(jsonObject.getJSONObject(Constants.DISTANCE)
						.get(Constants.TEXT).toString());
				setDuration(jsonObject.getJSONObject(Constants.DURATION)
						.get(Constants.TEXT).toString());

				Address startAddress = jsonObjectToAddress(jsonObject
						.getJSONObject(Constants.START_LOCATION));
				startAddress.setLocation(jsonObject
						.getString(Constants.START_ADDRESS));
				setStartLocation(startAddress);

				Address endAddress = jsonObjectToAddress(jsonObject
						.getJSONObject(Constants.END_LOCATION));
				endAddress.setLocation(jsonObject
						.getString(Constants.END_ADDRESS));
				setEndLocation(endAddress);

				JSONArray steps = jsonObject.getJSONArray(Constants.STEPS);

				List<Stage> listStages = new ArrayList<Stage>();

				for (int i = 0; i < steps.length(); i++) {
					Stage stage = new Stage();

					JSONObject step = (JSONObject) steps.get(i);

					Address start = jsonObjectToAddress(step
							.getJSONObject(Constants.START_LOCATION));
					Address end = jsonObjectToAddress(step
							.getJSONObject(Constants.END_LOCATION));

					stage.setInstructions(step.getString(
							Constants.HTML_INSTRUCTIONS).toString());

					stage.setDistance(step.getJSONObject(Constants.DISTANCE)
							.getString(Constants.TEXT));
					stage.setDuration(step.getJSONObject(Constants.DURATION)
							.getString(Constants.TEXT));
					stage.setStartLocation(start);
					stage.setEndLocation(end);

					listStages.add(stage);
				}
				if (listStages.size() != 0) {
					setStages(listStages);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to read the directions from the google directions web
	 * service
	 * 
	 * @return
	 */
	private String readDirections() {
		String lat1, lat2, lon1, lon2;
		lat1 = Double.toString(GeoLocation
				.microDegreesToDegrees(getStartLocation().getLatitude()));
		lat2 = Double.toString(GeoLocation
				.microDegreesToDegrees(getEndLocation().getLatitude()));
		lon1 = Double.toString(GeoLocation
				.microDegreesToDegrees(getStartLocation().getLongitude()));
		lon2 = Double.toString(GeoLocation
				.microDegreesToDegrees(getEndLocation().getLongitude()));

		StringBuilder url = new StringBuilder();
		url.append(Constants.URLs.GOOGLE_DIRECTIONS_API).append(lat1)
				.append(",").append(lon1).append("&destination=").append(lat2)
				.append(",").append(lon2).append("&mode=")
				.append(getMode().toLowerCase(Locale.ENGLISH))
				.append("&sensor=true");

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
	 * This method is used to convert a JSONObject to an address.
	 * 
	 * @param obj
	 * @return
	 */
	private Address jsonObjectToAddress(JSONObject obj) {
		Address address = new Address();

		try {
			address.setLatitude(obj.getString(Constants.LAT).toString());
			address.setLongitude(obj.getString(Constants.LNG).toString());
			address.setLocation("");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return address;
	}

}
