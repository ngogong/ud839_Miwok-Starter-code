package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by ismile on 5/24/2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String LOG_TAG = SimpleFragmentPagerAdapter.class.getName();

    private String tabTitles[] = new String[] { "Numbers", "Family", "Colors", "Phrases" };

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(LOG_TAG, " simplefragmentpageradpater getitem=" + position + " end");
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1){
            return new FamilyFragment();
        } else if (position == 2){
            return new ColorsFragment();
        } else if (position == 3){
            return new PhrasesFragment();
        }
        return new NumbersFragment();

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Log.e(LOG_TAG, " simplefragmentpageradpater getpagetitle=" + position + " end");
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        Log.e(LOG_TAG, " simplefragmentpageradpater getcount end");
        return 4;
    }
}
