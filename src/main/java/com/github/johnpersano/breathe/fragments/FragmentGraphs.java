package com.github.johnpersano.breathe.fragments;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.breathe.adapters.AdapterGraphs;


public class FragmentGraphs extends Fragment {
			
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.fragment_graphviewpager,
	    		container, false);
	    
	    AdapterGraphs adapterGraphs = new AdapterGraphs
				(getChildFragmentManager());
	    
	    Typeface lightTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Light.ttf");	
	    
	    final TextView titleTextView = (TextView) 
	    		view.findViewById(R.id.titleTextView);
	    titleTextView.setTypeface(lightTypeface);

	    
	    final Resources resources = getActivity().getResources();
	    
	    ViewPager viewPager = (ViewPager) 
	    		view.findViewById(R.id.pager);
	    viewPager.setAdapter(adapterGraphs);
	    
	    viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
								
				// Do nothing
				
			}

			@Override
			public void onPageScrolled(int position, float offset, int offsetPixels) {
				
				if (offset < .5 && offset != 0) {

					titleTextView.setAlpha(Math.max(0f,
							Math.min(1f, 1f - 2f * Math.abs(offset))));

					titleTextView.setText(resources
							.getString(R.string.smoked_history));

				} else if (offset > .5 && offset != 0) {

					titleTextView.setAlpha(Math.max(0f,
							Math.min(1f, 1f - 2f * Math.abs(1 - offset))));

					titleTextView.setText("Money");

				}

			}
			

			@Override
			public void onPageSelected(int position) {
				
				// Do nothing

			}

	    });
	    
		return view;
		
	}


}
