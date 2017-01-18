package com.example.asheransari.sunshineupdated;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class forecast extends AppCompatActivity {

    private TextView mWeatherTextView;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

    }
}
