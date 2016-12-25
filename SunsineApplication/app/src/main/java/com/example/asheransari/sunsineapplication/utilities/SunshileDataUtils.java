package com.example.asheransari.sunsineapplication.utilities;

import android.content.Context;
import android.text.format.DateUtils;

import com.example.asheransari.sunsineapplication.R;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by asher.ansari on 12/24/2016.
 */

public class SunshileDataUtils {
    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTES_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOURS_IN_MILLIS = MINUTES_IN_MILLIS * 60;
    public static final long DAYS_IN_MILLIS = HOURS_IN_MILLIS * 24;

    public static long getDayNumber(long date) {        ////es se hum day ka nmumber pata kr rhe hai...
        TimeZone tz = TimeZone.getDefault();
        long getOffSet = tz.getOffset(date);
        return (date + getOffSet) / DAYS_IN_MILLIS;
    }

    public static long normalizeDate(long Date) {                       ////yeha apki normal date convert hokr show ho rhi hai..
        long retValNew = Date / DAYS_IN_MILLIS * DAYS_IN_MILLIS;
        return retValNew;
    }

    public static long getLocalDateFromUTC(long utcDate) {      ////
        TimeZone tz = TimeZone.getDefault();
        long gmtOffser = tz.getOffset(utcDate);
        return utcDate - gmtOffser;
    }

    public static long getUTCDateFromLocal(long localDate) {////
        TimeZone tz = TimeZone.getDefault();
        long gmtOffSet = tz.getOffset(localDate);
        return localDate + gmtOffSet;
    }

    private static String getReadableDateString(Context context, long timeInMillis) {   ///
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR | DateUtils.FORMAT_SHOW_WEEKDAY;

        return DateUtils.formatDateTime(context, timeInMillis, flags);
    }

    private static String getDayName(Context context, long dateInMillis) {      ///         yeha se hamari day ki detail ja rhe hai... ke today hai ya tomorrow etc..
        long dayNumber = getDayNumber(dateInMillis);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());
        if (dayNumber == currentDayNumber) {
            return "Today";
        } else if (dayNumber == currentDayNumber + 1) {
            return "Tomorrow";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            return dateFormat.format(dateInMillis);
        }
    }


    public static String getFriendlyDateString(Context c, long DateInMillis, boolean showFullDates) {
        long localDate = getLocalDateFromUTC(DateInMillis);                 //// yeha se hum local date le rhe hai TIMEZONE ke through
        long dayNumber = getDayNumber(localDate);                           //// es me sayad hamre pas day ka number aye ga..
        long currentDayNumber = getDayNumber(System.currentTimeMillis());   //// yeha pe hamra pas current date aye ge from system millis ke form me...

        if (dayNumber == currentDayNumber || showFullDates) {
            String dayName = getDayName(c, localDate);                      ////
            String readableDate = getReadableDateString(c, localDate);      ////
            if (dayNumber - currentDayNumber < 2) {
                String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(localizedDayName, dayName);
            } else {
                return readableDate;
            }
        } else if (dayNumber < currentDayNumber + 7) {
            return getDayName(c, localDate);
        } else {
            int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR | DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_SHOW_WEEKDAY;
            return DateUtils.formatDateTime(c, localDate, flags);
        }
    }
}
