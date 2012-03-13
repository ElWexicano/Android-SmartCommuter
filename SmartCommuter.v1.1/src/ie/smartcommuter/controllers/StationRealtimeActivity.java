package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StationRealtimeActivity extends SmartTabContentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_realtime);
        
        String[] recentlyViewedStations = getResources().getStringArray(R.array.stationsListExample);
        
        ListView arrivals = (ListView)findViewById(R.id.arrivalsListView);
        ListView departures = (ListView)findViewById(R.id.departuresListView);

        arrivals.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recentlyViewedStations));
        arrivals.setTextFilterEnabled(true);
        
        departures.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recentlyViewedStations));
        departures.setTextFilterEnabled(true);
    }
}
