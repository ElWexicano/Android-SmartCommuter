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
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * This is a class is used to display a Nearby Stations
 * on a Google Map.
 * @author Shane Bryan Doyle
 */
public class NearbyStationsMapActivity extends SmartMapActivity {
	
	private Address userLocation;
	private List<Station> nearbyStations;
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_google);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        userLocation = (Address) bundle.getSerializable("userLocation");
        nearbyStations = (List<Station>) bundle.getSerializable("nearbyStations");
    }

	@Override
	protected void onResume() {
		super.onResume();
		
        mapView = initGoogleMap(userLocation);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Drawable drawable = getUserDrawable(prefs.getString("googleMapUserIconType", "male"));
        
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