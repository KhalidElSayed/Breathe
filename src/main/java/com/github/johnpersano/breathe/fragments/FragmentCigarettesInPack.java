package com.github.johnpersano.breathe.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.breathe.utility.Preferences;
import com.github.johnpersano.breathe.utility.WizardLocker;


public class FragmentCigarettesInPack extends Fragment {
		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View mView = inflater.inflate(R.layout.fragment_cigarettesinpack,
	    		container, false);
	    
        final Preferences mPreferences = new Preferences(getActivity(),
        		Preferences.CATEGORY_USERINFO);
        
        final int mCigarettesInPack = mPreferences
        		.getInteger(Preferences.INTEGER_CIGARETTESINPACK, 20);
		mPreferences.saveInteger(Preferences.INTEGER_CIGARETTESINPACK, mCigarettesInPack);
		
	    Typeface mLightTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Light.ttf");	   
	    
	    TextView mTitleTextView = (TextView)
	    		mView.findViewById(R.id.titleTextView);
	    mTitleTextView.setTypeface(mLightTypeface);
	    
	    NumberPicker mNumberPicker = (NumberPicker) 
	    		mView.findViewById(R.id.cigarettesinpackNumberPicker);
	    mNumberPicker.setMinValue(1);
	    mNumberPicker.setMaxValue(49);
	    mNumberPicker.setWrapSelectorWheel(false);
	    mNumberPicker.setValue(mCigarettesInPack);	    
	    mNumberPicker.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker mNumberPicker, int oldValue, int newValue) {

				mPreferences.saveInteger(Preferences.INTEGER_CIGARETTESINPACK, newValue);
				
			}	    	
	    	
	    });

		return mView;
		
	}
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if(this.isVisible()) {
			
			WizardLocker mWizardLocker = (WizardLocker) getActivity();
			mWizardLocker.unlockNextButton();
			
		} 
		
	}

}
