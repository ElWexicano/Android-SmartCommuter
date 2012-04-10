package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.RealtimeArrayAdapter;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import ie.smartcommuter.models.Station;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This is a class is used to display the realtime
 * information for the station.
 * @author Shane Bryan Doyle
 */
public class StationRealtimeActivity extends SmartTabContentActivity {
	
	private Station station;
	private ListView arrivals;
	private ListView departures;
	private RealtimeArrayAdapter arrivalsAdapter;
	private RealtimeArrayAdapter departuresAdapter;
	private int realtimeRefreshInterval;
	private SharedPreferences prefs;
	private Context context;
	private Boolean getRealtimeUpdates;
	private Handler handler;
	private ProgressDialog loadingDialog;
	private Runnable runnable;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_realtime);
        
        context = this;
        getRealtimeUpdates = true;
        handler = new Handler();
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        realtimeRefreshInterval = Integer.parseInt(prefs.getString("realtimeRefreshInterval", "30000"));
        
        loadingDialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        station = (Station) bundle.getSerializable("station");

        arrivals = (ListView)findViewById(R.id.arrivalsListView);
        departures = (ListView)findViewById(R.id.departuresListView);
        
        arrivals.setTextFilterEnabled(false);
        departures.setTextFilterEnabled(false);
        
		runnable = getRealtimeRunnable();
    }

	@Override
	protected void onPause() {
		super.onPause();
		getRealtimeUpdates = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		realtimeRefreshInterval = Integer.parseInt(prefs.getString("realtimeRefreshInterval", "30000"));
		
		getRealtimeUpdates = true;
		
		new Thread(runnable).start();
	}
	
	/**
	 * This method is used to run a thread that updates
	 * the realtime information on the screen.
	 * @return
	 */
	private Runnable getRealtimeRunnable() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while(getRealtimeUpdates) {
					
					station.getRealTimeData();
					
					handler.post(new Runnable() {
						@Override
						public void run() {
					        arrivalsAdapter = new RealtimeArrayAdapter(context, station.getArrivals());
					        arrivals.setAdapter(arrivalsAdapter);
					        
					        departuresAdapter = new RealtimeArrayAdapter(context, station.getDepartures());
					        departures.setAdapter(departuresAdapter);

							if(loadingDialog.isShowing()) {
					        	loadingDialog.dismiss();
					        } else {
					        	Toast.makeText(context, "Realtime Updated !", Toast.LENGTH_SHORT).show();
					        }
						}
					});
					
					try {
						Thread.sleep(realtimeRefreshInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		return runnable;
	}
}

