package com.github.johnpersano.breathe.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.github.johnpersano.breathe.fragments.FragmentMoneyBarGraph;
import com.github.johnpersano.breathe.fragments.FragmentSmokedLineGraph;

public class AdapterGraphs extends FragmentPagerAdapter {

	
	private boolean mOpenedBefore;
	
    public AdapterGraphs(FragmentManager mFragmentManager) {
        super(mFragmentManager);
        
    }

    @Override
    public Fragment getItem(int position) {
    	
    	switch(position) {
    	
	    	case 0:
	    		    		    			
				return new FragmentSmokedLineGraph();
	    		
	    		
	    	case 1:
	    		
				return new FragmentMoneyBarGraph();
	
	    		
	    	default:
	    		
				return new FragmentSmokedLineGraph();

    	}
    	
    	
    }

    
	@Override
	public int getCount() {
		
		return 2;
		
	}


}