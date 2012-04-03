package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.Station;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * This is a class is used to display the directions
 * to the station from the users location.
 * @author Shane Bryan Doyle
 */
public class StationDirectionsActivity extends SmartTabContentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_direction);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        Station station = (Station) bundle.getSerializable("station");
        
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	
        	AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        	
        	String message = getString(R.string.gpsAlertMessage);
        	
        	alertDialog.setTitle(R.string.gpsAlertTitle);
        	alertDialog.setMessage(message);
        	
        	alertDialog.show();
        }

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
        Address address = new Address(location);
        
        // TODO: Get the directions to the station.
    }

    
}
