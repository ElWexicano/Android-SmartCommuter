package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.RealtimeArrayAdapter;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Station;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * This is a class is used to display the realtime information for the station.
 * 
 * @author Shane Doyle
 */
public class StationRealtimeActivity extends SmartTabContentActivity {

	private Station mStation;
	private ListView mArrivals;
	private ListView mDepartures;
	private RealtimeArrayAdapter mArrivalsAdapter;
	private RealtimeArrayAdapter mDeparturesAdapter;
	private int mRealtimeRefreshInterval;
	private SharedPreferences mPrefs;
	private Context mContext;
	private Boolean mRealtimeUpdates;
	private Handler mHandler;
	private Runnable mRunnable;
	private Boolean mHideProgressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_station_realtime);

		mContext = this;
		mRealtimeUpdates = true;
		mHandler = new Handler();
		mHideProgressBar = false;

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mRealtimeRefreshInterval = Integer.parseInt(mPrefs.getString(
				"realtimeRefreshInterval", "30000"));

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		mStation = (Station) bundle.getSerializable("station");

		mArrivals = (ListView) findViewById(R.id.arrivalsListView);
		mArrivals.setTextFilterEnabled(false);
		mArrivals.setEmptyView(findViewById(R.id.arrivalsLoadingListView));

		mDepartures = (ListView) findViewById(R.id.departuresListView);
		mDepartures.setTextFilterEnabled(false);
		mDepartures.setEmptyView(findViewById(R.id.departuresLoadingListView));

		mRunnable = getRealtimeRunnable();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mRealtimeUpdates = false;
		mHandler.removeCallbacks(mRunnable);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mRealtimeRefreshInterval = Integer.parseInt(mPrefs.getString(
				"realtimeRefreshInterval", "30000"));

		mRealtimeUpdates = true;

		new Thread(mRunnable).start();
	}

	/**
	 * This method is used to run a thread that updates the realtime information
	 * on the screen.
	 * 
	 * @return
	 */
	private synchronized Runnable getRealtimeRunnable() {
		Runnable runnable = new Runnable() {

			public void run() {

				while (mRealtimeUpdates) {
					mStation.getRealTimeData();

					mHandler.post(new Runnable() {

						public void run() {
							mArrivalsAdapter = new RealtimeArrayAdapter(
									mContext, mStation.getArrivals());
							mArrivals.setAdapter(mArrivalsAdapter);

							mDeparturesAdapter = new RealtimeArrayAdapter(
									mContext, mStation.getDepartures());
							mDepartures.setAdapter(mDeparturesAdapter);

							if (!mHideProgressBar) {
								updateEmptyListMessages();
							}
						}
					});

					try {
						Thread.sleep(mRealtimeRefreshInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		return runnable;
	}

	/**
	 * This method is used to change the original empty view message from
	 * loading to the no data message.
	 * 
	 * @return
	 */
	private void updateEmptyListMessages() {
		ProgressBar arrivalsLoading = (ProgressBar) findViewById(R.id.arrivalsLoadingProgressBar);
		arrivalsLoading.setVisibility(View.INVISIBLE);
		TextView arrivalsTextView = (TextView) findViewById(R.id.arrivalsLoadingTextView);
		arrivalsTextView.setText(R.string.arrivals_list_empty_message);

		ProgressBar departuresLoading = (ProgressBar) findViewById(R.id.departuresLoadingProgressBar);
		departuresLoading.setVisibility(View.INVISIBLE);
		TextView departuresTextView = (TextView) findViewById(R.id.departuresLoadingTextView);
		departuresTextView.setText(R.string.departures_list_emptyMessage);

		mHideProgressBar = true;
	}

}
