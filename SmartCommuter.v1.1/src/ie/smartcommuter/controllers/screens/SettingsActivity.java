package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.models.DatabaseManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * This is a class is used for the Settings Screen of the Application.
 * 
 * @author Shane Doyle
 */
public class SettingsActivity extends PreferenceActivity {

	private Context mContext;
	private DatabaseManager mCatabaseManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		setContentView(R.layout.activity_settings);

		addPreferencesFromResource(R.xml.settings);
		mCatabaseManager = new DatabaseManager(this);

		Preference problemReporting = getPreferenceScreen().findPreference(
				"automaticProblemReporting");
		problemReporting
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference arg0) {
						Toast.makeText(mContext, R.string.restart_message,
								Toast.LENGTH_SHORT).show();
						return false;
					}

				});

		Preference clearFavourites = getPreferenceScreen().findPreference(
				"clearFavouriteStations");
		clearFavourites
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						displayClearAllAlertDialog("favourites");
						return false;
					}

				});

		Preference clearRecentlyViewed = getPreferenceScreen().findPreference(
				"clearRecenltyViewedStations");
		clearRecentlyViewed
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						displayClearAllAlertDialog("recents");
						return false;
					}

				});

		Preference showTutorial = getPreferenceScreen().findPreference(
				"showTutorial");
		showTutorial
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						Intent newActivity = new Intent(mContext,
								TutorialActivity.class);
						startActivity(newActivity);
						return false;
					}

				});

		Preference currentVersion = getPreferenceScreen().findPreference(
				"currentVersion");
		currentVersion.setSummary(getApplicationVersion());
	}

	/**
	 * This method is used to get the current version of the application.
	 * 
	 * @return
	 */
	private String getApplicationVersion() {
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		String version = "0";

		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
			version = info.versionName;
		} catch (NameNotFoundException e) {
		}

		return version;
	}

	/**
	 * This method is used to display the alert dialog for clearing either the
	 * favourite stations or recently viewed stations.
	 * 
	 * @param typeOfAlert
	 */
	private void displayClearAllAlertDialog(final String typeOfAlert) {

		int message;

		if (typeOfAlert.equals("favourites")) {
			message = R.string.clear_favourite_stations_message;
		} else {
			message = R.string.clear_recently_viewed_stations_message;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("Yes", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						mCatabaseManager.open();

						if (typeOfAlert.equals("favourites")) {
							mCatabaseManager.removeAllFavouriteStations();
							Toast.makeText(mContext,
									"Cleared Favourite Stations !",
									Toast.LENGTH_SHORT).show();
						} else {
							mCatabaseManager.removeAllRecentlyViewedStations();
							Toast.makeText(mContext,
									"Cleared Recently Viewed Stations !",
									Toast.LENGTH_SHORT).show();
						}

						mCatabaseManager.close();
					}
				}).setNegativeButton("No", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

}
