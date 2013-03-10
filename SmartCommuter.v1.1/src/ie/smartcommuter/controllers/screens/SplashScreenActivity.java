package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.models.DatabaseManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * This class is used to display the application 
 * Splash Screen. It is also used to create any
 * shared prefences that will be used.
 * @author Shane Doyle
 */
public class SplashScreenActivity extends Activity {

	private Dialog mDialog;
	private Context mContext;
	private DatabaseManager mDatabaseManager;
	
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	    
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    mContext = this;
	    
	    mDialog = ProgressDialog.show(this, "SmartCommuter", "Loading. Please wait...", true);
	    
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	    	        mDatabaseManager = new DatabaseManager(mContext);
	    	        mDatabaseManager.open();
	            } finally {
	            	if(mDialog.isShowing()) {
	            		mDialog.dismiss();
	            	}
	            	mDatabaseManager.close();
	                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
	            }
	        }
	    };
	    splashTread.start();
	}
}
