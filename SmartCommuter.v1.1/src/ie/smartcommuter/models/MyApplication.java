package ie.smartcommuter.models;

import ie.smartcommuter.R;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This class is used to send Crash Reports
 * to the SmartCommuter Document for Beta 
 * Testing.
 * @author Shane Bryan Doyle
 */
@ReportsCrashes(formKey = "dDZ3REFVbFR0anJqQ21CQW82UGJLTkE6MQ",
						mode = ReportingInteractionMode.TOAST,
						forceCloseDialogAfterToast = false, 
						resToastText = R.string.crashMessage) 
public class MyApplication extends Application {

    @Override
    public void onCreate() {
    	
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	
        if(prefs.getBoolean("automaticProblemReporting",true)) {
        	ACRA.init(this);
        }
        
        super.onCreate();
    }
}
