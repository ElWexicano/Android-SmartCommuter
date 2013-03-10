package ie.smartcommuter.controllers.maps;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;
import ie.smartcommuter.controllers.SmartOverlay;
import ie.smartcommuter.models.Station;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * This is a class is used to display the location
 * of a Station on a Google Map.
 * @author Shane Doyle
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
        mapView.getOverlays().clear();
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = getStationDrawable(station.getCompany().getMode());

        SmartOverlay stationOverlay = new SmartOverlay(this, drawable);
        
        OverlayItem overlayitem = new OverlayItem(station.getAddress().toGeoPoint(), "Station Location", "");
        
        stationOverlay.addOverlay(overlayitem);
        mapOverlays.add(stationOverlay);
    }


}