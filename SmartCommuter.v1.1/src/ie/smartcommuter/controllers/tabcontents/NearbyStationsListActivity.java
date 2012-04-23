package ie.smartcommuter.controllers.tabcontents;

import java.util.ArrayList;
import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.controllers.screens.NearbyStationsActivity;
import ie.smartcommuter.models.Station;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * This is a class that is used to display
 * the nearby stations in a list.
 * @author Shane Bryan Doyle
 */
public class NearbyStationsListActivity extends SmartTabContentActivity {
	
	private Context context;
	private StationArrayAdapter listAdapter;
	private ListView nearbyStationsList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_nearby_list);
        
        context = this;
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        @SuppressWarnings("unchecked")
		List<Station> nearbyStations = (List<Station>) bundle.getSerializable("nearbyStations");
        
        if(nearbyStations==null) {
        	nearbyStations = new ArrayList<Station>();
        }
        
        listAdapter = new StationArrayAdapter(this, nearbyStations);
        
        nearbyStationsList = (ListView)findViewById(R.id.nearbyStationsList);
        nearbyStationsList.setOnItemClickListener(new StationItemListener());
        nearbyStationsList.setAdapter(listAdapter);
        nearbyStationsList.setEmptyView(findViewById(R.id.nearbyListEmpty));
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		
		if(NearbyStationsActivity.nearbyStations!=null) {
			updateNearbyStations(NearbyStationsActivity.nearbyStations);
		}
	}

	/**
     * This method is used to update the nearest stations list.
     * @param stations
     */
    public void updateNearbyStations(List<Station> stations) {
		listAdapter = new StationArrayAdapter(context, stations);
		nearbyStationsList.setAdapter(listAdapter);
    }
    
}
