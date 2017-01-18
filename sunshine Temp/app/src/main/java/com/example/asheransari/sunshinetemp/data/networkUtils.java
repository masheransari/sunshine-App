package com.example.asheransari.sunshinetemp.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by asher.ansari on 1/14/2017.
 */

public class networkUtils {

    public static URL buildUrl(String locationQuery)
    {
        return null;
    }

    public static URL bulidUrl(Double lat, Double lon)
    {
        return null;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

        try
        {
            InputStream in = urlConnection.getInputStream();

            Scanner sc = new Scanner(in);

            sc.useDelimiter("//A");
            boolean hasInput = sc.hasNext();
            if (hasInput)
            {
                return sc.next();
            }
            else{
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
