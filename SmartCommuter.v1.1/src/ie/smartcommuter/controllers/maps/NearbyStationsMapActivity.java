package ie.smartcommuter.controllers.maps;

import java.util.List;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;
import ie.smartcommuter.controllers.SmartOverlay;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.Station;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        
        Drawable drawable = getUserDrawable();
        
        SmartOverlay overlay = new SmartOverlay(this, drawable);
        
        OverlayItem overlayItem = new OverlayItem(userLocation.toGeoPoint(), "User Location", "");

        overlay.addOverlay(overlayItem);
        
        if(nearbyStations!=null) {
        	
        	for(Station station : nearbyStations) {
        		drawable = overlay.getStationMarker(station.getCompany().getMode());
        		overlayItem = new OverlayItem(station.getAddress().toGeoPoint(), "Station", Integer.toString(station.getId()));
        		overlayItem.setMarker(drawable);
        		overlay.addOverlay(overlayItem);
        	}
        	
        }
        
        mapOverlays.add(overlay);
    }
}