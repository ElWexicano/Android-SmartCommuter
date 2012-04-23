package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.DirectionArrayAdapter;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.Directions;
import ie.smartcommuter.models.Station;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
	private Dialog dialog;
	private Directions directions;
	private ListView directionsList;
	private DirectionArrayAdapter directionsAdapter;
	private Context context;
	private Handler handler;
	private Runnable runnable;
	private Boolean hideProgressBar;
	private ProgressBar progressBar;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_direction);
        dialog = new Dialog(this);
        context = this;
        handler = new Handler();
        hideProgressBar = false;
        
        directionsList = (ListView) findViewById(R.id.directionsListView);
        directionsList.setEmptyView(findViewById(R.id.directionsListEmpty));
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        station = (Station) bundle.getSerializable("station");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	openGPSDialog();
        } else {

        	locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    		runnable = updateDirectionsRunnable();
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 180000, 200, this);
            
        	if(dialog.isShowing()) {
        		dialog.dismiss();
        	}

    		new Thread(runnable).start();
        } 
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
		handler.removeCallbacks(runnable);
	}

	@Override
	public void onLocationChanged(Location loc) { 
		location = loc;
		new Thread(runnable).start();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "GPS Disabled",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "GPS Enabled",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	
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
    
    /**
     * This method is used to run a thread that updates
     * the directions on the screen.
     * @return
     */
    private synchronized Runnable updateDirectionsRunnable() {
    	Runnable runnable = new Runnable() {

			@Override
			public void run() {
				
				handler.post(new Runnable() {

					@Override
					public void run() {
						
						if(location!=null) {
							address = new Address(location);
							
					        directions = new Directions();
					        directions.setStartLocation(address);
					        directions.setEndLocation(station.getAddress());
					        directions.generate();
						}

				        if(directions!=null) {
				        	
					        directionsAdapter = new DirectionArrayAdapter(context, directions.getStages());
					        
					        View header = getLayoutInflater().inflate(R.layout.row_directions_header, null, false);
					        TextView summary = (TextView) header.findViewById(R.id.directionSummary);
					        summary.setText(directions.getSummary());
					        TextView distance = (TextView) header.findViewById(R.id.directionDistance);
					        distance.setText(directions.getDistance());
					        TextView duration = (TextView) header.findViewById(R.id.directionDuration);
					        duration.setText(directions.getDuration());
					        
					        if(directionsList.getHeaderViewsCount()==0){
					        	directionsList.addHeaderView(header);
					        }
					        
					        int numberOfStages = 0;
					        
					        if(directions.getStages()!=null) {
					        	numberOfStages = directions.getStages().size();
					        }
					        
					        if(numberOfStages>0) {
					        	directionsList.setAdapter(directionsAdapter);
					        }
				        	
				        }
				        
				        if(!hideProgressBar) {
				        	updateEmptyListMessage();
				        }
					}
				});
			}
    	};
    	
		return runnable;
    }

    /**
     * This method is used to change the original loading message
     * to an empty list message.
     */
    private void updateEmptyListMessage() {
    	progressBar = (ProgressBar) findViewById(R.id.directionsProgressBar);
    	progressBar.setVisibility(View.INVISIBLE);
    	
    	TextView directionsListEmpty = (TextView) findViewById(R.id.directionsListEmpty);
    	directionsListEmpty.setText(R.string.directionsListEmptyMessage);
    	
    	hideProgressBar = true;
    }
    
}
