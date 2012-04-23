package ie.smartcommuter.controllers.screens;

import java.io.Serializable;
import java.util.List;
import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabActivity;
import ie.smartcommuter.controllers.maps.NearbyStationsMapActivity;
import ie.smartcommuter.controllers.tabcontents.NearbyStationsListActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This class is used for the Nearby Stations Screen
 * of the Application.
 * @author Shane Bryan Doyle
 */
public class NearbyStationsActivity extends SmartTabActivity implements LocationListener{

	private DatabaseManager databaseManager;
	public static List<Station> nearbyStations;
	private LocationManager locationManager;
	private Location location;
	public static Address address;
	private Bundle activityInfo;
	private Dialog dialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.screen_nearby);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        dialog = new Dialog(this);
        
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location!=null) {
            getNearbyStations();
        }
        
        activityInfo = new Bundle();
        activityInfo.putSerializable("nearbyStations", (Serializable) nearbyStations);
        activityInfo.putSerializable("userLocation", (Serializable) address);
        
        tabHost = getTabHost();
        addTab(NearbyStationsListActivity.class, activityInfo, "List");
        addTab(NearbyStationsMapActivity.class, activityInfo, "Map");
        tabHost.setCurrentTab(0);
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	openGPSDialog();
        } else {
        	if(dialog.isShowing()) {
        		dialog.dismiss();
        	}
        	
        	locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        	location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 180000, 200, this);
        	
        	if(location!=null) {
                updateNearbyStationTabs();
        	}
        }
	
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location loc) {
		location = loc;
        updateNearbyStationTabs();
	}

	@Override
	public void onProviderDisabled(String arg0) { 
		Toast.makeText(this, "GPS Disabled",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String arg0) { 
		Toast.makeText(this, "GPS Enabled",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) { }
	
	/**
	 * This method is used to get nearby stations.
	 */
	protected void getNearbyStations() {
        address = new Address(location);
        
        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        nearbyStations = databaseManager.getNearbyStations(address);
        databaseManager.close();
	}
	
	/**
	 * This method is used to update the users location and
	 * to get stations that are near the users location.
	 */
	protected void updateNearbyStationTabs() {
		getNearbyStations();
        
        if(getLocalActivityManager().getActivity("List")!=null) {
            Activity nearbyStationsListActivity = getLocalActivityManager().getActivity("List");
            ((NearbyStationsListActivity) nearbyStationsListActivity).updateNearbyStations(nearbyStations);
        }
        
        if(getLocalActivityManager().getActivity("Map")!=null) {
            Activity nearbyStationsMapActivity = getLocalActivityManager().getActivity("Map");
            ((NearbyStationsMapActivity) nearbyStationsMapActivity).updateNearbyStations(nearbyStations, address);
        }
	}
	
    /**
     * This method is used to display the GPS Dialog
     * when the GPS is turned off.
     */
    protected void openGPSDialog() {
		dialog.setTitle(R.string.gpsAlertTitle);
		dialog.setContentView(R.layout.dialog_gps);
		
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		
		Button enableGPSButton = (Button) dialog.findViewById(R.id.enableGPSButton);
		Button dontUseFeatureButton = (Button) dialog.findViewById(R.id.dontUseButton);
		enableGPSButton.setOnClickListener(new GPSDialogButtonListener(0));
		dontUseFeatureButton.setOnClickListener(new GPSDialogButtonListener(1));
    }
    
    /**
     * This class is used to either direct the user
     * to the Enable GPS screen or the previous
     * activity.
     * @author Shane Bryan Doyle
     */
    private class GPSDialogButtonListener implements OnClickListener {
    	
    	int operationId;
    	
    	public GPSDialogButtonListener(int id) {
    		operationId = id;
    	}
    	
		@Override
		public void onClick(View arg0) {
			dialog.dismiss();
			
			Intent intent = null;
			
			switch(operationId) {
			case 0:
				intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
				break;
			case 1: 
				finish();
				break;
			}
		}
    }
}
