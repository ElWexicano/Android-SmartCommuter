package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * This superclass is used in tabbed activity classes.
 * @author Shane Bryan Doyle
 */
public class SmartTabActivity extends TabActivity {

	protected TabHost tabHost;
	
    /**
     * This method is used to create a tab view.
     * @param context
     * @param text
     * @return
     */
    private static View createTabView(final Context context, final String text) {
    	View view = LayoutInflater.from(context).inflate(R.layout.tabs, null);
    	TextView tv = (TextView) view.findViewById(R.id.tabsTextView);
    	tv.setText(text);
    	return view;
    }
    
    /**
     * This method is used to add a new tab to the tab content.
     * @param activityClass
     * @param bundle
     * @param tabName
     */
    protected void addTab(Class<? extends Activity> activityClass, Bundle bundle, String tabName) {
        Intent intent = new Intent().setClass(this, activityClass);
        intent.putExtras(bundle);
        View tabView = createTabView(tabHost.getContext(), tabName);
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabName).setIndicator(tabView).setContent(intent);
        tabHost.addTab(tabSpec);
    }
    

}
