package com.example.asheransari.sunshineupdated.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by asher.ansari on 1/19/2017.
 */

public class NetworkUtilities {
    private static final String TAG = NetworkUtilities.class.getName();

    private static final String DYNAMIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/weather";
    private static final String STATIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/staticweather";
    private static final String FORECAST_BASE_URL = STATIC_WEATHER_URL;

    private static final String format = "json";
    private static final String units = "metric";
    private static final int numDays = 14;

    static final String QUERY_PARAM = "q";
    static final String LAT_PARAM = "lat";
    static final String LON_PARAM = "lon";
    static final String FORMAT_PARAM = "mode";
    static final String UNITS_PARAM = "units";
    static final String DAYS_PARAM = "cnt";

    public static URL buildUrl(String locationQuery)
    {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM,locationQuery)
                .appendQueryParameter(FORMAT_PARAM,format)
                .appendQueryParameter(UNITS_PARAM,units)
                .appendQueryParameter(DAYS_PARAM, String.valueOf(numDays)).build();

        URL url = null;
        try
        {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        Log.e(TAG,"URL = "+url);
        return url;
    }

    public static final String getResponseFromHttpUrl(URL url)throws IOException
    {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

        try
        {
            InputStream in = urlConnection.getInputStream();

            Scanner sc = new Scanner(in);

            sc.useDelimiter("\\A");

            boolean hasInput =sc.hasNext();

            if (hasInput)
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
