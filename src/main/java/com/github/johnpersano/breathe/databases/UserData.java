package com.github.johnpersano.breathe.databases;
 
public class UserData {
     
    private int id;
    private long calendarTime;
    private int dayTarget;
    private int daySmoked;


    public UserData(){
    	
         // Do nothing
    	
    }
    
    
    public UserData(int id, long calendarTime, int dayTarget, int daySmoked) {
    	
        this.id = id;
        this.calendarTime = calendarTime;
        this.dayTarget = dayTarget;
        this.daySmoked = daySmoked;

    }
     
    
    public UserData(long calendarTime, int dayTarget, int daySmoked) {
    	
        this.calendarTime = calendarTime;
        this.dayTarget = dayTarget;
        this.daySmoked = daySmoked;
        
    }
    
    
    public int getID() {
    	
        return this.id;
        
    }
     
    
    public void setID(int id) {
    	
        this.id = id;
        
    }
     
    
    public long getCalendarTime() {
    	
        return this.calendarTime;
        
    }
     
    
    public void setCalendarTime(long calendarTime) {
    	
        this.calendarTime = calendarTime;
        
    }
     
    
    public int getDayTarget() {
    	
        return this.dayTarget;
        
    }
     
    
    public void setDayTarget(int dayTarget) {
    	
        this.dayTarget = dayTarget;
        
    }
    
    
    public int getDaySmoked() {
    	
        return this.daySmoked;
        
    }
     
    
    public void setDaySmoked(int daySmoked) {
    	
        this.daySmoked = daySmoked;
        
    }
    
}



