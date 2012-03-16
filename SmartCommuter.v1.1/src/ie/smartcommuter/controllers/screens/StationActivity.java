package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.maps.StationLocationActivity;
import ie.smartcommuter.controllers.tabcontents.StationDirectionsActivity;
import ie.smartcommuter.controllers.tabcontents.StationRealtimeActivity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class StationActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.screen_station);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
        // TODO: Get the stations details.
        
        TabHost tabHost = getTabHost();
        View tabView;
        Intent intent;
        TabHost.TabSpec tabSpec;
        
        intent = new Intent().setClass(this, StationRealtimeActivity.class);
        tabView = createTabView(tabHost.getContext(), "Realtime");
        tabSpec = tabHost.newTabSpec("Realtime").setIndicator(tabView).setContent(intent);
        tabHost.addTab(tabSpec);
        
        intent = new Intent().setClass(this, StationDirectionsActivity.class);
        tabView = createTabView(tabHost.getContext(), "Directions");
        tabSpec = tabHost.newTabSpec("Directions").setIndicator(tabView).setContent(intent);
        tabHost.addTab(tabSpec);
        
        intent = new Intent().setClass(this, StationLocationActivity.class);
        tabView = createTabView(tabHost.getContext(), "Location");
        tabSpec = tabHost.newTabSpec("Location").setIndicator(tabView).setContent(intent);
        tabHost.addTab(tabSpec);
        
        tabHost.setCurrentTab(0);
    }
    
    private static View createTabView(final Context context, final String text) {
    	View view = LayoutInflater.from(context).inflate(R.layout.tabs, null);
    	TextView tv = (TextView) view.findViewById(R.id.tabsTextView);
    	tv.setText(text);
    	return view;
    }
}
