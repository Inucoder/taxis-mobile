package com.mobile.taxi.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Irving on 21/03/2015.
 */
public class PlacesSuggestionAdapter extends SimpleCursorAdapter{

    public PlacesSuggestionAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView description = (TextView) view.findViewById(android.R.id.text1);
        description.setText(cursor.getString(2));
    }
}
