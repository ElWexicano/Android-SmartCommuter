package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import android.os.Bundle;

/**
 * This class is used for the Information Screen 
 * of the Application.
 * @author Shane Bryan Doyle
 */
public class InfoActivity extends SmartActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_information);
        
        // TODO: Create the information screen. About the app and disclaimer.
    }
}
