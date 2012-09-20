package ie.smartcommuter.controllers.screens;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ViewFlipper;
import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;

/**
 * This class is used to display the tutorial
 * to the user.
 * @author Shane Bryan Doyle
 */
public class TutorialActivity extends SmartActivity {

	private ViewFlipper viewFlipper;
	private TextView previousTextView;
	private TextView nextTextView;
	private int numberSlides;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.screen_tutorial);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.tutorialSwitcher);
		
		previousTextView = (TextView) findViewById(R.id.previousTextView);
		previousTextView.setVisibility(View.INVISIBLE);
		previousTextView.setOnClickListener(new TutorialClickedListener(false));
		
		nextTextView = (TextView) findViewById(R.id.nextTextView);
		nextTextView.setOnClickListener(new TutorialClickedListener(true));
		
		numberSlides = viewFlipper.getChildCount() - 1;
	}
	
	/**
	 * This class is used to listen to when user clicks
	 * either back or forward.
	 * @author Shane Bryan Doyle
	 */
	private class TutorialClickedListener implements OnClickListener {
		
		private boolean viewNext;
		
		public TutorialClickedListener(boolean viewNext) {
			this.viewNext = viewNext;
		}
		
		public void onClick(View arg0) {
			
			int currentSlide = viewFlipper.getDisplayedChild();
			
			if(viewNext) {
				if(currentSlide<numberSlides) {
					viewFlipper.showNext();
					if(currentSlide==0) {
						previousTextView.setVisibility(View.VISIBLE);
					}
					if(currentSlide==(numberSlides-1)) {
						nextTextView.setText(R.string.finishText);
					}
				} else {
					finish();
				}
			} else {
				if(currentSlide>0) {
					
					if(currentSlide==numberSlides) {
						nextTextView.setText(R.string.nextText);
					}
					
					if(currentSlide==1) {
						previousTextView.setVisibility(View.INVISIBLE);
					}
					
					viewFlipper.showPrevious();
				}
			}
		}
	}
}












