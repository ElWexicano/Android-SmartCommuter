package ie.smartcommuter.controllers.maps;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;
import ie.smartcommuter.controllers.SmartOverlay;
import ie.smartcommuter.controllers.screens.NearbyStationsActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.Station;
import android.content.Context;
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
	private Context context;
	private Drawable drawable;
	private List<Overlay> mapOverlays;
	private List<Station> nearbyStations;
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_google);
        context = this;
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        userLocation = (Address) bundle.getSerializable("userLocation");
        nearbyStations = (List<Station>) bundle.getSerializable("nearbyStations");
        

    }

	@Override
	protected void onResume() {
		super.onResume();
		
        if(NearbyStationsActivity.nearbyStations!=null && NearbyStationsActivity.address!=null) {
        	userLocation = NearbyStationsActivity.address;
        	nearbyStations = NearbyStationsActivity.nearbyStations;
        }
        
        if(nearbyStations==null) {
        	nearbyStations = new ArrayList<Station>();
        }
		
		if(userLocation!=null) {
			updateNearbyStations(nearbyStations,userLocation);
		}
		
	}

    /**
     * This method is used to update the nearest stations map.
     * @param stations
     */
    public void updateNearbyStations(List<Station> stations, Address address) {
    	userLocation = address;
    	nearbyStations = stations;
    	
        mapView = initGoogleMap(userLocation);
        mapOverlays = mapView.getOverlays();
        mapOverlays.clear();
        SmartOverlay overlay = drawMapOverlays();
        mapOverlays.add(overlay);
    }
	
    /**
     * This method is used to draw the overlays on the map.
     * @return
     */
	private SmartOverlay drawMapOverlays() {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        drawable = getUserDrawable(prefs.getString("googleMapUserIconType", "male"));
		
		SmartOverlay overlay = new SmartOverlay(context, drawable);
        
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
		return overlay;
	}
 
}