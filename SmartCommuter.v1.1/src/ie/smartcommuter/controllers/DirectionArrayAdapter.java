package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import ie.smartcommuter.models.Stage;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This is a class is used to display directions in a list.
 * @author Shane Bryan Doyle
 */
public class DirectionArrayAdapter extends ArrayAdapter<Stage> {
	
	private Context context;
	private List<Stage> stages;
	
	public DirectionArrayAdapter(Context context,List<Stage> stages) {
		super(context, R.layout.row_directions, stages);
		this.context = context;
		if(stages!=null) {
			this.stages = stages;
		}
	}

	@Override
	public int getCount() {
		return stages.size();
	}

	@Override
	public Stage getItem(int position) {
		return stages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getPosition(Stage item) {
		return stages.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Stage stage = stages.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view;
		
		if(position % 2 == 0) {
			view = inflater.inflate(R.layout.row_directions_odd, parent, false);
		} else {
			view = inflater.inflate(R.layout.row_directions, parent, false);
		}
		
		TextView directionTextView = (TextView) view.findViewById(R.id.directionTextView);
		directionTextView.setText(stage.getInstructions());
		
		TextView distanceTextView = (TextView) view.findViewById(R.id.distanceTextView);
		distanceTextView.setText(stage.getDistance());
		
		return view;
	}
}