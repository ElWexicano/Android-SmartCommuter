package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.screens.HomeActivity;
import ie.smartcommuter.controllers.screens.InfoActivity;
import ie.smartcommuter.controllers.screens.SettingsActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.MapActivity;

public class SmartMapActivity extends MapActivity {

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
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
			goToActivity(HomeActivity.class);
			break;
		case R.id.settings :
			goToActivity(SettingsActivity.class);
			break;
		case R.id.information :
			goToActivity(InfoActivity.class);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	
	private void goToActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
	}
}
