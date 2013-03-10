package ie.smartcommuter.controllers.maps;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartMapActivity;
import ie.smartcommuter.controllers.SmartOverlay;
import ie.smartcommuter.controllers.screens.NearbyStationsActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.Station;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * This is a class is used to display a Nearby Stations
 * on a Google Map.
 * @author Shane Doyle
 */
public class NearbyStationsMapActivity extends SmartMapActivity {
	
	private Address mUserLocation;
	private Context mContext;
	private Drawable mDrawable;
	private List<Overlay> mMapOverlays;
	private List<Station> mNearbyStations;
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_google);
        mContext = this;
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        mUserLocation = (Address) bundle.getSerializable("userLocation");
        mNearbyStations = (List<Station>) bundle.getSerializable("nearbyStations");
    }

	@Override
	protected void onResume() {
		super.onResume();
		
        if(NearbyStationsActivity.nearbyStations!=null && NearbyStationsActivity.address!=null) {
        	mUserLocation = NearbyStationsActivity.address;
        	mNearbyStations = NearbyStationsActivity.nearbyStations;
        }
        
        if(mNearbyStations==null) {
        	mNearbyStations = new ArrayList<Station>();
        }
		
		if(mUserLocation!=null) {
			updateNearbyStations(mNearbyStations,mUserLocation);
		}
		
	}

	/**
	 * This method is used to update the nearest stations map.
	 * @param stations
	 * @param address
	 */
    public void updateNearbyStations(List<Station> stations, Address address) {
    	mUserLocation = address;
    	mNearbyStations = stations;
    	
        mapView = initGoogleMap(mUserLocation);
        mMapOverlays = mapView.getOverlays();
        mMapOverlays.clear();
        SmartOverlay overlay = drawMapOverlays();
        mMapOverlays.add(overlay);
    }
	
    /**
     * This method is used to draw the overlays on the map.
     * @return
     */
	private SmartOverlay drawMapOverlays() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mDrawable = getUserDrawable(prefs.getString("googleMapUserIconType", "male"));
		
		SmartOverlay overlay = new SmartOverlay(mContext, mDrawable);
        
        OverlayItem overlayItem = new OverlayItem(mUserLocation.toGeoPoint(), "User Location", "");

        overlay.addOverlay(overlayItem);
        
        if(mNearbyStations!=null) {
        	
        	for(Station station : mNearbyStations) {
        		mDrawable = overlay.getStationMarker(station.getCompany().getMode());
        		overlayItem = new OverlayItem(station.getAddress().toGeoPoint(), "Station", Integer.toString(station.getId()));
        		overlayItem.setMarker(mDrawable);
        		overlay.addOverlay(overlayItem);
        	}
        	
        }
		return overlay;
	}
 
}