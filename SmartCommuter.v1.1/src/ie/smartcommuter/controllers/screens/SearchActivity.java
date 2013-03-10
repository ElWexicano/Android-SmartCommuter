package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import ie.smartcommuter.controllers.StationArrayAdapter;
import ie.smartcommuter.models.DatabaseManager;
import ie.smartcommuter.models.Station;

import java.util.List;

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
 * This class is used for the search stations screen of the application.
 * 
 * @author Shane Doyle
 */
public class SearchActivity extends SmartActivity {

	private StationArrayAdapter mListAdapter;
	private Spinner mStationTypeSpinner;
	private EditText mStationNameText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mStationTypeSpinner = (Spinner) findViewById(R.id.stationTypeSpinner);
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(this, R.array.station_types,
						android.R.layout.simple_spinner_item);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mStationTypeSpinner.setAdapter(spinnerAdapter);
		mStationTypeSpinner
				.setOnItemSelectedListener(new SearchSpinnerChangedListener());

		DatabaseManager databaseManager = new DatabaseManager(this);
		databaseManager.open();

		List<Station> stations = databaseManager.getAllStations();

		databaseManager.close();

		mListAdapter = new StationArrayAdapter(this, stations);

		ListView searchStationsList = (ListView) findViewById(R.id.searchStationsList);
		searchStationsList.setOnItemClickListener(new StationItemListener());
		searchStationsList.setAdapter(mListAdapter);
		searchStationsList.setTextFilterEnabled(true);
		searchStationsList
				.setEmptyView(findViewById(R.id.searchStationsListEmpty));

		mStationNameText = (EditText) findViewById(R.id.searchStationsNameEditText);
		mStationNameText
				.addTextChangedListener(new SearchTextChangedListener());
	}

	/**
	 * This class is used by the Search Station Name Edit Text to update the
	 * List of Stations.
	 * 
	 * @author Shane Bryan Doyle
	 */
	private class SearchTextChangedListener implements TextWatcher {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mListAdapter.getFilter().filter(s);
		}
	}

	/**
	 * This class is used to update the list of stations based on what is
	 * selected in the Spinner.
	 * 
	 * @author Shane Bryan Doyle
	 */
	private class SearchSpinnerChangedListener implements
			OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			String selected = arg0.getItemAtPosition(arg2).toString();

			if (selected != null) {

				if (selected.equals("Bus")) {
					mListAdapter.updateStationModeFilter("Bus");
				} else if (selected.equals("Rail")) {
					mListAdapter.updateStationModeFilter("Rail");
				} else if (selected.equals("Tram")) {
					mListAdapter.updateStationModeFilter("Tram");
				} else {
					mListAdapter.updateStationModeFilter("All");
				}

				mListAdapter.getFilter().filter(mStationNameText.getText());

			}

		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}
