package com.github.johnpersano.breathe.utility;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class NamePreference extends EditTextPreference {
	

	public NamePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	
	@Override
	public CharSequence getSummary() {		
		
		return getPersistedString("");
		
	}
    
	
} 




