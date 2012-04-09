package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.RealtimeArrayAdapter;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Station;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

/**
 * This is a class is used to display the realtime
 * information for the station.
 * @author Shane Bryan Doyle
 */
public class StationRealtimeActivity extends SmartTabContentActivity {
	
	private Station station;
	private ListView arrivals;
	private ListView departures;
	private RealtimeArrayAdapter arrivalsAdapter;
	private RealtimeArrayAdapter departuresAdapter;
	private int realtimeRefreshInterval;
	private SharedPreferences prefs;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_realtime);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        station = (Station) bundle.getSerializable("station");

        arrivals = (ListView)findViewById(R.id.arrivalsListView);
        departures = (ListView)findViewById(R.id.departuresListView);
        
        arrivals.setTextFilterEnabled(false);
        departures.setTextFilterEnabled(false);
    }

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		realtimeRefreshInterval = Integer.parseInt(prefs.getString("realtimeRefreshInterval", "30000"));
		
		// TODO: Create a thread that runs every some seconds refreshing the RealTime Data.
		
        station.getRealTimeData();
        arrivalsAdapter = new RealtimeArrayAdapter(this, station.getArrivals());
        arrivals.setAdapter(arrivalsAdapter);
        
        departuresAdapter = new RealtimeArrayAdapter(this, station.getDepartures());
        departures.setAdapter(departuresAdapter);
	}
	
}




