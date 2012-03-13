package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SmartTabContentActivity extends Activity {

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater i = getMenuInflater();
		i.inflate(R.menu.options_menu,menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
    @Override
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
