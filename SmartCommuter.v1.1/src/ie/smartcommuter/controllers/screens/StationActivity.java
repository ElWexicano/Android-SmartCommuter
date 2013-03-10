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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity provides functionality to the Station Details screen of the
 * application.
 * 
 * @author Shane Doyle
 */
public class StationActivity extends SmartTabActivity {

	private Context mContext;
	private DatabaseManager mDatabaseManager;
	private Boolean mIsFavourite;
	private Station mStation;
	private ImageView mFavouritesImage, mStationCompanyImage;
	private SharedPreferences mPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		Intent i = getIntent();
		Bundle info = i.getExtras();
		int stationId = info.getInt("stationId");

		mDatabaseManager = new DatabaseManager(this);
		mDatabaseManager.open();
		mStation = mDatabaseManager.getStation(stationId);
		mDatabaseManager.close();

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.screen_station);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_bar);

		TextView stationNameTextView = (TextView) findViewById(R.id.text_station_name);
		TextView stationAddressTextView = (TextView) findViewById(R.id.text_station_address);
		stationNameTextView.setText(mStation.getName());
		stationAddressTextView.setText(mStation.getAddress().getLocation());

		mStationCompanyImage = (ImageView) findViewById(R.id.image_view_station_logo);
		mStationCompanyImage.setImageResource(mStation.getStationLogo());

		mFavouritesImage = (ImageView) findViewById(R.id.image_view_station_favourite);
		mFavouritesImage.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				mDatabaseManager.open();

				if (mIsFavourite) {
					mDatabaseManager.removeFromFavouriteStations(mStation
							.getId());
					Toast.makeText(mContext,
							R.string.favourite_removed_message,
							Toast.LENGTH_SHORT).show();
					mIsFavourite = false;
					mFavouritesImage
							.setImageResource(android.R.drawable.star_off);
				} else {
					if (mDatabaseManager.addToFavouriteStations(mStation
							.getId())) {
						Toast.makeText(mContext,
								R.string.favourite_added_success_message,
								Toast.LENGTH_SHORT).show();
						mIsFavourite = true;
						mFavouritesImage
								.setImageResource(R.drawable.ic_favourite);
					} else {
						Toast.makeText(mContext,
								R.string.favourite_added_failed_message,
								Toast.LENGTH_SHORT).show();
					}
				}

				mDatabaseManager.close();
			}
		});

		Bundle activityInfo = new Bundle();
		activityInfo.putSerializable("station", mStation);

		tabHost = getTabHost();
		addTab(StationRealtimeActivity.class, activityInfo, "Realtime");
		addTab(StationDirectionsActivity.class, activityInfo, "Directions");
		addTab(StationLocationActivity.class, activityInfo, "Location");
		tabHost.setCurrentTab(0);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mDatabaseManager.maxFavourites = Integer.parseInt(mPrefs.getString(
				"maxFavouriteStations", "10"));
		mDatabaseManager.maxRecentlyViewed = Integer.parseInt(mPrefs.getString(
				"maxRecentlyViewedStations", "10"));

		mDatabaseManager.open();
		mIsFavourite = mDatabaseManager.isFavourite(mStation.getId());
		mDatabaseManager.addToRecentlyViewedStations(mStation.getId());
		mDatabaseManager.close();

		if (mIsFavourite) {
			mFavouritesImage.setImageResource(R.drawable.ic_favourite);
		} else {
			mFavouritesImage.setImageResource(android.R.drawable.star_off);
		}
	}
}
