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
import android.os.Bundle;
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
	private ImageView favouritesImage;
	
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
        favouritesImage = (ImageView) findViewById(R.id.stationDetailsFavouriteImageView);
        
        favouritesImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				databaseManager.open();
				
				if(isFavourite) {
					databaseManager.removeFromFavouriteStations(station.getId());
					Toast.makeText(context, "Station removed from Favourites", Toast.LENGTH_SHORT).show();
					isFavourite = false;
					favouritesImage.setImageResource(android.R.drawable.star_off);
				} else {
					databaseManager.addToFavouriteStations(station.getId());
					Toast.makeText(context, "Station added to Favourites", Toast.LENGTH_SHORT).show();
					isFavourite = true;
					favouritesImage.setImageResource(R.drawable.ic_favourite);
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
