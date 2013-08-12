package com.github.johnpersano.breathe.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.breathe.utility.WizardLocker;


public class FragmentWelcome extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View mView = inflater.inflate(R.layout.fragment_welcome,
	    		container, false);
	    
	    Typeface mThinTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Thin.ttf");	    
	    
	    Typeface mLightTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Light.ttf");	   
	    
	    TextView mTitleTextView = (TextView)
	    		mView.findViewById(R.id.titleTextView);
	    mTitleTextView.setTypeface(mLightTypeface);


		return mView;
	
		
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if(this.isVisible()) {
			
			WizardLocker mWizardLocker = (WizardLocker) getActivity();
			mWizardLocker.lockPreviousButton();
			
		}
		
	}
	

}
