package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabActivity;
import ie.smartcommuter.controllers.maps.StationLocationActivity;
import ie.smartcommuter.controllers.tabcontents.StationDirectionsActivity;
import ie.smartcommuter.controllers.tabcontents.StationRealtimeActivity;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity provides functionality to the
 * Station Details screen of the application.
 * @author Shane Bryan Doyle
 */
public class StationActivity extends SmartTabActivity {
	
	private Context context;
	private DatabaseManager databaseManager;
	private Boolean isFavourite;
	private Station station;
	private ImageView favouritesImage, stationCompanyImage;
	private SharedPreferences prefs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        
        Intent i = getIntent();
        Bundle info = i.getExtras();
        int stationId = info.getInt("stationId");

        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        station = databaseManager.getStation(stationId);
        databaseManager.close();
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.screen_station);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
        TextView stationNameTextView = (TextView) findViewById(R.id.stationNameTextView);
        TextView stationAddressTextView = (TextView) findViewById(R.id.stationAddressTextView);
        stationNameTextView.setText(station.getName());
        stationAddressTextView.setText(station.getAddress().getLocation());
        
        stationCompanyImage = (ImageView) findViewById(R.id.stationCompanyImageView);
        stationCompanyImage.setImageResource(station.getStationLogo());

        favouritesImage = (ImageView) findViewById(R.id.stationDetailsFavouriteImageView);
        favouritesImage.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				databaseManager.open();
				
				if(isFavourite) {
					databaseManager.removeFromFavouriteStations(station.getId());
					Toast.makeText(context, R.string.favouriteRemovedMessage, Toast.LENGTH_SHORT).show();
					isFavourite = false;
					favouritesImage.setImageResource(android.R.drawable.star_off);
				} else {
					if(databaseManager.addToFavouriteStations(station.getId())){
						Toast.makeText(context, R.string.favouriteAddedSuccessMessage, Toast.LENGTH_SHORT).show();
						isFavourite = true;
						favouritesImage.setImageResource(R.drawable.ic_favourite);
					} else {
						Toast.makeText(context, R.string.favouriteAddedFailedMessage, Toast.LENGTH_SHORT).show();
					}
				}
				
				databaseManager.close();
			}
		});
        
        Bundle activityInfo = new Bundle();
        activityInfo.putSerializable("station", station);
        
        tabHost = getTabHost();
        addTab(StationRealtimeActivity.class, activityInfo, "Realtime");
        addTab(StationDirectionsActivity.class, activityInfo, "Directions");
        addTab(StationLocationActivity.class, activityInfo, "Location");
        tabHost.setCurrentTab(0);
    }
    
    
    @Override
	protected void onResume() {
		super.onResume();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		databaseManager.maxFavourites = Integer.parseInt(prefs.getString("maxFavouriteStations", "10"));
		databaseManager.maxRecentlyViewed = Integer.parseInt(prefs.getString("maxRecentlyViewedStations", "10"));
				
		databaseManager.open();
		isFavourite = databaseManager.isFavourite(station.getId());
		databaseManager.addToRecentlyViewedStations(station.getId());
		databaseManager.close();
		
		if(isFavourite) {
			favouritesImage.setImageResource(R.drawable.ic_favourite);
		} else {
			favouritesImage.setImageResource(android.R.drawable.star_off);
		}
    }
}
