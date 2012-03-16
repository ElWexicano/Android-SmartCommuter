package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;

public class FavouriteStationsActivity extends SmartActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_favourites);
        
        // TODO: Add in list of the stations of favourite stations
//        String[] favouriteStations = getResources().getStringArray(R.array.stationsListExample);
//        
//        ListView favourites = (ListView)findViewById(R.id.favouriteStationsList);
//        favourites.setOnItemClickListener(new StationItemListener());
//        favourites.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favouriteStations));
//
//        favourites.setTextFilterEnabled(true);
    }
}
