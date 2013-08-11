package com.hoo.marscolony;

import java.util.Locale;

import com.hoo.marscolony.R.color;
import com.hoo.marscolony.manage.Match;
import com.hoo.marscolony.manage.MatchManager;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private final String TAG = "MainActivity"; 
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private MatchManager m;
	private ScoreAdapter sa;
	private MenuItem start_stop;
	private ActionBar actionBar;
	
	private int spinner_img[] = {R.drawable.spinner_field, R.drawable.spinner_timer, R.drawable.spinner_total};
	
	private boolean isStart = true;
	private ColorDrawable cd = new ColorDrawable(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		cd.setColor(0xff000000);	// black
		actionBar.setBackgroundDrawable(cd);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		m = MatchManager.getManager(menu.findItem(R.id.timer), menu.findItem(R.id.score));
    	Spinner scoreSpinner = (Spinner) menu.findItem(R.id.score).getActionView();
    	sa = new ScoreAdapter( this, R.layout.spinner, new String[] {"0","0","0"});
    	scoreSpinner.setAdapter( sa );
		return true;
	}
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	Match currMatch = m.getcurrMatch();
    	currMatch.updateMenuItem(menu.findItem(R.id.timer), menu.findItem(R.id.score));
    	
    	MenuItem pause = menu.findItem(R.id.pause);
    	start_stop = menu.findItem(R.id.start_stop);
    	pause.setVisible(false);
    	
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Match currMatch = m.getcurrMatch();
    	switch (item.getItemId()) {
    		case R.id.start_stop:
    			if (isStart){
    				Log.w(TAG, "action_start()");
    				currMatch.onStart();
    				cd.setColor(0xff669900);	//green
    				item.setIcon(R.drawable.action_stop);
    			}else{
    				Log.w(TAG, "action_stop()");
    				currMatch.cancel();
    				cd.setColor(0xff0099CC);	// blue
    				item.setIcon(R.drawable.action_start);
    			}
				actionBar.setBackgroundDrawable(cd);
    			isStart = !isStart;
    			break;
    		case R.id.reset:
    			Log.w(TAG, "action_reset()");
				currMatch.cancel();
				currMatch.reset();
				start_stop.setIcon(R.drawable.action_start);
				isStart = true;
				cd.setColor(0xff000000);	// black
				actionBar.setBackgroundDrawable(cd);
    			break;
    		case R.id.timer:
    			currMatch.toggleTimer();
    			break;
    	}
    	return true;
    }
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment;
			if (position == 0)
			{
				fragment = new FieldFragment();
			}else{
				fragment = new DummySectionFragment();
			}
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	
	/** 
	 * 	Score Spinner ArrayAdapter
	 */
	public class ScoreAdapter extends ArrayAdapter<String>{
		 
        public ScoreAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }
 
        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
 
        public View getCustomView(int position, View convertView, ViewGroup parent) {
 
        	Match currMatch;
        	currMatch = m.getcurrMatch();
        	
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner, parent, false);
            
            TextView label = (TextView)row.findViewById(R.id.item_score);
            label.setText(""+currMatch.getScore(position));

            ImageView icon = (ImageView)row.findViewById(R.id.item_icon);
            icon.setImageResource(spinner_img[position]);
 
            return row;
            }
        }
}
