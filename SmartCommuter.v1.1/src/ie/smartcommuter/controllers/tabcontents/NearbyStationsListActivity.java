package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.controllers.screens.NearbyStationsActivity;
import ie.smartcommuter.models.Station;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * This is a class that is used to display the nearby stations in a list.
 * 
 * @author Shane Doyle
 */
public class NearbyStationsListActivity extends SmartTabContentActivity {

	private Context mContext;
	private StationArrayAdapter mListAdapter;
	private ListView mNearbyStationsList;
	private Boolean mHideProgressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_nearby_list);

		mContext = this;
		mHideProgressBar = false;

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		@SuppressWarnings("unchecked")
		List<Station> nearbyStations = (List<Station>) bundle
				.getSerializable("nearbyStations");

		if (nearbyStations == null) {
			nearbyStations = new ArrayList<Station>();
		}

		mListAdapter = new StationArrayAdapter(this, nearbyStations);

		mNearbyStationsList = (ListView) findViewById(R.id.nearbyStationsList);
		mNearbyStationsList.setOnItemClickListener(new StationItemListener());
		mNearbyStationsList.setAdapter(mListAdapter);
		mNearbyStationsList.setEmptyView(findViewById(R.id.nearbyListEmpty));
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (NearbyStationsActivity.nearbyStations != null) {
			updateNearbyStations(NearbyStationsActivity.nearbyStations);
		}
	}

	/**
	 * This method is used to update the nearest stations list.
	 * 
	 * @param stations
	 */
	public void updateNearbyStations(List<Station> stations) {
		mListAdapter = new StationArrayAdapter(mContext, stations);
		mNearbyStationsList.setAdapter(mListAdapter);

		if (mHideProgressBar) {
			mHideProgressBar = updateEmptyListMessage(
					R.id.nearbyStationsProgressBar, R.id.nearbyListEmpty,
					R.string.nearby_list_empty_message);
		}
	}
}
