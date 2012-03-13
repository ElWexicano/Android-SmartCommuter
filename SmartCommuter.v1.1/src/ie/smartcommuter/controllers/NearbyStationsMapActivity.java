package ie.smartcommuter.controllers;

import com.google.android.maps.MapView;

import ie.smartcommuter.R;
import android.os.Bundle;

public class NearbyStationsMapActivity extends SmartMapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
    }

}
