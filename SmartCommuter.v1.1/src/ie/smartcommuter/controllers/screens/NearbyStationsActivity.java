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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;

/**
 * This class is used for the Nearby Stations Screen
 * of the Application.
 * @author Shane Bryan Doyle
 */
public class NearbyStationsActivity extends SmartTabActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.screen_nearby);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	// TODO: Display a AlarmDialog prompt that asks the user if they want to enable GPS or not use this feature!
        }

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
        Address address = new Address(location);
        
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.open();
        List<Station> nearbyStations = databaseManager.getNearbyStations(address);
        databaseManager.close();
        
        Bundle activityInfo = new Bundle();
        activityInfo.putSerializable("nearbyStations", (Serializable) nearbyStations);
        activityInfo.putSerializable("userLocation", (Serializable) address);
        
        tabHost = getTabHost();
        addTab(NearbyStationsListActivity.class, activityInfo, "List");
        addTab(NearbyStationsMapActivity.class, activityInfo, "Map");
        tabHost.setCurrentTab(0);
    }
}
