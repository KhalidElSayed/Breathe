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


public class FragmentPricePack extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View mView = inflater.inflate(R.layout.fragment_pricepack,
	    		container, false); 
	    
        final Preferences mPreferences = new Preferences(getActivity(),
        		Preferences.CATEGORY_USERINFO);
        
        final int mDollarPerPack = mPreferences
        		.getInteger(Preferences.INTEGER_DOLLARPERPACK, 8);
		mPreferences.saveInteger(Preferences.INTEGER_DOLLARPERPACK, mDollarPerPack);

        final int mCentsPerPack = mPreferences
        		.getInteger(Preferences.INTEGER_CENTSERPACK, 75);
		mPreferences.saveInteger(Preferences.INTEGER_CENTSERPACK, mCentsPerPack);

	    Typeface mLightTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Light.ttf");	   
	    
	    TextView mTitleTextView = (TextView)
	    		mView.findViewById(R.id.titleTextView);
	    mTitleTextView.setTypeface(mLightTypeface);
	    
	    
	    final NumberPicker dollarNumberPicker = (NumberPicker)
	    		mView.findViewById(R.id.dollarNumberPicker);
	    dollarNumberPicker.setMinValue(1);
	    dollarNumberPicker.setMaxValue(99);
	    dollarNumberPicker.setWrapSelectorWheel(false);
	    dollarNumberPicker.setValue(mDollarPerPack);
	    dollarNumberPicker.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker mNumberPicker, int oldValue, int newValue) {

				mPreferences.saveInteger(Preferences.INTEGER_DOLLARPERPACK, newValue);
	    
			}	    	
	    	
	    });

	    final NumberPicker centsNumberPicker = (NumberPicker)
	    		mView.findViewById(R.id.centsNumberPicker);
	    centsNumberPicker.setMinValue(00);
	    centsNumberPicker.setMaxValue(99);
	    centsNumberPicker.setWrapSelectorWheel(false);
    	centsNumberPicker.setValue(mCentsPerPack);
	    centsNumberPicker.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker mNumberPicker, int oldValue, int newValue) {

				mPreferences.saveInteger(Preferences.INTEGER_CENTSERPACK, newValue);
	    
			}	    	
	    	
	    });

		return mView;
	
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if(this.isVisible()) {
			
			WizardLocker mWizardLocker = (WizardLocker) getActivity();

		}
		
	}
	

}
