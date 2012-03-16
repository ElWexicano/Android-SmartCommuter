package ie.smartcommuter.controllers.screens;

import java.util.ArrayList;
import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.models.Company;
import ie.smartcommuter.models.Station;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * This class is used for the search stations
 * screen of the application.
 * @author Shane Bryan Doyle
 *
 */
public class SearchActivity extends SmartActivity {
	
	StationArrayAdapter listAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_search);
        
        Spinner stationTypeSpinner = (Spinner) findViewById(R.id.stationTypeSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.stationTypeArray, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationTypeSpinner.setAdapter(spinnerAdapter);
        // TODO: Get the spinner to update the stations list.
        
        List<Station> stations = new ArrayList<Station>();
        Station station1 = new Station();
        Company company = new Company();
        
        station1.setName("Lower Some Road (Stop 4477)");
        company.setName("Dublin Bus");
        station1.setCompany(company);
        
        stations.add(station1);
        stations.add(station1);
        stations.add(station1);
        
        // TODO: Add in list of the stations. (Persistence Required)
//        String[] searchResultStations = getResources().getStringArray(R.array.stationsListExample);
        
        listAdapter = new StationArrayAdapter(this, stations);
        
        //listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResultStations);
        
        ListView searchStationsList = (ListView)findViewById(R.id.searchStationsList);
        searchStationsList.setOnItemClickListener(new StationItemListener());
        searchStationsList.setAdapter(listAdapter);

        searchStationsList.setTextFilterEnabled(true);
        EditText stationNameText = (EditText) findViewById(R.id.searchStationsNameEditText);
        stationNameText.addTextChangedListener(new SearchTextChangedListener());
    }
    
    
    /**
     * This class is used by the Search Station Name
     * Edit Text to update the List of Stations.
     * 
     * @author Shane Byran Doyle
     *
     */
    private class SearchTextChangedListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			listAdapter.getFilter().filter(s.toString());
		}
    	
    }
}
