package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;


/**
 * This activity provides functionality to the
 * Favourite Stations screen of the application.
 * @author Shane Doyle
 */
public class FavouriteStationsActivity extends SmartActivity {
	
	private DatabaseManager mDatabaseManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
    }

	@Override
	protected void onResume() {
		super.onResume();
		
        mDatabaseManager = new DatabaseManager(this);
        mDatabaseManager.open();
        List<Station> favouriteStations = mDatabaseManager.getFavouriteStations();
        mDatabaseManager.close();
        
        StationArrayAdapter listAdapter = new StationArrayAdapter(this, favouriteStations);
        
        ListView favouriteStationsList = (ListView)findViewById(R.id.favouriteStationsList);
        favouriteStationsList.setOnItemClickListener(new StationItemListener());
        favouriteStationsList.setAdapter(listAdapter);
        favouriteStationsList.setEmptyView(findViewById(R.id.favouritesListEmpty));
	}

}
