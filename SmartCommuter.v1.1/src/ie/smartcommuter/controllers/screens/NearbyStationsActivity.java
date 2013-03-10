package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabActivity;
import ie.smartcommuter.controllers.maps.NearbyStationsMapActivity;
import ie.smartcommuter.controllers.tabcontents.NearbyStationsListActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;
import ie.smartcommuter.models.Utilities;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * This class is used for the Nearby Stations Screen
 * of the Application.
 * @author Shane Doyle
 */
public class NearbyStationsActivity extends SmartTabActivity implements LocationListener{

	private DatabaseManager mDatabaseManager;
	public static List<Station> nearbyStations;
	private LocationManager mLocationManager;
	private Location mLocation;
	public static Address address;
	private Bundle mActivityInfo;
	private Dialog mDialog;
	private String mProvider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.screen_nearby);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        mDialog = new Dialog(this);
        
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        Criteria criteria = new Criteria();
		mProvider = mLocationManager.getBestProvider(criteria, false);
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(mLocation!=null) {
            getNearbyStations();
        }
        
        mActivityInfo = new Bundle();
        mActivityInfo.putSerializable("nearbyStations", (Serializable) nearbyStations);
        mActivityInfo.putSerializable("userLocation", (Serializable) address);
        
        tabHost = getTabHost();
        addTab(NearbyStationsListActivity.class, mActivityInfo, "List");
        addTab(NearbyStationsMapActivity.class, mActivityInfo, "Map");
        tabHost.setCurrentTab(0);
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		
    	mLocationManager.requestLocationUpdates(mProvider, 180000, 200, this);
    	
    	if(mLocation!=null) {
            updateNearbyStationTabs();
    	}
	
	}

	@Override
	protected void onPause() {
		super.onPause();
		mLocationManager.removeUpdates(this);
	}

	public void onLocationChanged(Location loc) {
		if(Utilities.isBetterLocation(loc, mLocation)) {
			mLocation = loc;
	        updateNearbyStationTabs();
		}
	}

	public void onProviderDisabled(String arg0) { 
		Toast.makeText(this, "GPS Disabled",Toast.LENGTH_SHORT).show();
	}

	public void onProviderEnabled(String arg0) { 
		Toast.makeText(this, "GPS Enabled",Toast.LENGTH_SHORT).show();
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) { }
	
	/**
	 * This method is used to get nearby stations.
	 */
	protected void getNearbyStations() {
        address = new Address(mLocation);
        
        mDatabaseManager = new DatabaseManager(this);
        mDatabaseManager.open();
        nearbyStations = mDatabaseManager.getNearbyStations(address);
        mDatabaseManager.close();
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
		mDialog.setTitle(R.string.gps_alert_title);
		mDialog.setContentView(R.layout.dialog_gps);
		
		mDialog.setCancelable(false);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.show();
		
		Button enableGPSButton = (Button) mDialog.findViewById(R.id.enableGPSButton);
		Button dontUseFeatureButton = (Button) mDialog.findViewById(R.id.dontUseButton);
		enableGPSButton.setOnClickListener(new GPSDialogButtonListener(0));
		dontUseFeatureButton.setOnClickListener(new GPSDialogButtonListener(1));
    }
    
    /**
     * This class is used to either direct the user
     * to the Enable GPS screen or the previous
     * activity.
     * @author Shane Doyle
     */
    private class GPSDialogButtonListener implements OnClickListener {
    	
    	private int mOperationID;
    	
    	public GPSDialogButtonListener(int id) {
    		mOperationID = id;
    	}
    	
		public void onClick(View arg0) {
			mDialog.dismiss();
			
			Intent intent = null;
			
			switch(mOperationID) {
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