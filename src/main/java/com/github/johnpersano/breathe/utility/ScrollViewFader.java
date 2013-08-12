package com.github.johnpersano.breathe.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollViewFader extends ScrollView {
	
	
	private OnScrollViewPositionChanged mOnScrollViewPositionChanged;
	
	
	public interface OnScrollViewPositionChanged {
		
		public void scrollPositionChanged(int position);
		
	}
	

	public ScrollViewFader(Context context) {
		super(context);
		
	}


    public ScrollViewFader(Context context, AttributeSet attrs) {
        super(context, attrs);
        
    }

    public ScrollViewFader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
    }
    
	
	
    @Override
    protected void onScrollChanged(int horizontalPosition, int verticalPosition, int oldHorizontal, int oldVertical) {
        super.onScrollChanged(horizontalPosition, verticalPosition, oldHorizontal, oldVertical);

        if(mOnScrollViewPositionChanged != null) {
        	
        	mOnScrollViewPositionChanged.scrollPositionChanged(verticalPosition);
        	
        }
        
    } 
    
    
    public void setOnScrollViewPositionChangedListener(OnScrollViewPositionChanged mOnScrollViewPositionChanged) {
    	
    	this.mOnScrollViewPositionChanged = mOnScrollViewPositionChanged;
    	
    }
	
	
}
