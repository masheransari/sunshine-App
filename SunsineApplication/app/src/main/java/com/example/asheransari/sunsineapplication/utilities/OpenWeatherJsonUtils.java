package com.example.asheransari.sunsineapplication.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.example.asheransari.sunsineapplication.data.SunshinePreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.jar.JarException;

/**
 * Created by asher.ansari on 12/23/2016.
 */

public final class OpenWeatherJsonUtils {

    public static String[] getSimpleWeatherStringFromJson(Context context, String forcaseJsonString) throws JSONException {
        //this is basically the variable of json variables
        final String OWM_LIST = "list";

        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";

        final String OWM_MESSAGE_CODE = "cod";

        String[] parsedWeatherData = null;
        JSONObject forecaseJson = new JSONObject(forcaseJsonString);

        if (forecaseJson.has(OWM_MESSAGE_CODE)) {
            int errorCodr = forecaseJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCodr) {
                case HttpURLConnection.HTTP_OK:         ////this is like 200 etc,,,
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:      ///404 error etc
                    return null;
            }
        }

        JSONArray weatherArray = forecaseJson.getJSONArray(OWM_LIST);       ///yeh hamare pas jo json a rha hai, us me array hai 'list' ke nam se...

        long localDate = System.currentTimeMillis();                        ///yeh apki current time la rha hai system se in Millis Second...
        long utcDate = SunshileDataUtils.getUTCDateFromLocal(localDate);     ///yeh apki gmtDate(System se leke a rhe hai through TImeZone) ur apki local date dono ka sum kr rha hai...
        long startDay = SunshileDataUtils.normalizeDate(utcDate);           ///it is convert into millis...

        for (int i = 0; i < weatherArray.length(); i++) {               ///list array ki length par for loop chal rha hai....
            String date;                                                ///for date
            String highAndLow;                                          ////for high and low temperature    jsut like    25.64/12.56
            long dateTimeMillis;                                        ////your date in millis..
            double high;                                                ////high temperature
            double low;                                                 ////low temperature
            String description;                                         ////detaill

            JSONObject dayForcast = weatherArray.getJSONObject(i);          ///         yeha pe hum us array ke jo elements hai un se khelenge one by one through loop..
            dateTimeMillis = startDay + SunshileDataUtils.DAYS_IN_MILLIS * i;   ///DAYS_IN_MILLIS me apke pas millis me date a rhe hai jo DAYS me convert hai..
            date = SunshileDataUtils.getFriendlyDateString(context, dateTimeMillis, false);
            JSONObject wetherObject = dayForcast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = wetherObject.getString(OWM_DESCRIPTION);

            JSONObject tempObject = dayForcast.getJSONObject(OWM_TEMPERATURE);
            high = tempObject.getDouble(OWM_MAX);
            low = tempObject.getDouble(OWM_MIN);
            highAndLow = SunshineWeatherUtils.formatHighLow(context, high, low);

            parsedWeatherData[i] = date + " - " + description + " - " + highAndLow;
        }
        return parsedWeatherData;
    }

    public static ContentValues[] getFullWeatherDataFromJson(Context c, String forcastJsonStr) {
        return null;
    }
}
