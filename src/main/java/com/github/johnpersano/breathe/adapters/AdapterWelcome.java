package com.github.johnpersano.breathe.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.github.johnpersano.breathe.fragments.*;

public class AdapterWelcome extends FragmentPagerAdapter {

	
	private boolean mOpenedBefore;
	
    public AdapterWelcome(FragmentManager mFragmentManager) {
        super(mFragmentManager);
        
    }

    @Override
    public Fragment getItem(int position) {
    	
    	switch(position) {
    	
	    	case 0:
	    		    		    			
	    		return new FragmentWelcome();
	    		
	    		
	    	case 1:
	    		
				return new FragmentDailySmoked();
				
				
	    	case 2:
	    		
				return new FragmentPricePack();
				
				
	    	case 3:
	    		
				return new FragmentCigarettesInPack();
				
				
	    	case 4:
	    		
				return new FragmentReview();
	    			    		
	    		
	    	default:
	    		
	    		
				return new FragmentDailySmoked();

    	}
    	
    	
    }

	@Override
	public int getCount() {
		
		return 5;
		
	}

}