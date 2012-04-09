package ie.smartcommuter.controllers;

import java.util.List;

import ie.smartcommuter.R;
import ie.smartcommuter.models.StationData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This is a class is used to display realtime in a list.
 * @author Shane Bryan Doyle
 */
public class RealtimeArrayAdapter extends ArrayAdapter<StationData> {

	private Context context;
	private List<StationData> realtime;
	
	public RealtimeArrayAdapter(Context context, List<StationData> realtime) {
		super(context, R.layout.row_realtime, realtime);
		this.context = context;
		this.realtime = realtime;
	}

	@Override
	public int getCount() {
		return realtime.size();
	}

	@Override
	public StationData getItem(int position) {
		return realtime.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getPosition(StationData item) {
		return realtime.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		StationData stationData = realtime.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view;
		
		if(position % 2 == 0) {
			view = inflater.inflate(R.layout.row_realtime, parent, false);
		} else {
			view = inflater.inflate(R.layout.row_realtime_odd, parent, false);
		}
		
		TextView destinationTextView = (TextView) view.findViewById(R.id.destinationTextView);
		destinationTextView.setText(stationData.getDestination());
		
		TextView expectedTimeTextView = (TextView) view.findViewById(R.id.expectedTimeTextView);
		expectedTimeTextView.setText(stationData.getExpectedTime());
		
		TextView routeTextView = (TextView) view.findViewById(R.id.routeTextView);
		routeTextView.setText(stationData.getRoute());
		
		return view;
	}
	
}
