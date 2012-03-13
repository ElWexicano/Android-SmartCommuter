package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * This activity provides functionality to the
 * home screen of the application.
 * 
 * @author iamshanedoyle
 *
 */
public class HomeActivity extends SmartActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        Button searchStationsButton = (Button)findViewById(R.id.searchStationsBtn);
        Button nearbyStationsButton = (Button)findViewById(R.id.nearbyStationsBtn);
        Button favouriteStationsButton = (Button)findViewById(R.id.favouriteStationsBtn);
        
        searchStationsButton.setOnClickListener(new HomeButtonsListener(0));
        nearbyStationsButton.setOnClickListener(new HomeButtonsListener(1));
        favouriteStationsButton.setOnClickListener(new HomeButtonsListener(2));
        
        // TODO: Add the recently viewed stations.
//        String[] recentlyViewedStations = getResources().getStringArray(R.array.stationsListExample);
//        
//        ListView recents = (ListView)findViewById(R.id.recentlyViewedList);
//        recents.setOnItemClickListener(new StationItemListener());
//        recents.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recentlyViewedStations));
//
//        recents.setTextFilterEnabled(true);
    }
    
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
