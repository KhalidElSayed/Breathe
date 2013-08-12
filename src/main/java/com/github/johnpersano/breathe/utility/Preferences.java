package com.github.johnpersano.breathe.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
	
	public static final String CATEGORY_USERINFO = "category_userinfo";
	
	
	public static final String BOOLEAN_OPENEDBEFORE = "boolean_openedbefore";
	public static final String BOOLEAN_TARGETENABLED = "boolean_targetenabled";
	public static final String BOOLEAN_QUOTEENABLED = "boolean_quoteenabled";
	public static final String BOOLEAN_CDCRSSENABLED = "boolean_cdcrssenabled";
	public static final String BOOLEAN_DATEENABLED = "boolean_dateenabled";
	public static final String BOOLEAN_JOHNSRSSNABLED = "boolean_johnsrssenabled";
	
	
	public static final String INTEGER_DAILYSMOKED = "integer_dailysmoked";
	public static final String INTEGER_DOLLARPERPACK = "integer_dollarperpack";
	public static final String INTEGER_CENTSERPACK = "integer_centsperpack";
	public static final String INTEGER_CIGARETTESINPACK = "integer_cigarettesinpack";
	
	public static final String STRING_WAKEUPTIME = "string_wakeuptime";
	public static final String STRING_USERNAME = "string_username";
	
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mSharedPreferencesEditor;
	
	
	public Preferences(Context mContext) {
		
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		mSharedPreferencesEditor = mSharedPreferences.edit();

	}
	
	public Preferences(Context mContext, String mPreferences) {
		
		mSharedPreferences = mContext.getSharedPreferences(mPreferences, 0);
		mSharedPreferencesEditor = mSharedPreferences.edit();

	}
	
	public int getInteger(String mKey) {
		
		final int mInteger = mSharedPreferences
				.getInt(mKey, 0);
		
		return mInteger;
		
	}
	
	public int getInteger(String mKey, int defaultValue) {
		
		final int mInteger = mSharedPreferences
				.getInt(mKey, defaultValue);
		
		return mInteger;
		
	}
	
	public boolean getBoolean(String mKey) {
		
		final boolean mBoolean = mSharedPreferences
				.getBoolean(mKey, false);
		
		return mBoolean;
		
	}
	
	public boolean getBoolean(String mKey, boolean defaultValue) {
		
		final boolean mBoolean = mSharedPreferences
				.getBoolean(mKey, defaultValue);
		
		return mBoolean;
		
	}
	
	public String getString(String mKey) {
		
		final String mString = mSharedPreferences
				.getString(mKey, "");
		
		return mString;
		
	}
	
	public void saveInteger(String mKey, int value) {
		
		mSharedPreferencesEditor.putInt(mKey, value);
		mSharedPreferencesEditor.commit();
			
	}
	
	public void saveBoolean(String mKey, boolean value) {
		
		mSharedPreferencesEditor.putBoolean(mKey, value);
		mSharedPreferencesEditor.commit();
			
	}

}
