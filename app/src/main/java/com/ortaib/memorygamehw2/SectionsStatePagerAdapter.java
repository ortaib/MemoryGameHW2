package com.ortaib.memorygamehw2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ortaib on 14/06/2018.
 */

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter{
    private final List<Fragment> myFragmentsList = new ArrayList<>();
    private final List<String> myFragmentsTitleList = new ArrayList<>();


    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment,String title){
        myFragmentsList.add(fragment);
        myFragmentsTitleList.add(title);

    }

    @Override
    public Fragment getItem(int position) {
        return myFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return myFragmentsList.size();
    }
}
