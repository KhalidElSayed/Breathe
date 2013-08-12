package com.github.johnpersano.breathe;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.github.johnpersano.breathe.fragments.FragmentGraphs;
import com.github.johnpersano.breathe.fragments.FragmentHome;
import com.github.johnpersano.breathe.services.ServiceNewDay;
import com.github.johnpersano.breathe.utility.Preferences;

public class ActivityMain extends FragmentActivity {
	
	private static int REQUESTCODE_PREFERENCES = 1;
	
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavigationSelections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        
        final Preferences mPreferences = new Preferences(this,
        		Preferences.CATEGORY_USERINFO);
        
        final boolean mOpenedBefore = mPreferences
        		.getBoolean(Preferences.BOOLEAN_OPENEDBEFORE);
        
        if(!mOpenedBefore) {
        	
        	Intent mWelcomeIntent = new Intent(this, 
        			ActivityUserInfo.class);
        	startActivity(mWelcomeIntent);
        	this.finish();
        	
        }
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        mTitle = mDrawerTitle = getTitle();
        
        mNavigationSelections = getResources()
        		.getStringArray(R.array.navigation_selections);
        
        mDrawerLayout = (DrawerLayout) 
        		findViewById(R.id.drawer_layout);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 
        		GravityCompat.START);
        
        mDrawerList = (ListView) 
        		findViewById(R.id.left_drawer);
        
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavigationSelections));
        
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,  
                R.string.drawer_open, R.string.drawer_close) {
        	
            public void onDrawerClosed(View view) {
            	
                getActionBar().setTitle(mTitle);
                
            }

            public void onDrawerOpened(View drawerView) {
            	
                getActionBar().setTitle(mDrawerTitle);
                
            }
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        
        if (savedInstanceState == null) {
        	
        	FragmentHome mFragment = new FragmentHome();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace
            	(R.id.content_frame, mFragment).commit();
            
            mDrawerList.setItemChecked(0, true);
            setTitle(mNavigationSelections[0]);
            mDrawerLayout.closeDrawer(mDrawerList);
            
        } 

    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        
        return super.onCreateOptionsMenu(menu);
        
    }


	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
        	
            return true;
            
        }
        
        switch(item.getItemId()) {
        
        	case R.id.settings:
        		
        		Intent mPreferencesIntent = new Intent(this, 
        				ActivityPreferences.class);
        		startActivityForResult(mPreferencesIntent, REQUESTCODE_PREFERENCES);
        		
            return true;
            
        	case R.id.debug:
        		
				Intent mServiceIntent = new Intent(this, ServiceNewDay.class);
				startService(mServiceIntent);
        		
            return true;
        
        default:
            return super.onOptionsItemSelected(item);
            
        }
        
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	
        	Fragment mFragment;
        	
        	switch(position) {
        	
        		case 0:
        			
        			mFragment = new FragmentHome();
        			
        			break;
        			
        		case 3:
        			
        			mFragment = new FragmentGraphs();
        			
        			break;
        			
        			
        		default:
        			
        			mFragment = new FragmentHome();
        			
        			break;
        	
        	
        	}
        	
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace
            	(R.id.content_frame, mFragment).commit();
            
            mDrawerList.setItemChecked(position, true);
            setTitle(mNavigationSelections[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            
        }
        
    }
    

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        
        mDrawerToggle.syncState();
        
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
        mDrawerToggle.onConfigurationChanged(newConfig);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(requestCode == REQUESTCODE_PREFERENCES) {
    		
            if (resultCode == RESULT_CANCELED) {    
            	
            	FragmentHome mFragment = new FragmentHome();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace
                	(R.id.content_frame, mFragment).commit();
                
                mDrawerList.setItemChecked(0, true);
                setTitle(mNavigationSelections[0]);
                mDrawerLayout.closeDrawer(mDrawerList);
                
            }
    		
    	}

    }



}
