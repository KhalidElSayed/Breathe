package com.github.johnpersano.breathe.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.github.johnpersano.breathe.databases.UserData;
import com.github.johnpersano.breathe.databases.UserDataHelper;
import com.github.johnpersano.breathe.utility.Preferences;

import java.util.Calendar;

public class ServiceNewDay extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
		
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
				
		UserDataHelper mUserDataHelper = new UserDataHelper(this);
		
		//Application has given data before
		if(mUserDataHelper.getDataCount() > 0) {
			
			UserData mLatestUserData = mUserDataHelper.getUserData(mUserDataHelper
					.getDataCount());
			
			int newTarget = calculateNewTarget(mLatestUserData.getDayTarget(), 
					mLatestUserData.getDaySmoked());
			
			Calendar mNewCalendar = Calendar.getInstance();
			int newDay = mNewCalendar.get(Calendar.DAY_OF_MONTH);
			
			Calendar mLatestCalendar = Calendar.getInstance();
			mLatestCalendar.setTimeInMillis(mLatestUserData.getCalendarTime());
			int lastDay = mLatestCalendar.get(Calendar.DAY_OF_MONTH);
			//TODO
//			if(newDay == lastDay) {
//				
//				this.stopSelf();
//				
//			} else {
			
				Log.d("TAG", "SERVICE NEW DAY");

				UserData mNewUserData = new UserData(mNewCalendar.getTimeInMillis(), 
						newTarget, 0);
				
				mUserDataHelper.addData(mNewUserData);
				
//			}
			
		} else {
						
			Calendar mNewCalendar = Calendar.getInstance();
			
	        final Preferences mPreferences = new Preferences(this,
	        		Preferences.CATEGORY_USERINFO);
	        
			int dailySmoked = mPreferences.getInteger(Preferences.INTEGER_DAILYSMOKED);

			UserData mNewUserData = new UserData(mNewCalendar.getTimeInMillis(), 
					dailySmoked, 0);
			
			mUserDataHelper.addData(mNewUserData);
			
		}
		
		mUserDataHelper.close();
		
		//TODO
		Log.d("SERVICENEWDAY", "FIRE AWAY");
		
        this.stopSelf();
		
		return super.onStartCommand(intent, flags, startId);
		
	}


	private int calculateNewTarget(int oldTarget, int userSmoked) {
		
		if(oldTarget < userSmoked) {
			
			return oldTarget;
			
		} else {
			
			int difference = (oldTarget - userSmoked);
			
			double splitDecimal = (difference / 2);

			int roundedSplit = (int) Math.floor(splitDecimal);
			
			return (oldTarget - roundedSplit);
			
		}
		
	}

}
