package com.ortaib.memorygamehw2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ScoreAndMapFragmentsActivity extends AppCompatActivity {
    private SectionsStatePagerAdapter mySectionsStatePagerAdapter;
    private ViewPager myViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_and_map_fragments);
        mySectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        myViewPager = (ViewPager)findViewById(R.id.container);
        setUpViewPage(myViewPager);
    }

    public void setUpViewPage(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScoreBoardFragment(),"ScoreBoardFragment");
        adapter.addFragment(new MapFragment(),"MapFragment");
        viewPager.setAdapter(adapter);
    }
    public void setViewPager(int fragmentNumber){
        myViewPager.setCurrentItem(fragmentNumber);
    }
}

