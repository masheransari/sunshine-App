package com.example.asheransari.sunshineupdated.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by asher.ansari on 1/21/2017.
 */

public class OpenWeatherJsonUtils {


    public static String[] getSimpleWEatherStringFromJson(Context context, String forecastJsonStr) throws JSONException {
        final String OWM_LIST = "list";
        final String OWM_TEMPERATURE = "temp";

        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";

        final String OWM_MESSAGE_CODE = "cod";

        String[] parsedWeatherData = null;

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpsURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        long localDate = System.currentTimeMillis();
        long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
        long startDay = SunshineDateUtils.normalizeDate(utcDate);

        for (int i = 0; i < weatherArray.length(); i++) {
            String date;
            String highAndLow;
            long dateTimeMillis;
            double high;
            double low;
            String discription;

            JSONObject dayForecast = weatherArray.getJSONObject(i);

            dateTimeMillis = startDay + SunshineDateUtils.DAYS_IN_MILLIS * i;
            date = SunshineDateUtils.getFriendlyDateString(context, dateTimeMillis, false);

            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            discription = weatherObject.getString(OWM_DESCRIPTION);

            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);
            highAndLow = SunshineWeatherUtils.formatHighLows(context, high, low);
            parsedWeatherData[i] = date + " - " + discription + " - " + highAndLow;
        }
        //TODO 1 : Hamara jo main Data save ho rha hai JSON se wo is array me save ho rha hai..

        return parsedWeatherData;
    }
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr)
    {
        return null;
    }

}
