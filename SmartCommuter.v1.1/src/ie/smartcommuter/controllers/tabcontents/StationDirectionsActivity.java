package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.DirectionArrayAdapter;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Address;
import ie.smartcommuter.models.Directions;
import ie.smartcommuter.models.Station;
import ie.smartcommuter.models.Utilities;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a class is used to display the directions to the station from the
 * users location.
 * 
 * @author Shane Doyle
 */
public class StationDirectionsActivity extends SmartTabContentActivity
		implements LocationListener {

	private LocationManager mLocationManager;
	private Station mStation;
	private Address mAddress;
	private Location mLocation;
	private Dialog mDialog;
	private Directions mDirections;
	private ListView mDirectionsList;
	private DirectionArrayAdapter mDirectionsAdapter;
	private SharedPreferences mPrefs;
	private Context mContext;
	private Handler mHandler;
	private Runnable mRunnable;
	private Boolean mHideProgressBar;
	private String mProvider;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_station_direction);
		mDialog = new Dialog(this);
		mContext = this;
		mHandler = new Handler();
		mHideProgressBar = false;

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		mDirectionsList = (ListView) findViewById(R.id.directionsListView);
		mDirectionsList.setEmptyView(findViewById(R.id.directionsListEmpty));

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		mStation = (Station) bundle.getSerializable("station");

		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		mProvider = mLocationManager.getBestProvider(criteria, false);
		mLocation = mLocationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mRunnable = updateDirectionsRunnable();
		mLocationManager.requestLocationUpdates(mProvider, 180000, 200, this);

		new Thread(mRunnable).start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mLocationManager.removeUpdates(this);
		mHandler.removeCallbacks(mRunnable);
	}

	public void onLocationChanged(Location loc) {
		if (Utilities.isBetterLocation(loc, mLocation)) {
			mLocation = loc;
			new Thread(mRunnable).start();
		}
	}

	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "GPS Disabled", Toast.LENGTH_SHORT).show();
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "GPS Enabled", Toast.LENGTH_SHORT).show();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * This method is used to display the GPS Dialog when the GPS is turned off.
	 */
	private void openGPSDialog() {
		mDialog.setTitle(R.string.gps_alert_title);
		mDialog.setContentView(R.layout.dialog_gps);

		mDialog.setCancelable(false);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.show();

		Button enableGPSButton = (Button) mDialog
				.findViewById(R.id.enableGPSButton);
		Button dontUseFeatureButton = (Button) mDialog
				.findViewById(R.id.dontUseButton);
		enableGPSButton.setOnClickListener(new GPSDialogButtonListener(0));
		dontUseFeatureButton.setOnClickListener(new GPSDialogButtonListener(1));
	}

	/**
	 * This class is used to either direct the user to the Enable GPS screen or
	 * the previous activity.
	 * 
	 * @author Shane Doyle
	 */
	private class GPSDialogButtonListener implements OnClickListener {

		private int mOperationID;

		public GPSDialogButtonListener(int id) {
			this.mOperationID = id;
		}

		public void onClick(View arg0) {
			mDialog.dismiss();

			Intent intent = null;

			switch (mOperationID) {
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
	 * This method is used to run a thread that updates the directions on the
	 * screen.
	 * 
	 * @return
	 */
	private synchronized Runnable updateDirectionsRunnable() {
		Runnable runnable = new Runnable() {

			public void run() {
				mHandler.post(new Runnable() {

					public void run() {
						if (mLocation != null) {
							mAddress = new Address(mLocation);

							mDirections = new Directions();
							mDirections.setStartLocation(mAddress);
							mDirections.setEndLocation(mStation.getAddress());
							mDirections.setMode(mPrefs.getString(
									"directionsMode", "walking"));
							mDirections.generate();
						}

						if (mDirections != null) {
							mDirectionsAdapter = new DirectionArrayAdapter(
									mContext, mDirections.getStages());

							View header = getLayoutInflater().inflate(
									R.layout.list_item_directions_header, null,
									false);
							TextView summary = (TextView) header
									.findViewById(R.id.directionSummary);
							summary.setText(mDirections.getSummary());
							TextView distance = (TextView) header
									.findViewById(R.id.directionDistance);
							distance.setText(mDirections.getDistance());
							TextView duration = (TextView) header
									.findViewById(R.id.directionDuration);
							duration.setText(mDirections.getDuration());

							if (mDirectionsList.getHeaderViewsCount() == 0) {
								mDirectionsList.addHeaderView(header);
							}

							int numberOfStages = 0;

							if (mDirections.getStages() != null) {
								numberOfStages = mDirections.getStages().size();
							}
							if (numberOfStages > 0) {
								mDirectionsList.setAdapter(mDirectionsAdapter);
							}
						}

						if (mHideProgressBar) {
							mHideProgressBar = updateEmptyListMessage(
									R.id.directionsProgressBar,
									R.id.directionsListEmpty,
									R.string.directions_list_empty_message);
						}
					}
				});
			}
		};

		return runnable;
	}
}