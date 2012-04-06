package ie.smartcommuter.models;

import ie.smartcommuter.R;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

/**
 * This class is used to send Crash Reports
 * to the SmartCommuter Document for Beta 
 * Testing.
 * @author Shane Bryan Doyle
 */
//@ReportsCrashes(formKey = "dDZ3REFVbFR0anJqQ21CQW82UGJLTkE6MQ",
//						mode = ReportingInteractionMode.TOAST,
//						forceCloseDialogAfterToast = false, 
//						resToastText = R.string.crashMessage) 
public class MyApplication extends Application {

    @Override
    public void onCreate() {
//        ACRA.init(this);
        super.onCreate();
    }
}
