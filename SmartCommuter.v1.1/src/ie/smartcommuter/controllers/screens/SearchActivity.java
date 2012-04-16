package ie.smartcommuter.controllers.screens;

import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * This class is used for the search stations
 * screen of the application.
 * @author Shane Bryan Doyle
 */
public class SearchActivity extends SmartActivity {

	private StationArrayAdapter listAdapter;
	private Spinner stationTypeSpinner;
	private EditText stationNameText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_search);
        
        stationTypeSpinner = (Spinner) findViewById(R.id.stationTypeSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.stationTypeArray, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationTypeSpinner.setAdapter(spinnerAdapter);
        stationTypeSpinner.setOnItemSelectedListener(new SearchSpinnerChangedListener());
        
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.open();
        
        List<Station> stations = databaseManager.getAllStations();
        
        databaseManager.close();
        
        listAdapter = new StationArrayAdapter(this, stations);
        
        ListView searchStationsList = (ListView)findViewById(R.id.searchStationsList);
        searchStationsList.setOnItemClickListener(new StationItemListener());
        searchStationsList.setAdapter(listAdapter);
        searchStationsList.setTextFilterEnabled(true);
        searchStationsList.setEmptyView(findViewById(R.id.searchStationsListEmpty));
        
        stationNameText = (EditText) findViewById(R.id.searchStationsNameEditText);
        stationNameText.addTextChangedListener(new SearchTextChangedListener());
    }
    
    
    /**
     * This class is used by the Search Station Name
     * Edit Text to update the List of Stations.
     * 
     * @author Shane Bryan Doyle
     */
    private class SearchTextChangedListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			listAdapter.getFilter().filter(s);
		}
    }
    
    /**
     * This class is used to update the list of stations based on
     * what is selected in the Spinner.
     * 
     * @author Shane Bryan Doyle
     */
    private class SearchSpinnerChangedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			String selected = arg0.getItemAtPosition(arg2).toString();

			if(selected!=null) {
				
				if(selected.equals("Bus")) {
					listAdapter.updateStationModeFilter("Bus");
				} else if (selected.equals("Rail")) {
					listAdapter.updateStationModeFilter("Rail");
				} else if (selected.equals("Tram")) {
					listAdapter.updateStationModeFilter("Tram");
				} else {
					listAdapter.updateStationModeFilter("All");
				}
				
				listAdapter.getFilter().filter(stationNameText.getText());
				
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {}
    }
}
