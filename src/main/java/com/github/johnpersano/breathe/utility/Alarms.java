package com.github.johnpersano.breathe.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.github.johnpersano.breathe.services.ServiceNewDay;

import java.util.Calendar;

public class Alarms {
	
	private static final int INTERVAL_DAY = 86400000;
	
	private static final int ID_PENDINGINTENT_NEWDAY = 1;

	
	private Context mContext;
	
	
	public Alarms(Context mContext) {
		
		this.mContext = mContext;
		
	}
	
	
	public void setNewDayAlarm() {
		
	    Preferences mPreferences = new Preferences(mContext);
	    
	    String mTime = mPreferences.getString(Preferences.STRING_WAKEUPTIME);
	            
        int mHour = 0;
        int mMinute = 0;
        
	        try{
	        	
	            String[] mTimeArray = mTime.split(":");
	        	
	        	mHour = Integer.parseInt(mTimeArray[0]);
	        	mMinute = Integer.parseInt(mTimeArray[1]);  
	        	
	        } catch (NumberFormatException e) {
	        	
	        	Log.e("Alarms", e.toString());
	        	
	        	mHour = 0;
	        	mMinute= 0;
	        	
	        }
        
        				
		Intent mNewDayIntent = new Intent(mContext, 
				ServiceNewDay.class);
		
		AlarmManager mAlarmManager = (AlarmManager) 
				mContext.getSystemService(Context.ALARM_SERVICE);

		Calendar mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
		mCalendar.set(Calendar.MINUTE, mMinute);
		mCalendar.set(Calendar.SECOND, 0);
		
		Calendar mCurrentCalendar = Calendar.getInstance();
		
		long mAlarmDelay = 0;
		
		// If the date set is in the 'past'
		if(mCurrentCalendar.getTimeInMillis() > mCalendar.getTimeInMillis()) {
			
			mAlarmDelay = INTERVAL_DAY;
			
		} 


		PendingIntent mPendingIntent = PendingIntent.getService(
				mContext, ID_PENDINGINTENT_NEWDAY, mNewDayIntent, 0);
		
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis() + (mAlarmDelay), 
				INTERVAL_DAY, mPendingIntent);
		
		//TODO
		Log.d("ALARMS", "FIRED");

	}

}
