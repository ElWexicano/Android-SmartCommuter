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
 * Splash Screen.
 * @author Shane Bryan Doyle
 */
public class SplashScreenActivity extends Activity {

	private Dialog dialog;
	private Context context;
	private DatabaseManager databaseManager;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    context = this;
	    
	    dialog = ProgressDialog.show(this, "SmartCommuter", "Loading. Please wait...", true);
	    
	    Thread splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	    	        databaseManager = new DatabaseManager(context);
	    	        databaseManager.open();
	            } finally {
	            	if(dialog.isShowing()) {
	            		dialog.dismiss();
	            	}
	            	databaseManager.close();
	                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
	            }
	        }
	    };
	    splashTread.start();
	}
}
