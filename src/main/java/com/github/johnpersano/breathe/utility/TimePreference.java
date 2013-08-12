package com.github.johnpersano.breathe.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePreference extends DialogPreference {
	
    private TimePicker mTimePicker;
    
    private int mHour;
    private int mMinute;
    
    private Context mContext;
    
    
    public TimePreference(Context mContext) {
    	this(mContext, null);
        
    	this.mContext = mContext;
        
    }

    
    public TimePreference(Context mContext, AttributeSet mAttributeSet) {
    	this(mContext, mAttributeSet, android.R.attr.preferenceStyle);
        
    	this.mContext = mContext;
        
    }
    

    public TimePreference(Context mContext, AttributeSet attrs, int defStyle) {
        super(mContext, attrs, defStyle);

        setPositiveButtonText(mContext.getResources().getString(R.string.set));
        setNegativeButtonText(mContext.getResources().getString(R.string.cancel));
        
    	this.mContext = mContext;

                
    }

    
    @Override
    protected View onCreateDialogView() {
    	
    	mTimePicker = new TimePicker(getContext());
        
        return (mTimePicker);
        
    }

    
    @Override
    protected void onBindDialogView(View mView) {
        super.onBindDialogView(mView);
        
        mTimePicker.setCurrentHour(mHour);
        mTimePicker.setCurrentMinute(mMinute);
        
    }
    

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if(positiveResult) {
        	
            mHour = mTimePicker.getCurrentHour();
            mMinute = mTimePicker.getCurrentMinute();

            String time = String.valueOf(mHour) + 
            		":" + String.valueOf(mMinute);

            setSummary(getSummary());
            
            if(callChangeListener(time)) {
            	
                persistString(time);
                notifyChanged();
                
				Alarms mAlarms = new Alarms(mContext);
				mAlarms.setNewDayAlarm();

				showSuperButtonToast();
				
            }
            
        }
        
    }

    
    @Override
    protected Object onGetDefaultValue(TypedArray mTypedArray, int index) {
    	
        return (mTypedArray.getString(index));
        
    }
    

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
    	
    	String time = "";
    	
        if (restoreValue) {
        	
            if (defaultValue != null) {
            	
                time = getPersistedString(defaultValue.toString());
                
            } else {
            	
                time = getPersistedString("00:00");

            }
            
        } else {
        	
            if (defaultValue != null) {
            	
                time = getPersistedString(defaultValue.toString());
                
            } else {
            	
                time = getPersistedString("00:00");

            }
            
        }
        
        String[] timeArray = time.split(":");
        
        mHour = Integer.parseInt(timeArray[0]);
        
        mMinute = Integer.parseInt(timeArray[1]);      
        
    }
    
    
    @Override
    public CharSequence getSummary() {
    	
    	Calendar mCalendar = Calendar.getInstance();
    	
    	mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
    	mCalendar.set(Calendar.MINUTE, mMinute);
    	
    	Date mDate = new Date(mCalendar.getTimeInMillis());

    	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm aa");
    	
    	return mSimpleDateFormat.format(mDate);
    	
    }
    
    
    private void showSuperButtonToast() {
    	
    	SuperActivityToast mSuperActivityToast = new SuperActivityToast(mContext, SuperToast.Type.BUTTON);
    	mSuperActivityToast.setText(mContext.getResources().getString(R.string.new_day_changed));
		mSuperActivityToast.setButtonText(mContext.getResources().getString(R.string.info_allcaps));
		mSuperActivityToast.setButtonResource(SuperToast.BUTTON_DARK_INFO);
		mSuperActivityToast.setDuration(SuperToast.DURATION_XLONG);
		mSuperActivityToast.setButtonOnClickListener(mOnClickListener);
		mSuperActivityToast.show();
    	
    }
	
	
	private OnClickListener mOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View view) {		
			
			SuperActivityToast.cancelAllSuperActivityToasts();
			 
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(mContext.getResources().getString(R.string.new_day));
            builder.setMessage(mContext.getResources().getString(R.string.new_day_dialog));
            builder.setNeutralButton(mContext.getResources().getString(R.string.cancel), null);
            builder.show();
            
		}

	};

    
} 




