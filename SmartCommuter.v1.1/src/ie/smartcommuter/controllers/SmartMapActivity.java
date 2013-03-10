package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.screens.HomeActivity;
import ie.smartcommuter.controllers.screens.SettingsActivity;
import ie.smartcommuter.models.Address;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

/**
 * This is a superclass that is widely used by
 * classes that need Maps.
 * @author Shane Doyle
 */
public class SmartMapActivity extends MapActivity {

	protected MapView mapView;
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

   @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater i = getMenuInflater();
		i.inflate(R.menu.menu_options,menu);
		
		return super.onCreateOptionsMenu(menu);
	}
    
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		
		case R.id.home :
			goToActivity(HomeActivity.class, null);
			break;
		case R.id.settings :
			goToActivity(SettingsActivity.class, null);
			break;
		case R.id.information :
			openInformationDialog();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	/**
	 * This method is used to start a new activity.
	 * @param activityClass
	 * @param bundle
	 */
	private void goToActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent newActivity = new Intent(this, activityClass);
        
        if(bundle!=null) {
        	newActivity.putExtras(bundle);
        }
        
        startActivity(newActivity);
	}
	
    /**
     * This method is used to centre the Map on an address.
     * @param address
     */
    protected MapView initGoogleMap(Address address) {
    	MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        MapController mapController = mapView.getController();
        mapController.setCenter(new GeoPoint(address.getLatitude(),address.getLongitude()));
        mapController.setZoom(15);
		return mapView;
    }
    
	/**
	 * This method is used to open the information
	 * dialog.
	 */
	private void openInformationDialog() {
		Dialog dialog = new Dialog(this);
		dialog.setTitle(R.string.app_about);
		dialog.setContentView(R.layout.dialog_information);
		
		TextView currentVersion = (TextView) dialog.findViewById(R.id.versionTextView);
		currentVersion.setText(getApplicationVersion());
		
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
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
	 * This method is used to get the Icon to display
	 * for the Station location on the Google Map.
	 * @param mode
	 * @return
	 */
	protected Drawable getStationDrawable(String mode) {
		Drawable drawable;
		if(mode.equalsIgnoreCase("bus")) {
        	drawable = this.getResources().getDrawable(R.drawable.map_ic_bus);
        } else if (mode.equalsIgnoreCase("rail")) {
        	drawable = this.getResources().getDrawable(R.drawable.map_ic_train);
        } else {
        	drawable = this.getResources().getDrawable(R.drawable.map_ic_tram);
        }
		return drawable;
	}
	
	/**
	 * This method is used to get the Icon to display
	 * for the User location on the Google Map.
	 * @param gender
	 * @return
	 */
	protected Drawable getUserDrawable(String gender){
		Drawable drawable;
		
		if(gender.equals("male")) {
			drawable = this.getResources().getDrawable(R.drawable.map_ic_male);
		} else {
			drawable = this.getResources().getDrawable(R.drawable.map_ic_female);
		}
		
		return drawable;
	}
}
