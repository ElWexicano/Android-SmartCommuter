package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchActivity extends SmartActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        Spinner stationTypeSpinner = (Spinner) findViewById(R.id.stationTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.stationTypeArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationTypeSpinner.setAdapter(adapter);
        
        // Add in list of the stations.. Persistence Required.
        String[] searchResultStations = getResources().getStringArray(R.array.stationsListExample);
        
        ListView searchStationsList = (ListView)findViewById(R.id.searchStationsList);
        searchStationsList.setOnItemClickListener(new StationItemListener());
        searchStationsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResultStations));

        searchStationsList.setTextFilterEnabled(true);
    }
}
