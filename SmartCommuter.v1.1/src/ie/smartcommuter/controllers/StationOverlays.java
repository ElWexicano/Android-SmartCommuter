package ie.smartcommuter.controllers;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * This is a class is used to add Overlays to Google Map
 * @author Shane Bryan Doyle
 */
public class StationOverlays extends ItemizedOverlay<OverlayItem> {

	public StationOverlays(Drawable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}