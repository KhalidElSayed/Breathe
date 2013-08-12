package com.github.johnpersano.breathe.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.github.johnpersano.breathe.fragments.FragmentCigarettesInPack;
import com.github.johnpersano.breathe.fragments.FragmentDailySmoked;
import com.github.johnpersano.breathe.fragments.FragmentPricePack;

public class AdapterUserInfo extends FragmentPagerAdapter {

	
	private boolean mOpenedBefore;
	
    public AdapterUserInfo(FragmentManager mFragmentManager) {
        super(mFragmentManager);
        
    }

    @Override
    public Fragment getItem(int position) {
    	
    	switch(position) {
    	
	    	case 0:
	    		    		    			
				return new FragmentDailySmoked();
	    		
	    		
	    	case 1:
	    		
				return new FragmentPricePack();
				
	    		
	    	case 2:
	    		
				return new FragmentCigarettesInPack();
				
	    		
	    	default:
	    		
				return new FragmentDailySmoked();

    	}
    	
    	
    }

    
	@Override
	public int getCount() {
		
		return 4;
		
	}


}