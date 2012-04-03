package ie.smartcommuter.controllers.maps;

import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.Station;
import android.content.Intent;
import android.os.Bundle;

/**
 * This is a class is used to display a Nearby Stations
 * on a Google Map.
 * @author Shane Bryan Doyle
 */
public class NearbyStationsMapActivity extends SmartMapActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_google);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        Address userLocation = (Address) bundle.getSerializable("userLocation");
        @SuppressWarnings("unchecked")
		List<Station> nearbyStations = (List<Station>) bundle.getSerializable("nearbyStations");
        
        mapView = initGoogleMap(userLocation);
        
        if(nearbyStations!=null) {
        	System.out.println(nearbyStations);
        }
        
        // TODO: Show the users location and nearby stations

    }
}