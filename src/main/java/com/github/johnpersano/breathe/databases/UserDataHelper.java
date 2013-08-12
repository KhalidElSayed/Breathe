package com.github.johnpersano.breathe.databases;
 
import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class UserDataHelper extends SQLiteOpenHelper {
 
	
    private static final int DATABASE_VERSION = 1;
    
    private static final String DATABASE_NAME = "userData";
    private static final String TABLE_DAILY = "tableDaily";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CALENDAR = "calendar";
    private static final String KEY_DAYTARGET = "day_target";
    private static final String KEY_DAYSMOKED = "day_smoked";

    public UserDataHelper(Context context) {
    	
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DAILY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CALENDAR + " INT,"
                + KEY_DAYTARGET + " INT," + KEY_DAYSMOKED + " INT" + ")";
        
        db.execSQL(CREATE_CONTACTS_TABLE);
        
    }
 
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY);
 
        onCreate(db);
        
    }
 

    public void addData(UserData mUserData) {
    	
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
 
        ContentValues mContentValues = new ContentValues();
        
        mContentValues.put(KEY_CALENDAR, mUserData.getCalendarTime()); 
        mContentValues.put(KEY_DAYTARGET, mUserData.getDayTarget()); 
        mContentValues.put(KEY_DAYSMOKED, mUserData.getDaySmoked()); 

        sQLiteDatabase.insert(TABLE_DAILY, null, mContentValues);
        
        sQLiteDatabase.close(); 
        
    }
    
 
    public UserData getUserData(int id) {
    	
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
 
        Cursor cursor = sQLiteDatabase.query(TABLE_DAILY, new String[] { KEY_ID,
        		KEY_CALENDAR, KEY_DAYTARGET, KEY_DAYSMOKED}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null) {
        	
        	cursor.moveToFirst();

        }
 
        UserData mUserData = new UserData(Integer.parseInt(cursor.getString(0)),
        		cursor.getLong(1), cursor.getInt(2), cursor.getInt(3));
        
        sQLiteDatabase.close();
        
        cursor.close();
        
        return mUserData;
        
    }
    
     
    public List<UserData> getAllUserData() {
    	
        List<UserData> mUserDataList = new ArrayList<UserData>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_DAILY;
 
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        
        Cursor cursor = sQLiteDatabase
        		.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
        	
            do {
            	
            	UserData mUserData = new UserData();
            	mUserData.setID(Integer.parseInt(cursor.getString(0)));
            	mUserData.setCalendarTime(cursor.getLong(1));
            	mUserData.setDayTarget(cursor.getInt(2));
            	mUserData.setDaySmoked(cursor.getInt(3));

            	mUserDataList.add(mUserData);
                
            } 
            
            while (cursor.moveToNext());
            
        }
        
        cursor.close();
        
        sQLiteDatabase.close();
 
        return mUserDataList;
        
    }
 
    
    public int updateContact(UserData mUserData) {
    	
        SQLiteDatabase sQLiteDatabase = this
        		.getWritableDatabase();
 
        ContentValues mContentValues = new ContentValues();
        
        mContentValues.put(KEY_CALENDAR, mUserData.getCalendarTime()); 
        mContentValues.put(KEY_DAYTARGET, mUserData.getDayTarget()); 
        mContentValues.put(KEY_DAYSMOKED, mUserData.getDaySmoked());
 
        return sQLiteDatabase.update(TABLE_DAILY, mContentValues, KEY_ID + " = ?",
                new String[] { String.valueOf(mUserData.getID()) });
                
    }
 
    
    public void deleteContact(UserData mUserData) {
    	
        SQLiteDatabase mSQLiteDatabase = this.getWritableDatabase();
        
        mSQLiteDatabase.delete(TABLE_DAILY, KEY_ID + " = ?",
                new String[] { String.valueOf(mUserData.getID()) });
        
        mSQLiteDatabase.close();
        
    }
    
    
    public void updateById(int id, long calendarTime, int dayTarget, int daySmoked) {
    	
          SQLiteDatabase sQLiteDatabase = this
        		.getWritableDatabase();
    	
    	  ContentValues mContentValues = new ContentValues();
    	  
          mContentValues.put(KEY_CALENDAR, calendarTime); 
          mContentValues.put(KEY_DAYTARGET, dayTarget); 
          mContentValues.put(KEY_DAYSMOKED, daySmoked);
          
          sQLiteDatabase.update(TABLE_DAILY, mContentValues, KEY_ID + "=" + id, null);
    	  
          sQLiteDatabase.close();
     }
    	 
 
    public int getDataCount() {
    	
        SQLiteDatabase sQLiteDatabase = this
        		.getReadableDatabase();

        String countQuery = "SELECT  * FROM " + TABLE_DAILY;
        
        Cursor cursor = sQLiteDatabase.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
        
        cursor.close();
        
        sQLiteDatabase.close();
         
        return count;
        
    }
 
}







