package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.models.DatabaseManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * This is a class is used for the Settings
 * Screen of the Application.
 * @author Shane Bryan Doyle
 */
public class SettingsActivity extends PreferenceActivity {
	
	private Context context;
	private DatabaseManager databaseManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.screen_settings);
        
        addPreferencesFromResource(R.xml.settings);
        databaseManager = new DatabaseManager(this);
        
        Preference problemReporting = getPreferenceScreen().findPreference("automaticProblemReporting");
        problemReporting.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				Toast.makeText(context, R.string.restartMessage, Toast.LENGTH_SHORT).show();
				return false;
			}

        });
        
        Preference clearFavourites = getPreferenceScreen().findPreference("clearFavouriteStations");
        clearFavourites.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				displayClearAllAlertDialog("favourites");
				return false;
			}
        	
        });
        
        Preference clearRecentlyViewed = getPreferenceScreen().findPreference("clearRecenltyViewedStations");
        clearRecentlyViewed.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				displayClearAllAlertDialog("recents");
				return false;
			}
        	
        });
        
        Preference showTutorial = getPreferenceScreen().findPreference("showTutorial");
        showTutorial.setOnPreferenceClickListener(new OnPreferenceClickListener(){

			@Override
			public boolean onPreferenceClick(Preference preference) {
				Intent newActivity = new Intent(context, TutorialActivity.class);
				startActivity(newActivity);
				return false;
			}
        	
        });
        
        Preference currentVersion = getPreferenceScreen().findPreference("currentVersion");
        currentVersion.setSummary(getApplicationVersion());
    }
    
	/**
	 * This method is used to get the current version of the application.
	 * @return
	 */
	private String getApplicationVersion() {
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		String version = "0";
		
		try {
			info = manager.getPackageInfo(
			    this.getPackageName(), 0);
			version = info.versionName;
		} catch (NameNotFoundException e) {
		}
		
		return version;
    }
	
	/**
	 * This method is used to display the alert dialog for
	 * clearing either the favourite stations or recently
	 * viewed stations.
	 * @param typeOfAlert
	 */
	private void displayClearAllAlertDialog(final String typeOfAlert) {
		
		int message;
		
		if(typeOfAlert.equals("favourites")) {
			message = R.string.clearFavouriteStationsMessage;
		} else {
			message = R.string.clearRecentlyViewedStationsMessage;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
			.setCancelable(false)
			.setPositiveButton("Yes", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					databaseManager.open();
					
					if(typeOfAlert.equals("favourites")) {
						databaseManager.removeAllFavouriteStations();
						Toast.makeText(context, "Cleared Favourite Stations !", Toast.LENGTH_SHORT).show();
					} else {
						databaseManager.removeAllRecentlyViewedStations();
						Toast.makeText(context, "Cleared Recently Viewed Stations !", Toast.LENGTH_SHORT).show();
					}
					
					databaseManager.close();
				}
		       })
		    .setNegativeButton("No", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}});
		
		AlertDialog alert = builder.create();
		alert.show();
	}
	
}
