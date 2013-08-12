package com.github.johnpersano.breathe.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.johnpersano.breathe.ActivityMain;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.breathe.services.ServiceNewDay;
import com.github.johnpersano.breathe.utility.Alarms;
import com.github.johnpersano.breathe.utility.Preferences;
import com.github.johnpersano.breathe.utility.WizardLocker;

import java.util.ArrayList;


public class FragmentReview extends Fragment {

    TextView dailysmokedTextView;
    TextView priceperpackTextView;
    TextView cigarettesperpackTextView;
    
    
	@Override
	public View onCreateView(LayoutInflater mLayoutInflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View mView = mLayoutInflater.inflate(R.layout.fragment_review,
	    		container, false); 
	    
	    Typeface mTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Light.ttf");	
	    
	    TextView mTitleTextView = (TextView)
	    		mView.findViewById(R.id.titleTextView);
	    mTitleTextView.setTypeface(mTypeface);
	    
	    showCards(mView, mLayoutInflater);
	    	    	    
		return mView;
		
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if(this.isVisible()) {
			
			WizardLocker mWizardLocker = (WizardLocker) getActivity();
			mWizardLocker.lockNextButton();
			
	        final Preferences mPreferences = new Preferences(getActivity(),
	        		Preferences.CATEGORY_USERINFO);
	        
	        final int mDailySmoked = mPreferences
	        		.getInteger(Preferences.INTEGER_DAILYSMOKED);
	        
	        final int mDollarsPerPackPack = mPreferences
	        		.getInteger(Preferences.INTEGER_DOLLARPERPACK);
	        
	        final int mCentsPerPackPack = mPreferences
	        		.getInteger(Preferences.INTEGER_CENTSERPACK);
	        
	        final int mCigarettesInPack = mPreferences
	        		.getInteger(Preferences.INTEGER_CIGARETTESINPACK);
	        
		    dailysmokedTextView.setText(String.valueOf(mDailySmoked));

		    priceperpackTextView.setText("$" + String.valueOf(
		    		getPricePerPack(mDollarsPerPackPack, mCentsPerPackPack)));
		    
		    cigarettesperpackTextView.setText(String.valueOf(mCigarettesInPack));
		    
		} 
		
	}

	
	private float getPricePerPack(int dollars, int cents) {
		
		float formattedCents = (float) (cents * .01);
		
		return (dollars + formattedCents);
		
	}
	
	
	private void showCards(View mView, LayoutInflater mLayoutInflater) {
	    
	    LayoutAnimationController mLayoutAnimationController = AnimationUtils
				   .loadLayoutAnimation(getActivity(), R.anim.layout_animation);
 	
	    LinearLayout mCardContainer = (LinearLayout)
	    		mView.findViewById(R.id.card_container);
	    
	    ArrayList<View> mArrayList = new ArrayList<View>();
	    	    		    
		mArrayList.add(getReviewView(mLayoutInflater));
		mArrayList.add(getStartView(mLayoutInflater));

		
        for (int i = 0; i < mArrayList.size(); i++) {        	
        	
    		final View view = mArrayList.get(i);
    		
    		mCardContainer.addView(view);
    		
    		mCardContainer.invalidate();
            
        }

        mCardContainer.setLayoutAnimation(mLayoutAnimationController);		
		
	}
	
	
	private View getReviewView(LayoutInflater mLayoutInflater) {
    	
	    View mReviewView = mLayoutInflater.inflate(R.layout.view_review, 
	    		null);
        
        dailysmokedTextView = (TextView)
	    		mReviewView.findViewById(R.id.dailysmokedTextView);
	    
        priceperpackTextView = (TextView)
	    		mReviewView.findViewById(R.id.priceperpackTextView);

        cigarettesperpackTextView = (TextView)
	    		mReviewView.findViewById(R.id.cigarettesperpackTextView);
	    
	    return mReviewView;
	    
	}
	
	
	private View getStartView(LayoutInflater mLayoutInflater) {
    	
	    View mStartView = mLayoutInflater.inflate(R.layout.view_getstarted, 
	    		null);
	    
	    Typeface mTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Light.ttf");	
	    
	    TextView mTitleTextView = (TextView)
	    		mStartView.findViewById(R.id.titleTextView);
	    mTitleTextView.setTypeface(mTypeface);

	    
	    Button mStartButton = (Button)
	    		mStartView.findViewById(R.id.startButton);
	    mStartButton.setOnClickListener(new OnClickListener(){
	    	
	    	@Override
	    	public void onClick(View view) {
	    		
	            final Preferences mPreferences = new Preferences(getActivity(), 
	            		Preferences.CATEGORY_USERINFO);
	            
				Intent mServiceIntent = new Intent(getActivity(), ServiceNewDay.class);
				getActivity().startService(mServiceIntent);
	    		
				mPreferences.saveBoolean(Preferences.BOOLEAN_OPENEDBEFORE, true);
	    		
				Alarms mAlarms = new Alarms(getActivity());
				mAlarms.setNewDayAlarm();
				
				Intent mIntent = new Intent(getActivity(), ActivityMain.class);
				getActivity().startActivity(mIntent);
				
				getActivity().finish();
	    		
	    	}
	    	
	    });
	    
	    
	    return mStartView;
	    
	}
	

}
