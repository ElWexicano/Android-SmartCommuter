package ie.smartcommuter.controllers.screens;

import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * This activity provides functionality to the
 * home screen of the application.
 * @author Shane Bryan Doyle
 */
public class HomeActivity extends SmartActivity {
	
	private List<Station> recentlyViewedStations;
	private DatabaseManager databaseManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isHomeActivity = true;
        
        Button searchStationsButton = (Button)findViewById(R.id.searchStationsBtn);
        Button nearbyStationsButton = (Button)findViewById(R.id.nearbyStationsBtn);
        Button favouriteStationsButton = (Button)findViewById(R.id.favouriteStationsBtn);
        
        searchStationsButton.setOnClickListener(new HomeButtonsListener(0));
        nearbyStationsButton.setOnClickListener(new HomeButtonsListener(1));
        favouriteStationsButton.setOnClickListener(new HomeButtonsListener(2));
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		
        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        
        recentlyViewedStations = databaseManager.getRecentlyViewedStations();
        
        databaseManager.close();
        
        StationArrayAdapter listAdapter  = new StationArrayAdapter(this, recentlyViewedStations);
        ListView recentStationsList = (ListView)findViewById(R.id.recentlyViewedList);
        recentStationsList.setOnItemClickListener(new StationItemListener());
        recentStationsList.setAdapter(listAdapter);
	}


	/**
     * This class is used to listen to when a
     * button on the home screen has been 
     * clicked and starts the activity that is
     * associated with that button.
     * @author Shane Bryan Doyle
     */
    private class HomeButtonsListener implements OnClickListener {
    	
    	int operationId;
    	
    	public HomeButtonsListener(int i){
    		this.operationId = i;
    	}
    	
		@Override
		public void onClick(View arg0) {
			
			Intent activityIntent = null;
			
			switch(operationId) {
			case 0:
				activityIntent = new Intent(HomeActivity.this, SearchActivity.class);
				break;
			case 1:
				activityIntent = new Intent(HomeActivity.this, NearbyStationsActivity.class);
				break;
			case 2:
				activityIntent = new Intent(HomeActivity.this, FavouriteStationsActivity.class);
				break;
			}
			
			if(activityIntent!=null) {;
				startActivity(activityIntent);
			}
		}
    }
}
