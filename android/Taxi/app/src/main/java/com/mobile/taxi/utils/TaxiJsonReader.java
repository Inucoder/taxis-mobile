package com.mobile.taxi.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Irving on 20/03/2015.
 */
public class TaxiJsonReader {

    public static String loadJSONFromAsset(Context context) {

        String json = null;

        try {

            InputStream is = context.getAssets().open("zones.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

}
