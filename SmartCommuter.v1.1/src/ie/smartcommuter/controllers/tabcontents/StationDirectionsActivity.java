package ie.smartcommuter.controllers.tabcontents;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartTabContentActivity;
import android.os.Bundle;

public class StationDirectionsActivity extends SmartTabContentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_station_direction);
        
        // TODO: Get the user location and directions to the station.
    }
}
