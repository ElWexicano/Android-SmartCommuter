package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.GeoLocation;
import ie.smartcommuter.models.Station;
import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This is a class is used to display the directions
 * to the station from the users location.
 * @author Shane Bryan Doyle
 */
public class StationDirectionsActivity extends SmartTabContentActivity implements LocationListener {
	
	private LocationManager locationManager;
	private Station station;
	private Address address;
	private Location location;
	private String provider;
	private Dialog dialog;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_direction);
        dialog = new Dialog(this);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        station = (Station) bundle.getSerializable("station");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	openGPSDialog();
        }
        
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        
        if(location==null) {
        	Toast.makeText(this, "Couldn't locate a provider to find your location", Toast.LENGTH_LONG).show();
        	finish();
        } else {
            address = new Address(location);
            
            String lat1, lat2, lon1, lon2;
            lat1 = Double.toString(GeoLocation.microDegreesToDegrees(address.getLatitude()));
            lat2 = Double.toString(GeoLocation.microDegreesToDegrees(station.getAddress().getLongitude()));
            lon1 = Double.toString(GeoLocation.microDegreesToDegrees(address.getLatitude()));
            lon2 = Double.toString(GeoLocation.microDegreesToDegrees(station.getAddress().getLongitude()));
            
//            String url = "http://maps.google.com/maps?saddr="+lat1+","+lon1+"&daddr="+lat2+","+lon2;
//            intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
//            startActivity(intent);
        }
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
        }
        
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
        address = new Address(location);
        
        // TODO: Update the directions
	}

	@Override
	public void onProviderDisabled(String arg0) {}

	@Override
	public void onProviderEnabled(String arg0) {}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
	
    /**
     * This method is used to display the GPS Dialog
     * when the GPS is turned off.
     */
    private void openGPSDialog() {
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
    		this.operationId = id;
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
