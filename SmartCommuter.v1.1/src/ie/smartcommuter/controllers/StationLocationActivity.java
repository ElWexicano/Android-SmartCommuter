package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import com.google.android.maps.MapView;
import android.os.Bundle;

public class StationLocationActivity extends SmartMapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
    }

}

