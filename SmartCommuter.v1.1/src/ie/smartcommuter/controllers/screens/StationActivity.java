package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabActivity;
import ie.smartcommuter.controllers.maps.StationLocationActivity;
import ie.smartcommuter.controllers.tabcontents.StationDirectionsActivity;
import ie.smartcommuter.controllers.tabcontents.StationRealtimeActivity;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * This activity provides functionality to the
 * Station Details screen of the application.
 * @author Shane Bryan Doyle
 */
public class StationActivity extends SmartTabActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent i = getIntent();
        Bundle info = i.getExtras();
        int stationId = info.getInt("stationId");

        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.open();
        Station station = databaseManager.getStation(stationId);
        databaseManager.addToRecentlyViewedStations(stationId);
        databaseManager.close();
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.screen_station);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
        TextView stationNameTextView = (TextView) findViewById(R.id.stationNameTextView);
        TextView stationAddressTextView = (TextView) findViewById(R.id.stationAddressTextView);
        stationNameTextView.setText(station.getName());
        stationAddressTextView.setText(station.getAddress().getLocation());
        
        // TODO: Add the favourite stations feature.
        
        Bundle activityInfo = new Bundle();
        activityInfo.putSerializable("station", station);
        
        tabHost = getTabHost();
        addTab(StationRealtimeActivity.class, activityInfo, "Realtime");
        addTab(StationDirectionsActivity.class, activityInfo, "Directions");
        addTab(StationLocationActivity.class, activityInfo, "Location");
        tabHost.setCurrentTab(0);
    }

}
