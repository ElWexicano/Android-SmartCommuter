package ie.smartcommuter.controllers;

import java.util.ArrayList;
import java.util.List;
import ie.smartcommuter.R;
import ie.smartcommuter.models.Station;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is a class is used to display a stations in a list.
 * @author Shane Bryan Doyle
 */
public class StationArrayAdapter extends ArrayAdapter<Station> {
	
	private Context context;
	private List<Station> stations;
	private List<Station> originalStations;
	private Filter mFilter;
	private final Object mLock = new Object();
	
	public StationArrayAdapter(Context context,  List<Station> stations) {
		super(context, R.layout.row_station, stations);
		this.context = context;
		this.stations = stations;
		this.originalStations = stations;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Station station = stations.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.row_station, parent, false);
		
		view.setId(station.getId());
		
		TextView stationNameView = (TextView) view.findViewById(R.id.stationNameText);
		ImageView stationImageView = (ImageView) view.findViewById(R.id.stationLogo);
		stationNameView.setText(station.getName());
		
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
	
	@Override
	public int getCount() {
		return stations.size();
	}
	
	@Override
	public Station getItem(int position) {
		return stations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
    @Override
    public int getPosition(Station station) {
        return stations.indexOf(station);
    }
	
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new StationsFilter();
        }
        return mFilter;
    }

    /**
     * This class is used to filter through the list of 
     * stations on the Search Stations screen.
     * @author Shane Bryan Doyle
     *
     */
    private class StationsFilter extends Filter {
        protected FilterResults performFiltering(CharSequence prefix) {

            FilterResults results = new FilterResults();
            
            if (stations == null) {
                synchronized (mLock) {
                    stations = new ArrayList<Station>(originalStations);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {
                    results.values = originalStations;
                    results.count = originalStations.size();
                }
            } else {
                String prefixString = prefix.toString().toLowerCase();
                final int count = stations.size();
                final List<Station> newStations = new ArrayList<Station>(count);
                for (int i = 0; i < count; i++) {
                    final Station station = stations.get(i);
                    final String itemName = station.getName().toString().toLowerCase();
                    if (itemName.startsWith(prefixString)) {
                        newStations.add(station);
                    } else {}
                }
                results.values = newStations;
                results.count = newStations.size();
            }
            return results;
        }
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence prefix, FilterResults results) {
            stations = (ArrayList<Station>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
