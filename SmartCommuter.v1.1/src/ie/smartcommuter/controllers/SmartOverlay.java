package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.screens.StationActivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * This is a class is used to add Overlays to Google Map
 * @author Shane Bryan Doyle
 */
public class SmartOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context context;
	
	public SmartOverlay(Drawable arg0) {
		super(boundCenterBottom(arg0));
	}

	public SmartOverlay(Context context, Drawable arg0) {
		super(boundCenterBottom(arg0));
		this.context = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		
		if(item.getTitle().equals("Station")) {
			
			Bundle activityInfo = new Bundle();
			activityInfo.putInt("stationId", Integer.parseInt(item.getSnippet()));
			goToActivity(StationActivity.class, activityInfo);

		} else if(item.getTitle().equals("Station Location")) {
			Toast.makeText(context, "Station Location", Toast.LENGTH_SHORT).show();
		} else if(item.getTitle().equals("User Location")) {
			Toast.makeText(context, "Your Location", Toast.LENGTH_SHORT).show();
		}
		
		return true;
	}
	
	
	/**
	 * This method is used to start a new activity.
	 * @param activityClass
	 * @param bundle
	 */
	private void goToActivity(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent newActivity = new Intent(context, activityClass);
        
        if(bundle!=null) {
        	newActivity.putExtras(bundle);
        }
        
        context.startActivity(newActivity);
	}
	
	/**
	 * This method is used to get the Station Marker.
	 * @param mode
	 * @return
	 */
	public Drawable getStationMarker(String mode) {
		Drawable drawable;
		if(mode.equalsIgnoreCase("bus")) {
        	drawable = context.getResources().getDrawable(R.drawable.map_ic_bus);
        } else if (mode.equalsIgnoreCase("rail")) {
        	drawable = context.getResources().getDrawable(R.drawable.map_ic_train);
        } else {
        	drawable = context.getResources().getDrawable(R.drawable.map_ic_tram);
        }
		
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		boundCenterBottom(drawable);
		
		return drawable;
	}
	
	
}