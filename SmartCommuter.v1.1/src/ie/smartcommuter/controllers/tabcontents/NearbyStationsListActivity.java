package ie.smartcommuter.controllers.tabcontents;

import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.models.Station;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * This is a class that is used to display
 * the nearby stations in a list.
 * @author Shane Bryan Doyle
 */
public class NearbyStationsListActivity extends SmartTabContentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_nearby_list);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        @SuppressWarnings("unchecked")
		List<Station> nearbyStations = (List<Station>) bundle.getSerializable("nearbyStations");
        
        if(nearbyStations!=null) {
            StationArrayAdapter listAdapter = new StationArrayAdapter(this, nearbyStations);
            
            ListView favouriteStationsList = (ListView)findViewById(R.id.nearbyStationsList);
            favouriteStationsList.setOnItemClickListener(new StationItemListener());
            favouriteStationsList.setAdapter(listAdapter);
            favouriteStationsList.setEmptyView(findViewById(R.id.nearbyListEmpty));
        }
    }
}
