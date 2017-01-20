package com.example.asheransari.sunshineupdated;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asheransari.sunshineupdated.data.SunshinePreferences;
import com.example.asheransari.sunshineupdated.utilities.NetworkUtilities;
import com.example.asheransari.sunshineupdated.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class forecast extends AppCompatActivity {

    private TextView mWeatherTextView;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        mWeatherTextView = (TextView)findViewById(R.id.tv_weather_data);

        mErrorMessageTextView = (TextView)findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar)findViewById(R.id.pd_loading_indicator);

        loadWeatherData();
    }

    private void loadWeatherData()
    {
        showWeatherDataView();
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    private void showWeatherDataView()
    {
        mWeatherTextView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage()
    {
        mWeatherTextView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }
    public class FetchWeatherTask extends AsyncTask<String,Void,String[]>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {

            if (strings.length == 0)
            {
                return null;
            }

            String location = strings[0];
            URL weatherRequestUrl = NetworkUtilities.buildUrl(location);
            try
            {
                String jsonWeatherResponse = NetworkUtilities.getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWEatherStringFromJson(getApplicationContext(),jsonWeatherResponse);
                return simpleJsonWeatherData;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
//            return null;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (weatherData !=null)
            {
                showWeatherDataView();
                for (String weatherString : weatherData)
                {
                    mWeatherTextView.append((weatherString)+ "\n\n\n");
                }
            }
            else
            {
                showErrorMessage();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_refresh)
        {
            mWeatherTextView.setText("");
            loadWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
