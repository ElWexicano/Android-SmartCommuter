package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.screens.HomeActivity;
import ie.smartcommuter.controllers.screens.SettingsActivity;
import ie.smartcommuter.controllers.screens.StationActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This is a superclass that is widely used by
 * classes that need Tab Content.
 * @author Shane Bryan Doyle
 */
public class SmartTabContentActivity extends Activity {

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
	 * This class is used to Listen to when an Station
	 * Item has been clicked and responds to it by 
	 * starting the the StationActivity class which
	 * intends displays the Station Details to the user.
	 * @author Shane Bryan Doyle
	 */
	public class StationItemListener implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
			Bundle activityInfo = new Bundle();
			
			activityInfo.putInt("stationId", arg1.getId());
			
			goToActivity(StationActivity.class, activityInfo);
		}
		
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
	 * This method is used to open the information
	 * dialog.
	 */
	private void openInformationDialog() {
		Dialog dialog = new Dialog(this);
		dialog.setTitle(R.string.appAbout);
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
}
