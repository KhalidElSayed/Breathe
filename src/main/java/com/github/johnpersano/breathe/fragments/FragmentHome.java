package com.github.johnpersano.breathe.fragments;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.johnpersano.breathe.R;
import com.github.johnpersano.breathe.databases.UserData;
import com.github.johnpersano.breathe.databases.UserDataHelper;
import com.github.johnpersano.breathe.utility.Preferences;
import com.github.johnpersano.breathe.utility.ScrollViewFader;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FragmentHome extends Fragment {
	
	
	private static final String CDC_RSSFEED = "http://feeds.feedburner.com/cdc/GEla";
	private static final String JOHNS_RSSFEED = "http://www.androidianlabs.com/4/feed";

	
	
	Button mRSSButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        getActivity().setTitle("Home");

	    View view = inflater.inflate(R.layout.fragment_home,
	    		container, false);
	    
	    Typeface lightTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-Light.ttf");	   
	    
	    final TextView titleTextView = (TextView)
	    		view.findViewById(R.id.titleTextView);
	    titleTextView.setTypeface(lightTypeface);
	    
	    Preferences preferences = new Preferences(getActivity());
	    
	    final String mUserName= preferences
	    		.getString(Preferences.STRING_USERNAME);
	    
	    if(!mUserName.equals("")) {
	    	
	    	titleTextView.setText(String.format(getResources().getString(R.string.welcome_user), mUserName));
	    	
	    }
	    
	    	    
	    if(view.findViewById(R.id.root_layout) != null) {
	    	
	    	setSingleRowLayout(view, inflater);
	    	
	    } else {
	    	
	    	setDualRowLayout(view, inflater);

	    }

	    ScrollViewFader mScrollViewFader = (ScrollViewFader)
	    		view.findViewById(R.id.fader_scrollview);
	    
	    mScrollViewFader.setOnScrollViewPositionChangedListener(new ScrollViewFader.OnScrollViewPositionChanged() {

			@Override
			public void scrollPositionChanged(int position) {
				
				titleTextView.setAlpha(Math.max(0f, Math.min(1f,
                        1f - 2f * Math.abs(position) / titleTextView.getHeight())));					
			}

	    });

		return view;
	
	}

	
	private boolean isConnected() {

	    ConnectivityManager mConnectivityManager = (ConnectivityManager) 
	    		getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo mNetworkInfoWIFI = mConnectivityManager
	    		.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    
	    NetworkInfo mNetworkInfoMobile = mConnectivityManager
	    		.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    
	    if(mNetworkInfoWIFI != null) {
	    	
	    	 if (mNetworkInfoWIFI.isConnected()) {
	 	    	
	 	        return true;
	 	        
	 	    }
	    	
	    } 

	    if (mNetworkInfoMobile != null) {
	    	
		    if (mNetworkInfoMobile.isConnected()) {
		    	
		        return true;
		        
		    }
		    
	    }
	    
	    return false;

	}
	
	private void setSingleRowLayout(View mRootView, LayoutInflater mLayoutInflater) {
	    
	    LayoutAnimationController mLayoutAnimationController = AnimationUtils
				   .loadLayoutAnimation(getActivity(), R.anim.layout_animation);
 	
	    LinearLayout mRoot = (LinearLayout)
	    		mRootView.findViewById(R.id.root_layout);
	    
	    ArrayList<View> mArrayList = new ArrayList<View>();
	    
	    Preferences mPreferences = new Preferences(getActivity());
	    
	    if(mPreferences.getBoolean(Preferences.BOOLEAN_TARGETENABLED, true)) {
		    
		    mArrayList.add(getTargetView(mLayoutInflater));
	    	
	    }
	    
	    if(mPreferences.getBoolean(Preferences.BOOLEAN_QUOTEENABLED, true)) {
	    	
		    mArrayList.add(getQuoteView(mLayoutInflater));

	    }
	    
	    if(mPreferences.getBoolean(Preferences.BOOLEAN_CDCRSSENABLED, true)) {
		    
		    mArrayList.add(getCDCRSSView(mLayoutInflater));	    
	    	
	    }
	    
	    if(mPreferences.getBoolean(Preferences.BOOLEAN_DATEENABLED, true)) {

		    mArrayList.add(getDateView(mLayoutInflater));	    
	    	
	    }
	    
	    if(mPreferences.getBoolean(Preferences.BOOLEAN_JOHNSRSSNABLED, true)) {
	    	
	    	mArrayList.add(getJohnsRSSView(mLayoutInflater));
	    	
	    }
	    
        for (int i = 0; i < mArrayList.size(); i++) {        	
        	
    		final View view = mArrayList.get(i);
    		
    		mRoot.addView(view);
    		
    		mRoot.invalidate();
            
        }

	    mRoot.setLayoutAnimation(mLayoutAnimationController);		
		
	}

	
	private void setDualRowLayout(View mRootView, LayoutInflater mLayoutInflater) {

//	    Animation animation = AnimationUtils
//				   .loadAnimation(getActivity(), R.anim.card_animation);
		
	    LinearLayout rootLeft = (LinearLayout)
	    		mRootView.findViewById(R.id.root_layout_left);
	    
	    LinearLayout rootRight = (LinearLayout)
	    		mRootView.findViewById(R.id.root_layout_right);
	    
	    ArrayList<View> arrayList = new ArrayList<View>();
	    
	    Preferences preferences = new Preferences(getActivity());
	    
	    if(preferences.getBoolean(Preferences.BOOLEAN_TARGETENABLED, true)) {
		    
		    arrayList.add(getTargetView(mLayoutInflater));
	    	
	    }
	    
	    if(preferences.getBoolean(Preferences.BOOLEAN_QUOTEENABLED, true)) {
	    	
		    arrayList.add(getQuoteView(mLayoutInflater));

	    }
	    
	    if(preferences.getBoolean(Preferences.BOOLEAN_CDCRSSENABLED, true)) {
		    
		    arrayList.add(getCDCRSSView(mLayoutInflater));	    
	    	
	    }
	    
	    if(preferences.getBoolean(Preferences.BOOLEAN_DATEENABLED, true)) {

		    arrayList.add(getDateView(mLayoutInflater));	    
	    	
	    }
	    
	    if(preferences.getBoolean(Preferences.BOOLEAN_JOHNSRSSNABLED, true)) {
	    	
	    	arrayList.add(getJohnsRSSView(mLayoutInflater));
	    	
	    }
	    
	    
        for (int i = 0; i < arrayList.size(); i++) {        	
        	
    		final View view = arrayList.get(i);
    		
    		Animation animation = AnimationUtils.loadAnimation(getActivity(),
    				R.anim.card_animation);
    		
    		animation.setStartOffset(i * 100);

        	if(i % 2 == 0) {
        		
        		rootLeft.addView(view);
        		rootLeft.invalidate();
        		
        	} else {
        		        		
        		rootRight.addView(view);
        		rootRight.invalidate();
        		
        	}
        	
    		view.startAnimation(animation); 
    		
        }
		
	}
	
	
	
	
	private View getTargetView(LayoutInflater mLayoutInflater) {
		
		
	    View mTargetView = mLayoutInflater.inflate(R.layout.view_todaystarget, 
	    		null);
	    
		final UserDataHelper mUserDataHelper = new UserDataHelper(getActivity());
		
		int newTarget;
		
		if(mUserDataHelper.getDataCount() > 0) {
			
			UserData mLatestUserData = mUserDataHelper.getUserData(mUserDataHelper
					.getDataCount());
			
			int daySmoked = mLatestUserData
					.getDaySmoked();
			
			int dayTarget = mLatestUserData.
					getDayTarget();
			
			newTarget = (dayTarget - daySmoked);


		} else {
			
	        final Preferences mPreferences = new Preferences(getActivity(), 
	        		Preferences.CATEGORY_USERINFO);
	        
	        newTarget = mPreferences.getInteger
					(Preferences.INTEGER_DAILYSMOKED);
			
		}

		
	    final TextView contentTextView = (TextView)
	    		mTargetView.findViewById(R.id.contentTextView);
	    	    
	    contentTextView.setText(String.valueOf(newTarget));	
	    
        mUserDataHelper.close();
	    
	    Button mButton = (Button)
	    		mTargetView.findViewById(R.id.logButton);
	    
        mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(mUserDataHelper.getDataCount() > 0) {
					
					final int latestEntry = mUserDataHelper
							.getDataCount();
					
					UserData mLatestUserData = mUserDataHelper
							.getUserData(latestEntry);
					
					int daySmoked = mLatestUserData
							.getDaySmoked();
					
					int dayTarget = mLatestUserData.
							getDayTarget();
					
					daySmoked++;
					
					int newTarget = (dayTarget - daySmoked);
					
					mUserDataHelper.updateById(latestEntry, mLatestUserData.
							getCalendarTime(), dayTarget, daySmoked);

					contentTextView.setText(String.valueOf(newTarget));
					
					
				} else {
					
					SuperActivityToast.createDarkSuperActivityToast(getActivity(),
                            "Could not add data.", SuperToast.DURATION_MEDIUM).show();
					
				}

			}						
        	
        });
        
        
	    return mTargetView;
	    
	}
	
	
	private View getQuoteView(LayoutInflater mLayoutInflater) {
		
		
	    View mQuoteView = mLayoutInflater.inflate(R.layout.view_quoteofday, 
	    		null);    	
	    
	    return mQuoteView;
	    
	}
	
	
	private View getCDCRSSView(LayoutInflater mLayoutInflater) {
		
		
	    View mRSSView = mLayoutInflater.inflate(R.layout.view_rss, 
	    		null);
	    	    
	    final Button mButton = (Button)
	    		mRSSView.findViewById(R.id.rssButton);
	    
		try {
			
			URL url = new URL(CDC_RSSFEED);
			
	        GetRSSFeed mGetRSSFeed = new GetRSSFeed(mButton);
	        mGetRSSFeed.execute(url);
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
		}		
		
	    final TextView mTextView = (TextView)
	    		mRSSView.findViewById(R.id.titleTextView);
	    
	    mTextView.setText(getActivity().getResources()
	    		.getString(R.string.cdc_rss));
	    
	    return mRSSView;
	    
	}
	
	
	private View getDateView(LayoutInflater mLayoutInflater) {
		
    	
	    View mDateView = mLayoutInflater.inflate(R.layout.view_date, 
	    		null);
	    
	    Typeface mTypeface = Typeface.createFromAsset(getActivity().getAssets(), 
	    		"Roboto-BoldCondensedItalic.ttf");	
	    
	    Button mButton = (Button) 
	    		mDateView.findViewById(R.id.dateButton);
    	
    	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
                "MMMM dd, yyyy");
        Calendar mCalendar = Calendar.getInstance();
        
        mButton.setText(mSimpleDateFormat
        		.format(mCalendar.getTime()));
        
        mButton.setTypeface(mTypeface);
        
        mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				try {
					
					Intent mIntent = new Intent();
					
					ComponentName mComponentName = new ComponentName
							("com.google.android.calendar", "com.android.calendar.LaunchActivity");
				
					mIntent.setComponent(mComponentName);
					
					startActivity(mIntent);						
					
				} catch (ActivityNotFoundException e) {
					
					SuperActivityToast.createDarkSuperActivityToast(getActivity(),
							"Google calendar not found!", SuperToast.DURATION_MEDIUM).show();
					
				}
									
			}	        	
        	
        });
	    
	    return mDateView;
	    
	}
	
	
	private View getJohnsRSSView(LayoutInflater mLayoutInflater) {
		
		
	    View mRSSView = mLayoutInflater.inflate(R.layout.view_rss, 
	    		null);
	    	    
	    final Button mButton = (Button)
	    		mRSSView.findViewById(R.id.rssButton);
	    
		try {
			
			URL url = new URL(JOHNS_RSSFEED);
			
	        GetRSSFeed mGetRSSFeed = new GetRSSFeed(mButton);
	        mGetRSSFeed.execute(url);
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
		}		
		
	    final TextView mTextView = (TextView)
	    		mRSSView.findViewById(R.id.titleTextView);
	    
	    mTextView.setText(getActivity().getResources()
	    		.getString(R.string.johns_rss));
	    
	    return mRSSView;
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private class GetRSSFeed extends AsyncTask<URL, Void, String> {
		
		ArrayList<String> headlines = new ArrayList<String>();
		ArrayList<String> links = new ArrayList<String>();
		Button mButton;

		public GetRSSFeed(Button mButton) {
			
			this.mButton = mButton;
			
		}

		@Override
	      protected String doInBackground(URL... url) {
	    	  
	    	  try {
	    		  	  		 
	  		    XmlPullParserFactory mXmlPullParserFactory = XmlPullParserFactory
	  		    		.newInstance();
	  		    	  		    
	  		    XmlPullParser mXmlPullParser = mXmlPullParserFactory
	  		    		.newPullParser();
	  		    
	  		    mXmlPullParser.setInput(url[0].openConnection().getInputStream(), 
	  		    		"UTF_8");

	  		    boolean insideItem = false;
	  		 
	  		    int eventType = mXmlPullParser.getEventType();
	  		    
	  		    while (eventType != XmlPullParser.END_DOCUMENT) {
	  		    	
	  		    	
	  		        if (eventType == XmlPullParser.START_TAG) {
	  		 
	  		            if (mXmlPullParser.getName().equalsIgnoreCase("item")) {
	  		            	
	  		                insideItem = true;
	  		                
	  		            } else if (mXmlPullParser.getName().equalsIgnoreCase("title")) {
	  		            	
	  		                if (insideItem) {
	  		                	
	  		                    headlines.add(mXmlPullParser.nextText()); //extract the headline

	  		                }
	  		                
	  		            } else if (mXmlPullParser.getName().equalsIgnoreCase("link")) {
	  		            	
	  		                if (insideItem) {
	  		                	
	  		                    links.add(mXmlPullParser.nextText()); //extract the link of article
	  		                    
	  		                }
	  		                
	  		            }
	  		            
	  		        } else if(eventType==XmlPullParser.END_TAG && mXmlPullParser.getName().equalsIgnoreCase("item")) {
	  		        	
	  		            insideItem = false;
	  		            
	  		        }
	  		 
	  		        eventType = mXmlPullParser.next(); 
	  		    }
	  		 
	  			} catch (MalformedURLException e) {
	  			
	  				e.printStackTrace();
	  		    
	  			} catch (XmlPullParserException e) {
	  			
	  				e.printStackTrace();
	  		    
	  			} catch (IOException e) {
	  			
	  				e.printStackTrace();
	  		    
	  			}

	        return null;
	            
	      }      

	      @Override
	      protected void onPostExecute(String result) {   
	    	  
	    	  if(!headlines.isEmpty()) {
	    		  
	    		  mButton.setEnabled(true);
	    		  	    			  
	    		  mButton.setText(headlines.get(0).toString());
	    			  	    		  
	    	  } else {
	    		  
	    		  mButton.setEnabled(false);
    			  
    			  if(isConnected()) {
    				  
    				  mButton.setText("No feed information");

	    	    	  
    			  } else {
    				  
    				  mButton.setText("No connection");

    			  }
    			  
    		  }
	    	  
	    	  mButton.setOnClickListener(new OnClickListener() {

	    		  @Override
				  public void onClick(View arg0) {

	    			  if(!links.isEmpty()) {
							
	    				  Uri uri = Uri.parse(links.get(0));
						  Intent mIntent = new Intent(Intent.ACTION_VIEW, uri);
					      startActivity(mIntent);
														
						} else {
							
							SuperActivityToast.createDarkSuperActivityToast(getActivity(), 
									"Connection error", SuperToast.DURATION_MEDIUM).show();
							
						}
						
					}

		      });

	      }

	}
	
	
	
	

	

}
