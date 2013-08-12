package com.github.johnpersano.breathe;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.github.johnpersano.breathe.adapters.AdapterUserInfo;
import com.github.johnpersano.breathe.adapters.AdapterWelcome;
import com.github.johnpersano.breathe.utility.LinePageIndicator;
import com.github.johnpersano.breathe.utility.Preferences;
import com.github.johnpersano.breathe.utility.WizardLocker;

public class ActivityUserInfo extends FragmentActivity implements WizardLocker {

	private ViewPager mViewPager;
	private LinePageIndicator mIndicator;
	private Button mPreviousButton;
	private Button mNextButton;
	private FragmentPagerAdapter mPagerAdapter;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		
        final Preferences mPreferences = new Preferences(this,
        		Preferences.CATEGORY_USERINFO);
        
        final boolean mOpenedBefore = mPreferences
        		.getBoolean(Preferences.BOOLEAN_OPENEDBEFORE);
        
        if(!mOpenedBefore) {

        	mPagerAdapter = new AdapterWelcome
    				(getSupportFragmentManager());
        	
        } else {
        	
        	mPagerAdapter = new AdapterUserInfo
    				(getSupportFragmentManager());
        	
        }

		
		mViewPager = (ViewPager) 
				findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);

		
		mPreviousButton = (Button)
				findViewById(R.id.previousButton);
		mPreviousButton.setEnabled(false);
		mPreviousButton.setTextColor(Color.LTGRAY);
		
		mPreviousButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if(mViewPager.getCurrentItem() > 0) {
					
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
					
				}
				
			}

		});
		
		mNextButton = (Button)
				findViewById(R.id.nextButton);
		
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if((mViewPager.getCurrentItem() + 1) < mPagerAdapter.getCount()) {
					
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
										
				} 
				
			}

		});

        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);
        
        final float density = getResources().getDisplayMetrics().density;
        mIndicator.setSelectedColor(0xFF33B5E5);
        mIndicator.setUnselectedColor(0xFF888888);
        
        if(mOpenedBefore) {

            mIndicator.setLineNumber(4);

        	
        } else {
        	
            mIndicator.setLineNumber(4);
        	
        }
        
        mIndicator.setFinalColor(0xFF99CC00);
        mIndicator.setPreviousColor(0x6433B5E5);
        mIndicator.setStrokeWidth(4 * density);
        mIndicator.setLineWidth(30 * density);
		
	}

	@Override
	public void lockPreviousButton() {

		mPreviousButton.setEnabled(false);
		mPreviousButton.setTextColor(Color.LTGRAY);
		
	}

	@Override
	public void lockNextButton() {

		mNextButton.setEnabled(false);
		mNextButton.setTextColor(Color.LTGRAY);
		
	}

	@Override
	public void lockViewPager() {
		
		mViewPager.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				//Intercept touch events to lock the ViewPager
				return false;
			}

		});
		
	}

	@Override
	public void unlockPreviousButton() {
		
		mPreviousButton.setEnabled(true);
		mPreviousButton.setTextColor(Color.BLACK);		
		
	}

	@Override
	public void unlockNextButton() {

		mNextButton.setEnabled(true);
		mNextButton.setTextColor(Color.BLACK);		
		
	}

	@Override
	public void unlockViewPager() {
		
		mViewPager.setOnTouchListener(null);

	}


}
