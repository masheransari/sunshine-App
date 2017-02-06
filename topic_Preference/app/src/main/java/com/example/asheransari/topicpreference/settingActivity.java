package com.example.asheransari.topicpreference;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by asher.ansari on 2/6/2017.
 */

public class settingActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
