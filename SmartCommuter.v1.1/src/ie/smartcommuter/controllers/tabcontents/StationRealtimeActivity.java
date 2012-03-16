package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StationRealtimeActivity extends SmartTabContentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_realtime);
        
        // TODO: Get the realtime data for the station.
        
        String[] recentlyViewedStations = getResources().getStringArray(R.array.realtimeExample);
        
        ListView arrivals = (ListView)findViewById(R.id.arrivalsListView);
        ListView departures = (ListView)findViewById(R.id.departuresListView);

        arrivals.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recentlyViewedStations));
        arrivals.setTextFilterEnabled(true);
        
        departures.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recentlyViewedStations));
        departures.setTextFilterEnabled(true);
    }
}
