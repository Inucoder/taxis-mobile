package com.mobile.taxi.utils;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.mobile.taxi.models.Prediction;

import java.util.List;

/**
 * Created by Irving on 21/03/2015.
 */
public class PredictionsCursorFactory {

    public static Cursor generate(List<Prediction> predictions, String[] from){

        MatrixCursor cursor = new MatrixCursor(from);
        Integer i = 0;

        for (Prediction prediction : predictions) {
            String[] temp = new String[3];
            temp[0] = (i++).toString();
            temp[1] = prediction.getPlaceId();
            temp[2] = prediction.getDescription();
            cursor.addRow(temp);
        }

        return cursor;

    }

}
