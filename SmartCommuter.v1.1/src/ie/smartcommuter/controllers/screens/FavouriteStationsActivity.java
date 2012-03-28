package ie.smartcommuter.controllers.screens;

import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;
import android.os.Bundle;
import android.widget.ListView;


/**
 * This activity provides functionality to the
 * Favourite Stations screen of the application.
 * @author Shane Bryan Doyle
 */
public class FavouriteStationsActivity extends SmartActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_favourites);
        
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.open();
        
        List<Station> favouriteStations = databaseManager.getAllStations();
        
        databaseManager.close();
        
        StationArrayAdapter listAdapter = new StationArrayAdapter(this, favouriteStations);
        
        ListView favouriteStationsList = (ListView)findViewById(R.id.favouriteStationsList);
        favouriteStationsList.setOnItemClickListener(new StationItemListener());
        favouriteStationsList.setAdapter(listAdapter);
    }
}
