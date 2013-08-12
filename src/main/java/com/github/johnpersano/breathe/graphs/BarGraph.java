/*
 *         Created by Daniel Nadeau
 *         daniel.nadeau01@gmail.com
 *         danielnadeau.blogspot.com
 * 
 *         Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.github.johnpersano.breathe.graphs;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.github.johnpersano.breathe.R;

import java.util.ArrayList;

@SuppressWarnings("javadoc")
public class BarGraph extends View {
	
	private static final int LINE_STROKE = 1;
	private static final int LINE_ALPHA = 125;
	private static final int PADDING_BOTTOMLINE = 8;
	private static final int PADDING_TOPVIEW_BAR = 12;
	private static final int PADDING_TOPVIEW_TEXT = 4;
	
	private boolean mShowTopLabel = true;
	private float mTopLabelTextSize = 0.0f;
	private float mBottomLabelTextSize = 0.0f;
	
    private Paint mTopTextPaint;
    private Paint mBottomTextPaint;
    private Paint mBarPaint;
    private Paint mLinePaint;
    private Paint mTopBarPaint;
    
    private RectF mBarRect = new RectF();
    private Rect mTopLabelRect = new Rect();

    private int mMaximumValue = 10;
    private float mBarPadding = 10;
    
	private ArrayList<Bar> mArrayListBar = new ArrayList<Bar>();    
	
	
	public BarGraph(Context context) {
		super(context);
		
        initialize();

	}

	public BarGraph(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet,
                R.styleable.BarGraph, 0, 0);

        try {
        	
        	mShowTopLabel = typedArray.getBoolean(R.styleable.BarGraph_showTopLabel, true);
        	mTopLabelTextSize = typedArray.getDimension(R.styleable.BarGraph_topLabelTextSize, 40.0f);

        } finally {
        	
        	typedArray.recycle();
        }

        initialize();
        
	}
	
	
	public void addBar(Bar bar) {
		
		mArrayListBar.add(bar);
		
	}
	
	
	
	
	
	private void initialize() {

        // Set up the paint for the label text
		mTopTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTopTextPaint.setColor(Color.WHITE);
		
        if (mTopLabelTextSize == 0) {
        	
        	mTopLabelTextSize = mTopTextPaint.getTextSize();
            
        } else {
        	
        	mTopTextPaint.setTextSize(mTopLabelTextSize);
            
        }        
        
        mBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomTextPaint.setColor(Color.BLACK);
		
        if (mBottomLabelTextSize == 0) {
        	
        	mBottomLabelTextSize = mBottomTextPaint.getTextSize();
            
        } else {
        	
        	mBottomTextPaint.setTextSize(mBottomLabelTextSize);
        	
        }
        
        mBarPaint = new Paint();
        
        mTopBarPaint = new Paint();
        mTopBarPaint.setColor(Color.LTGRAY);    
        
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setAlpha(LINE_ALPHA);
        mLinePaint.setStrokeWidth(dipToPixels(LINE_STROKE));

    }

	
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

		float usableHeight;
				
		if(mShowTopLabel) {
			
			mTopTextPaint.getTextBounds("$", 0, 1, mTopLabelRect);
			
			usableHeight = getHeight() - (getPaddingTop() + getPaddingBottom())
					- Math.abs(mTopLabelRect.top - mTopLabelRect.bottom);
			
		} else {
			
			usableHeight = getHeight() - (getPaddingTop() + getPaddingBottom());
			
		}
		
        for (int i = 0; i < mArrayListBar.size(); i++) {   
        	
    		final Bar bar = mArrayListBar.get(i);
    		
            mBarPaint.setColor(bar.getColor());  
    		
			float barWidth = (getWidth() / mArrayListBar.size());
			
			float barLeft = getPaddingLeft() + (barWidth * i) + mBarPadding;
			float barTop = usableHeight - (usableHeight * (bar.getValue() / mMaximumValue));
			float barRight = getPaddingRight() + barWidth + (barWidth * i) - mBarPadding;
			float barBottom = usableHeight - getPaddingBottom() - dipToPixels(PADDING_BOTTOMLINE);

			mBarRect.set(barLeft, barTop, barRight, barBottom);

			canvas.drawRect(mBarRect, mBarPaint);
			
			if(mShowTopLabel) {
				
				String label = bar.getTopBarLabel();
				
				mTopTextPaint.getTextBounds(label, 0, 1, mTopLabelRect);
				
				int drawableLeft = (int) (((barRight + barLeft) / 2) - (mTopTextPaint.measureText(label) / 2) 
						- dipToPixels(PADDING_TOPVIEW_TEXT));
				int drawableTop = (int) (barTop - (Math.abs(mTopLabelRect.height())) - dipToPixels(PADDING_TOPVIEW_BAR) 
						- (dipToPixels(PADDING_TOPVIEW_TEXT) * 2));
				int drawableRight = (int) (((barRight + barLeft) / 2) + (mTopTextPaint.measureText(label) / 2) 
						+ dipToPixels(PADDING_TOPVIEW_TEXT));
				int drawableBottom = (int) (barTop - dipToPixels(PADDING_TOPVIEW_BAR) 
						+ dipToPixels(PADDING_TOPVIEW_TEXT));
				
				mTopLabelRect.set(drawableLeft, drawableTop, drawableRight, drawableBottom);

	        	canvas.drawRect(mTopLabelRect, mTopBarPaint);
	        	
				canvas.drawText(label, (int) (((barLeft + barRight) / 2) - (mTopTextPaint.measureText(label) / 2)),
						drawableBottom - dipToPixels(PADDING_TOPVIEW_TEXT) - dipToPixels(PADDING_TOPVIEW_TEXT), mTopTextPaint);

			}
			
        }
                
		canvas.drawLine(getPaddingLeft(), usableHeight - getPaddingBottom(), getWidth(),
				usableHeight - getPaddingBottom(), mLinePaint);
    }
    
    public void showTopLabel(boolean showTopLabel) {
    	
    	this.mShowTopLabel = showTopLabel;
    	
    }
    
    public void setTopLabelTextSize(float topLabelTextSize) {
    	
    	this.mTopLabelTextSize = topLabelTextSize;
    	
    }
    
    
    
    
    private float dipToPixels(int dip) {
    	
    	Resources resources = getResources();
    	
    	return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
    			dip, resources.getDisplayMetrics());
    	
    }

}