package com.github.johnpersano.breathe.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.github.johnpersano.breathe.R;

public class FragmentPreferences extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	 
	 super.onCreate(savedInstanceState);
  
     addPreferencesFromResource(R.xml.preferences);
     
	}

}