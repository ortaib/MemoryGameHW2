package com.ortaib.memorygamehw2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ScoreBoardFragment extends Fragment {
    private Button btnMap,btnList,btnBack;
    private final int LIST_FRAGMENT = 0;
    private final int MAP_FRAGMENT = 1;
    private DatabaseHelper myDatabasehelper;
    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_score_board, container, false);
        listView = (ListView)view.findViewById(R.id.listview);
        myDatabasehelper = new DatabaseHelper((ScoreAndMapFragmentsActivity)getContext());
        btnMap = (Button) view.findViewById(R.id.mapfragment);
        btnList = (Button)view.findViewById(R.id.listfragment);
        btnBack = (Button)view.findViewById(R.id.back);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScoreAndMapFragmentsActivity)getActivity()).setViewPager(LIST_FRAGMENT);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScoreAndMapFragmentsActivity)getActivity()).setViewPager(MAP_FRAGMENT);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((ScoreAndMapFragmentsActivity) getActivity(), HomePageActivity.class);
                intent.putExtra("year",0);
                startActivity(intent);
            }});
        populateListView();
        return view;
    }
    public void populateListView(){
        Cursor data = myDatabasehelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        int num=1;
        while(data.moveToNext()){
            listData.add("#"+num+" : " + data.getString(1)+" , Score: "+data.getInt(2));
            num++;
        }
        ListAdapter adapter = new ArrayAdapter<>((ScoreAndMapFragmentsActivity)getActivity(),
                android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);
    }
    private void toastMessage(String message){
        Toast.makeText(((ScoreAndMapFragmentsActivity)getActivity()),message,Toast.LENGTH_SHORT).show();
    }

}