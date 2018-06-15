package com.ortaib.memorygamehw2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class HomePageActivity extends AppCompatActivity {
    private Bundle extras;
    private String name;
    private int year,month,day,age;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        year = mPreferences.getInt(getString(R.string.year),0);
        month = mPreferences.getInt(getString(R.string.month),0);
        day = mPreferences.getInt(getString(R.string.day),0);
        name = mPreferences.getString(getString(R.string.name),"undefined");
        Intent intent = getIntent();
        TextView view = (TextView)findViewById(R.id.name_age);
        extras = intent.getExtras();

        age = getAge(year,month,day);
        if(isBirthday(month,day)){
            TextView birthday = (TextView)findViewById(R.id.happy_birthday);
            birthday.setVisibility(View.VISIBLE);

        }
        view.setText(name +","+age);
    }
    public void startMediumGame(View view) {
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra(getString(R.string.time),""+45);
        intent.putExtra(getString(R.string.rows),""+4);
        intent.putExtra(getString(R.string.cols),""+4);


        startActivity(intent);
    }
    public void startEasyGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(getString(R.string.time), "" + 30);
        intent.putExtra(getString(R.string.rows), "" + 4);
        intent.putExtra(getString(R.string.cols), "" + 3);


        startActivity(intent);
    }
    public void startHardGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(getString(R.string.time), "" + 60);
        intent.putExtra(getString(R.string.rows), "" + 4);
        intent.putExtra(getString(R.string.cols), "" + 5);

        startActivity(intent);
    }
    //
    public int getAge(int year,int month,int day){
        Calendar cal = Calendar.getInstance();
        int age = cal.get(Calendar.YEAR)-year-1;
        if( (cal.get(Calendar.MONTH) - month) >0) {
           age++;
        }
        else if(cal.get(Calendar.MONTH)==month && cal.get(Calendar.DAY_OF_MONTH)>=day)
            age++;
        return age;
    }
    public boolean isBirthday(int month,int day){
        Calendar cal = Calendar.getInstance();
        if(cal.get(Calendar.MONTH)==month){
            if(cal.get(Calendar.DAY_OF_MONTH)==day)
                return true;
        }
        return false;
    }
    public void moveToScoreboard(View view){
        Intent intent = new Intent(this,ScoreAndMapFragmentsActivity.class);
        startActivity(intent);
    }

}
