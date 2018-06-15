package com.ortaib.memorygamehw2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ScoreAndMapFragmentsActivity extends AppCompatActivity {
    private SectionsStatePagerAdapter mySectionsStatePagerAdapter;
    private final int LIST_FRAGMENT = 0;
    private final int MAP_FRAGMENT = 1;
    private Button btnScoreBoard,btnMap,btnBack;
    private ViewPager myViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_and_map_fragments);
        mySectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        myViewPager = (ViewPager)findViewById(R.id.container);
        setUpViewPage(myViewPager);
        btnBack = (Button)findViewById(R.id.back);
        btnMap = (Button)findViewById(R.id.mapfragment);
        btnScoreBoard = (Button)findViewById(R.id.listfragment);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewPager(MAP_FRAGMENT);
            }
        });
        btnScoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewPager(LIST_FRAGMENT);
            }
        });
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

