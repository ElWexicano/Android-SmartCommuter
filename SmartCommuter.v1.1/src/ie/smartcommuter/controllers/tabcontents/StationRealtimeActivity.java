package ie.smartcommuter.controllers.tabcontents;

import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.client.RealTimeClient;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Station;
import ie.smartcommuter.models.StationData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * This is a class is used to display the realtime
 * information for the station.
 * @author Shane Bryan Doyle
 */
public class StationRealtimeActivity extends SmartTabContentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_realtime);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        Station station = (Station) bundle.getSerializable("station");

//        List<StationData> stationData = RealTimeClient.getRealTimeData(station);
        
        
        // TODO: Get the real time data for the station.
        
        String[] recentlyViewedStations = getResources().getStringArray(R.array.realtimeExample);
        
        ListView arrivals = (ListView)findViewById(R.id.arrivalsListView);
        ListView departures = (ListView)findViewById(R.id.departuresListView);

        arrivals.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recentlyViewedStations));
        arrivals.setTextFilterEnabled(true);
        
        departures.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recentlyViewedStations));
        departures.setTextFilterEnabled(true);
    }
}
