package ie.smartcommuter.controllers;

import ie.smartcommuter.R;
import ie.smartcommuter.models.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is a class is used to display stations in a list.
 * @author Shane Doyle
 */
public class StationArrayAdapter extends ArrayAdapter<Station>{
	
	private Context mContext;
	private List<Station> mStations;
	private List<Station> mOriginalStations;
	private Filter mFilter;
	private final Object mLock = new Object();
	private String mStationModeFilter;
	
	public StationArrayAdapter(Context context,  List<Station> stations) {
		super(context, R.layout.list_item_station, stations);
		
		this.mContext = context;
		this.mStations = stations;
		this.mOriginalStations = stations;
		this.mStationModeFilter = "All";
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Station station = mStations.get(position);
		
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view;
		
		if(position % 2 == 0) {
			view = inflater.inflate(R.layout.list_item_station, parent, false);
		} else {
			view = inflater.inflate(R.layout.list_item_station_odd, parent, false);
		}
		
		view.setId(station.getId());
		
		TextView stationNameView = (TextView) view.findViewById(R.id.text_station_name);
		ImageView stationImageView = (ImageView) view.findViewById(R.id.image_view_station_logo);
		stationNameView.setText(station.getName());
		stationImageView.setImageResource(station.getStationLogo());
		
		return view;
	}
	
	@Override
	public int getCount() {
		return mStations.size();
	}
	
	@Override
	public Station getItem(int position) {
		return mStations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
    @Override
    public int getPosition(Station station) {
        return mStations.indexOf(station);
    }
	
    /**
     * This method is used to get the filter
     * that will be used to filter the list
     * of stations.
     */
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
            
            if (mStations == null) {
                synchronized (mLock) {
                    mStations = new ArrayList<Station>();
                }
            }
            if (prefix == null || prefix.length() == 0) {
        		synchronized (mLock) {
        			
        			if(mStationModeFilter.equals("All") ){
                        results.values = mOriginalStations;
                        results.count = mOriginalStations.size();
        			} else {
        				final List<Station> newStations = new ArrayList<Station>();
        				for(Station station: mOriginalStations) {
        					if(mStationModeFilter.equals(station.getCompany().getMode())) {
                        		newStations.add(station);
                        	}
        				}
                        results.values = newStations;
                        results.count = newStations.size();
        			}
        			
        		}
            } else {
                String prefixString = prefix.toString().toLowerCase(Locale.UK);

                final List<Station> newStations = new ArrayList<Station>();
                
                for(Station station: mOriginalStations) {
                	final String itemName = station.getName().toString().toLowerCase(Locale.UK);
                    if (itemName.contains(prefixString)) {
                    	if(mStationModeFilter.equals("All") ){
                    		newStations.add(station);
                    	} else if(mStationModeFilter.equals(station.getCompany().getMode())) {
                    		newStations.add(station);
                    	}
                    }
                }
                results.values = newStations;
                results.count = newStations.size();
            }
            
            return results;
        }
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence prefix, FilterResults results) {
            mStations = (ArrayList<Station>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
    
    /**
     * This method is used to update the mode of
     * stations to be shown in the list.
     * @param mode
     */
    public void updateStationModeFilter(String mode) {
    	this.mStationModeFilter = mode;
    }
}
