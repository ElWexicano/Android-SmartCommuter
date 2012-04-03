package ie.smartcommuter.controllers.maps;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;
import ie.smartcommuter.models.Station;

import android.content.Intent;
import android.os.Bundle;

/**
 * This is a class is used to display the location
 * of a Station on a Google Map.
 * @author Shane Bryan Doyle
 */
public class StationLocationActivity extends SmartMapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_google);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        Station station = (Station) bundle.getSerializable("station");
        
        mapView = initGoogleMap(station.getAddress());
        
        // TODO: Show the station location on the map.
    }
}