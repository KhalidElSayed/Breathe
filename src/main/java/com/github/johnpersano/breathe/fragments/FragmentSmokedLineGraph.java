package com.github.johnpersano.breathe.fragments;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.breathe.databases.UserData;
import com.github.johnpersano.breathe.databases.UserDataHelper;
import com.github.johnpersano.breathe.graphs.Line;
import com.github.johnpersano.breathe.graphs.LineGraph;
import com.github.johnpersano.breathe.graphs.LinePoint;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FragmentSmokedLineGraph extends Fragment {
	
	private LineGraph lineGraph;
	private TextView targetTextView;
	private TextView smokedTextView;
	private TextView dateTextView;
	
	private int forDays;

		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.fragment_smokedlinegraph,
	    		container, false); 
	    
        final Resources resources = getActivity().getResources();
        
	    Typeface boldCondensedTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-BoldCondensedItalic.ttf");	

        lineGraph = (LineGraph)
        		view.findViewById(R.id.linegraph);
        
        targetTextView = (TextView) 
	    		view.findViewById(R.id.targetTextView);
    	targetTextView.setText(String.format(resources.getString(R.string.target_data), 0));

        smokedTextView = (TextView) 
	    		view.findViewById(R.id.smokedTextView);
    	smokedTextView.setText(String.format(resources.getString(R.string.smoked_data), 0));

        dateTextView = (TextView) 
	    		view.findViewById(R.id.dateTextView);    	
        dateTextView.setTypeface(boldCondensedTypeface);
        
	    Spinner spinner = (Spinner) 
	    		view.findViewById(R.id.dateSpinner);
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> AdapterView, View view,
					int position, long arg3) {

				switch(position) {
				
					case 0:
				
						forDays = 3;
						
						drawGraph();
						
						break;
						
						
					case 1:
						
						forDays = 5;

						drawGraph();
						
						break;
				
						
					case 2:
					
						forDays = 7;

						drawGraph();
						
						break;
				
					case 3:
						
						forDays = 14;

						drawGraph();
						
						break;
						
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				
				// Do nothing
				
			}
	    		    	
	    });

        


		return view;
		
	}
	
	
	private void drawGraph() {
	    
		final UserDataHelper userDataHelper = new UserDataHelper(getActivity());
        
        final int dataCount = userDataHelper.getDataCount();
        
        final Resources resources = getActivity().getResources();
        
        if(forDays >= dataCount) {
        	
        	lineGraph.setVisibility(View.GONE);
        	
        	// Hack to prevent sequential SuperActivityToasts
        	SuperActivityToast.cancelAllSuperActivityToasts();
        	
        	SuperActivityToast.createDarkSuperActivityToast(getActivity(), 
        			resources.getString(R.string.your_selection_outofbounds), SuperToast.DURATION_MEDIUM).show();
        	
        	return;
        	
        } else {
        	
        	lineGraph.setVisibility(View.VISIBLE);
        	lineGraph.removeAllLines();

        }
        
        Line smokedLine = new Line();
    	smokedLine.setColor(getActivity().getResources().getColor(R.color.holo_orange_light));
    	
        Line targetLine = new Line();
        targetLine.setColor(getActivity().getResources().getColor(R.color.holo_blue_dark));
        
        int highestNumber = 0;
        
    	for (int i = dataCount - forDays; i < dataCount; i++) {

			UserData mLatestUserData = userDataHelper.getUserData(i);
			
			int daySmoked = mLatestUserData.getDaySmoked();
			int dayTarget = mLatestUserData.getDayTarget();

			if(highestNumber < daySmoked) {
				
				highestNumber = daySmoked;
				
			}
			
			if(highestNumber < dayTarget) {
				
				highestNumber = dayTarget;
			}
				
		    LinePoint smokedLinePoint = new LinePoint();
		    smokedLinePoint.setX(i);
		    smokedLinePoint.setY(daySmoked);
		    smokedLine.addPoint(smokedLinePoint);
		    
		    LinePoint targetLinePoint = new LinePoint();
		    targetLinePoint.setX(i);
		    targetLinePoint.setY(dayTarget);
		    targetLine.addPoint(targetLinePoint);
    				
    	}		
    	
        lineGraph.addLine(smokedLine);
        lineGraph.setRangeY(0, highestNumber);
        lineGraph.setLineToFill(0);
        
        lineGraph.addLine(targetLine);
        lineGraph.setRangeY(0, highestNumber);
        lineGraph.setLineToFill(0);
        
        lineGraph.setOnPointClickedListener(new LineGraph.OnPointClickedListener(){
        	
        	@Override
            public void onClick(int lineIndex, int pointIndex) {

    			UserData userData = userDataHelper.getUserData((dataCount - forDays) + pointIndex);
    			
    			int daySmoked = userData.getDaySmoked();
    			int dayTarget = userData.getDayTarget();
    			
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTimeInMillis(userData.getCalendarTime());
    			
    	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
    	                "MMMM dd, yyyy");
    	    	
    	    	targetTextView.setText(String.format(resources.getString(R.string.target_data), dayTarget));
    	    	
    	    	smokedTextView.setText(String.format(resources.getString(R.string.smoked_data), daySmoked));
    	    	
    	    	dateTextView.setText(simpleDateFormat
    	    			.format(calendar.getTime()));
    					    	    			
    		}

        });
		
	}
	
	
	
	
	

}






