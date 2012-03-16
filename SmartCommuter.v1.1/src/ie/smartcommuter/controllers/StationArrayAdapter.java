package ie.smartcommuter.controllers;

import java.util.List;
import ie.smartcommuter.R;
import ie.smartcommuter.models.Station;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StationArrayAdapter extends ArrayAdapter<Station> {
	
	private Context context;
	private List<Station> stations;
	
	public StationArrayAdapter(Context context,  List<Station> stations) {
		super(context, R.layout.row_station, stations);
		this.context = context;
		this.stations = stations;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Station station = stations.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.row_station, parent, false);
		
		TextView stationNameView = (TextView) view.findViewById(R.id.stationNameText);
		ImageView stationImageView = (ImageView) view.findViewById(R.id.stationLogo);
		stationNameView.setText(station.getName());
		
		// TODO: Alternate bg color for each row.
		if(station.getCompany().getName().equals("Bus Éireann")) {
			stationImageView.setImageResource(R.drawable.img_bus_eireann);
		} else if(station.getCompany().getName().equals("Dublin Bus")) {
			stationImageView.setImageResource(R.drawable.img_dublin_bus);
		} else if(station.getCompany().getName().equals("Irish Rail")) {
			stationImageView.setImageResource(R.drawable.img_irish_rail);
		} else if(station.getCompany().getName().equals("JJ Kavanagh & Sons")) {
			stationImageView.setImageResource(R.drawable.img_jj_kavanagh);
		} else if(station.getCompany().getName().equals("Luas")) {
			stationImageView.setImageResource(R.drawable.img_luas);
		}
		
		return view;
	}
}
