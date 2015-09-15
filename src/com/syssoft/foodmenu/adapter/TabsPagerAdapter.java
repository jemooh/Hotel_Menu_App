package com.syssoft.foodmenu.adapter;

import com.syssoft.foodmenu.src.ListHotDrinksFragment;
import com.syssoft.foodmenu.src.ListDrinksFragment;
import com.syssoft.foodmenu.src.ListMainCoursesFragment;
import com.syssoft.foodmenu.src.ListSoupFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
	 
	public class TabsPagerAdapter extends FragmentPagerAdapter {
	 
	    public TabsPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
	 
	    @Override
	    public Fragment getItem(int index) {
	 
	        switch (index) {
	       case 0:
	            // Top Rated fragment activity
	            return new ListHotDrinksFragment();
	        case 1:
	            // Games fragment activity
	            return new ListDrinksFragment();
	        case 2:
	            // Top Rated fragment activity
	            return new ListMainCoursesFragment();
	        case 3:
	            // Games fragment activity
	            return new ListSoupFragment();
	       }
	 
	        return null;
	    }
	 //check count
	    @Override
	    public int getCount() {
	        // get item count - equal to number of tabs
	        return 4;
	    }
	 
}

