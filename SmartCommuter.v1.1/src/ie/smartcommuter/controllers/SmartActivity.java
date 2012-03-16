package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.screens.HomeActivity;
import ie.smartcommuter.controllers.screens.InfoActivity;
import ie.smartcommuter.controllers.screens.SettingsActivity;
import ie.smartcommuter.controllers.screens.StationActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SmartActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.screen_home);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater i = getMenuInflater();
		i.inflate(R.menu.menu_options,menu);
		
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
    
	
	public class StationItemListener implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			goToActivity(StationActivity.class);
		}
		
	}
	
	private void goToActivity(Class<? extends Activity> activityClass) {
        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
	}
	
}