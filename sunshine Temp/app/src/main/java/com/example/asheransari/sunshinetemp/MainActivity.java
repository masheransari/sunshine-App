package com.example.asheransari.sunshinetemp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asheransari.sunshinetemp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoXEditText;
    private TextView mUrlDisplayTextVView;
    private TextView mSearchResultinTextView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoXEditText = (EditText)findViewById(R.id.et_search_box);
        mUrlDisplayTextVView = (TextView)findViewById(R.id.tv_url_display);
        mSearchResultinTextView = (TextView)findViewById(R.id.tv_github_search_result_json);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);


    }

    private void makeGithubSearchQUery()
    {
        String githubQUery = mSearchBoXEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQUery);
        mUrlDisplayTextVView.setText(githubSearchUrl.toString());
        new GithubQueryTask().execute(githubSearchUrl);
    }

    private void showJsonDataView()
    {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mSearchResultinTextView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage()
    {
        mSearchResultinTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int menuItem = item.getItemId();
        if (menuItem == R.id.action_search)
        {
//            Context context = MainActivity.this;
//            String Message = "Search Selected";
            makeGithubSearchQUery();
            return true;
//            Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private class GithubQueryTask extends AsyncTask<URL, Void, String>
    {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResult = null;
            try
            {
                githubSearchResult = NetworkUtils.getResponseFromJHttpUrl(searchUrl);
//                  Both are same thing...
//                githubSearchResult = NetworkUtils.getResponseFromJHttpUrl(urls[0]);
            }
            catch(IOException e)
            {
                Log.e(String.valueOf(MainActivity.this),e.getMessage());
            }
            return githubSearchResult;
        }

        @Override
        protected void onPostExecute(String s) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
//            super.onPostExecute(s);
            if (s !=null && !s.equals("")) {
                showJsonDataView();
                mSearchResultinTextView.setText(s);
            }
            else
            {
                showErrorMessage();
            }
        }
    }

}
