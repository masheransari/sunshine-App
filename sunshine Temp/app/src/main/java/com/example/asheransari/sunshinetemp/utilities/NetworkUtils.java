package com.example.asheransari.sunshinetemp.utilities;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import java.net.HttpURLConnection;

/**
 * Created by asher.ansari on 12/26/2016.
 */

public class NetworkUtils {

    final static String GITHUB_BASE_URL = "http://api.github.com/search/repositories";

    final static String PARAM_QUERY = "q";

    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    public static URL buildUrl(String gitSearchQuery)
    {
//        return null;
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon().
                    appendQueryParameter(PARAM_QUERY,gitSearchQuery)
                    .appendQueryParameter(PARAM_SORT,sortBy).build();
        Log.e(Context.class.getSimpleName(),uri.toString());
        URL url = null;
        try
        {
            url = new URL(uri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromJHttpUrl (URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner sc=  new Scanner(in);
            sc.useDelimiter("\\A");

//            Log.e(NetworkUtils.class.getName(),"This is Scanner Output = "+sc.toString());
            boolean hasInpur= sc.hasNext();

            if (hasInpur)
            {
                return sc.next();
            }
            else
            {
                return null;
            }

        }
        finally {
            urlConnection.disconnect();
        }
    }

}
