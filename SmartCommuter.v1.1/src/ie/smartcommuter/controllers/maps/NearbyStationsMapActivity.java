package ie.smartcommuter.controllers.maps;

import com.google.android.maps.MapView;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;
import android.os.Bundle;

public class NearbyStationsMapActivity extends SmartMapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_google);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
    }

}
