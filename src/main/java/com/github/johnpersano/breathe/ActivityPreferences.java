package com.github.johnpersano.breathe;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.github.johnpersano.breathe.fragments.FragmentPreferences;

public class ActivityPreferences extends FragmentActivity {
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new FragmentPreferences()).commit();

    }
    
}
