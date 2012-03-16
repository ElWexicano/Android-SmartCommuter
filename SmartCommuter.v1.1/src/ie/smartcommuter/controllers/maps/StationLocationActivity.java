package ie.smartcommuter.controllers.maps;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;

import com.google.android.maps.MapView;
import android.os.Bundle;

public class StationLocationActivity extends SmartMapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_google);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        // TODO: Show the station location on the map.
    }

}

