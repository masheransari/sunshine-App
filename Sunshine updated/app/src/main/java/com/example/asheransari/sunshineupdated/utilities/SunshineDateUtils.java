package com.example.asheransari.sunshineupdated.utilities;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.asheransari.sunshineupdated.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by asher.ansari on 1/21/2017.
 */

public class SunshineDateUtils {
    private static final String TAG =SunshineDateUtils.class.getSimpleName();
    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAYS_IN_MILLIS = HOUR_IN_MILLIS * 24;

    public static long getDayNumber(long date)
    {
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffset = timeZone.getOffset(date);

        Log.e(TAG, "IN getDayNumber()--->  where:   date = "+date + ", gmtOffset = "+gmtOffset+ ", and DAYS_IN_MILLIS = "+DAYS_IN_MILLIS);
        return (date + gmtOffset) / DAYS_IN_MILLIS;
    }

    public static long normalizeDate(long date)
    {
        long retValNews = date / DAYS_IN_MILLIS * DAYS_IN_MILLIS;
        Log.e(TAG,"in normalizeDate()--->  where date = "+date+ ", and retValNews = "+retValNews);
        return retValNews;
    }

    public static long getLocalDateFormatUTC(long utcDate)
    {
        TimeZone timeZone = TimeZone.getDefault();
        Log.e(TAG,"in getLocalDateFormatUTC() where ----> timeZone = "+timeZone);
        long gmtOffset = timeZone.getOffset(utcDate);
        Log.e(TAG," in getLocalDateFormatUTC() where ---> utcDate = "+utcDate+", gmtOffset = "+gmtOffset + "and of both is (utcDate - gmtOffset) = "+(utcDate - gmtOffset));
        return utcDate - gmtOffset;
    }
    public static long getUTCDateFromLocal(long localDate) {
        TimeZone tz = TimeZone.getDefault();
        Log.e(TAG,"in getLocalDateFormatUTC() where ----> timeZone = "+tz);
        long gmtOffset = tz.getOffset(localDate);
        Log.e(TAG," in getLocalDateFormatUTC() where ---> utcDate = "+localDate+", gmtOffset = "+gmtOffset + "and of both is (localDate + gmtOffset) = "+(localDate + gmtOffset));
        return localDate + gmtOffset;
    }
    public static String getFriendlyDateString(Context context, long dateInMillis, boolean showFullDate) {
        long localDate = getLocalDateFormatUTC(dateInMillis);
        long dayNumber = getDayNumber(localDate);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());

        if (dayNumber == currentDayNumber || showFullDate) {
            String dayName = getDayName(context, localDate);
            String readableDAte = getReadableDateString(context, localDate);

            if ((dayNumber - currentDayNumber) < 2) {
                String localizeDayName = new SimpleDateFormat("EEEE").format(localDate);
                Log.e(TAG, "in getFriendlyDateString() where ----> dayName = " + dayName + " readableDAte = " + readableDAte + ", and after replace [  readableDAte.replace(localizeDayName,dayName)  ] = " + readableDAte.replace(localizeDayName, dayName));
                return readableDAte.replace(localizeDayName, dayName);
            } else {
                Log.e(TAG, "in getFriendlyDateString() and else condition on under the first if condition where --->  readableDAte" + readableDAte);
                return readableDAte;
            }
        } else if (dayNumber < currentDayNumber + 7) {
            Log.e(TAG, "in getFriendlyDateString() at first else if condition... where [  getDayName(context,localDate)  ] = " + getDayName(context, localDate));
            return getDayName(context, localDate);
        } else {
            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_ABBREV_ALL
                    | DateUtils.FORMAT_ABBREV_WEEKDAY;
            Log.e(TAG, "in getFriendlyDateString() in main Else COndition where ---> [  DateUtils.formatDateTime(context,localDate,flags)  ] = " + DateUtils.formatDateTime(context, localDate, flags));
            return DateUtils.formatDateTime(context, localDate, flags);
        }
    }
    private static String getReadableDateString(Context context, long timeInMillis)
    {
        int flag = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NO_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY;

        Log.e(TAG, "in getReadableDateString() where ---> [  DateUtils.formatDateTime(context,timeInMillis,flag)  ] = "+DateUtils.formatDateTime(context,timeInMillis,flag));
        return DateUtils.formatDateTime(context,timeInMillis,flag);
    }

    private static String getDayName(Context context, long dateInMillis)
    {
        long dayNumber = getDayNumber(dateInMillis);
        Log.e(TAG,"in getDayName() where ---> dayName = " +dayNumber);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());
        Log.e(TAG,"in getDayName() where ---> currentDayNumber = "+currentDayNumber);
        if (dayNumber == currentDayNumber)
        {
//            return context.getString(R.id.today);
            return context.getString(R.string.today);
        }
        else if (dayNumber == currentDayNumber + 1)
        {
            return context.getString(R.string.tommorow);
        }
        else
        {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMillis);
        }
    }
}
