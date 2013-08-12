package com.github.johnpersano.breathe.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.breathe.databases.UserData;
import com.github.johnpersano.breathe.databases.UserDataHelper;
import com.github.johnpersano.breathe.graphs.Bar;
import com.github.johnpersano.breathe.graphs.BarGraph;
import com.github.johnpersano.breathe.utility.Preferences;

import java.util.ArrayList;


public class FragmentMoneyBarGraph extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.fragment_moneybargraph,
	    		container, false); 
	    
        final Resources resources = getActivity().getResources();

        final Preferences mPreferences = new Preferences(getActivity(),
        		Preferences.CATEGORY_USERINFO);
        
        final int dollarsPerPackInteger = mPreferences
        		.getInteger(Preferences.INTEGER_DOLLARPERPACK);

        final int centsPerPackInteger = mPreferences
        		.getInteger(Preferences.INTEGER_CENTSERPACK);
        
        final int dailySmoked = mPreferences
        		.getInteger(Preferences.INTEGER_DAILYSMOKED);
        
        final int cigarettesInPack = mPreferences
        		.getInteger(Preferences.INTEGER_CIGARETTESINPACK);
                
        float centsPerPack = centsPerPackInteger * (0.01f);
        
        float pricePerPack = dollarsPerPackInteger + centsPerPack;

		final UserDataHelper userDataHelper = new UserDataHelper(getActivity());
        
        final int dataCount = userDataHelper.getDataCount();
        
        float moneyPissed = (((((float)dataCount - 1) * dailySmoked) / cigarettesInPack) * pricePerPack);
        
        float avg = ((float)moneyPissed) / dataCount - 1;
        
        int realSmoked = 0;
        
    	for (int i = 1; i < dataCount; i++) {

			UserData mLatestUserData = userDataHelper.getUserData(i);
			
			realSmoked += mLatestUserData.getDaySmoked();
    				
    	}	
    	
    	float moneyPissedh = (((((float)realSmoked) / cigarettesInPack) * pricePerPack));
        
        float avgj = ((float)moneyPissedh) / dataCount - 1;

		ArrayList<Bar> points = new ArrayList<Bar>();

		Bar initialBar = new Bar();
		initialBar.setColor(resources.getColor(R.color.holo_red_light));
		initialBar.setBarTopLabel("$ " + String.format("%.2f", avg));
		initialBar.setValue(8);

		Bar currentBar = new Bar();
		currentBar.setColor(resources.getColor(R.color.holo_purple));
		currentBar.setBarTopLabel("$ " + String.format("%.2f", avgj));
		currentBar.setValue(5);

		points.add(initialBar);
		points.add(currentBar);

		BarGraph barGraph = (BarGraph) view.findViewById(R.id.graph);
		barGraph.addBar(initialBar);
		barGraph.addBar(currentBar);


        
        
        
		return view;

	}
	

}
