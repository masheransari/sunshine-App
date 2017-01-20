package com.example.asheransari.sunshineupdated.data;

import android.content.Context;

/**
 * Created by asher.ansari on 1/19/2017.
 */

public class SunshinePreferences {

    public static final String PREF_CITY_NAME = "city_name";

    public static final String PREF_COORD_LAT = "coord_lat";

    public static final String PREF_COORD_LONG = "coord_long";

    private static final String DEFAULT_WEATHER_LOCATION = "94043,USA";
    private static final double[] DEFAULT_WEATHER_COORDINATES = {37.4284,122.0724};

    public static final String DEFAULT_MAP_LOCATION =
            "1600 Amphitheatre Parkway, Mountain View, CA 94043";


    public static void setLocationDetails(Context c, String cityName, double lat, double lon)
    {

    }
    static public void resetLocationCoordinates(Context c)
    {

    }

    public static String getPreferredWeatherLocation(Context c)
    {
        return getDefaultWeatherLocation();
    }
    public static boolean isMetric(Context c)
    {
        return true;
    }

    public static double[] getLocationCoordinates(Context c)
    {
        return getDefaultWeatherCoordinates();
    }

    public static boolean isLocationLatLonAvailable(Context c)
    {
        return false;
    }
    private static String getDefaultWeatherLocation()
    {
        return DEFAULT_WEATHER_LOCATION;
    }

    public static double[] getDefaultWeatherCoordinates()
    {
        return DEFAULT_WEATHER_COORDINATES;
    }

}
