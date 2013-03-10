package ie.smartcommuter.controllers.screens;

import ie.smartcommuter.R;
import ie.smartcommuter.controllers.SmartActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * This class is used to display the tutorial to the user.
 * 
 * @author Shane Doyle
 */
public class TutorialActivity extends SmartActivity {

	private ViewFlipper mViewFlipper;
	private TextView mPreviousTextView;
	private TextView mNextTextView;
	private int mNumberSlides;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tutorial);

		mViewFlipper = (ViewFlipper) findViewById(R.id.tutorialSwitcher);

		mPreviousTextView = (TextView) findViewById(R.id.previousTextView);
		mPreviousTextView.setVisibility(View.INVISIBLE);
		mPreviousTextView
				.setOnClickListener(new TutorialClickedListener(false));

		mNextTextView = (TextView) findViewById(R.id.nextTextView);
		mNextTextView.setOnClickListener(new TutorialClickedListener(true));

		mNumberSlides = mViewFlipper.getChildCount() - 1;
	}

	/**
	 * This class is used to listen to when user clicks either back or forward.
	 * 
	 * @author Shane Doyle
	 */
	private class TutorialClickedListener implements OnClickListener {

		private boolean mViewNext;

		public TutorialClickedListener(boolean viewNext) {
			this.mViewNext = viewNext;
		}

		public void onClick(View arg0) {

			int currentSlide = mViewFlipper.getDisplayedChild();

			if (mViewNext) {
				if (currentSlide < mNumberSlides) {
					mViewFlipper.showNext();
					if (currentSlide == 0) {
						mPreviousTextView.setVisibility(View.VISIBLE);
					}
					if (currentSlide == (mNumberSlides - 1)) {
						mNextTextView.setText(R.string.finish);
					}
				} else {
					finish();
				}
			} else {
				if (currentSlide > 0) {

					if (currentSlide == mNumberSlides) {
						mNextTextView.setText(R.string.next);
					}

					if (currentSlide == 1) {
						mPreviousTextView.setVisibility(View.INVISIBLE);
					}

					mViewFlipper.showPrevious();
				}
			}
		}
	}
}