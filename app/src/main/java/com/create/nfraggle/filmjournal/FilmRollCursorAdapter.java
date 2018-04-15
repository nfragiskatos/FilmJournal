package com.create.nfraggle.filmjournal;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.create.nfraggle.filmjournal.data.FilmContract.FilmRollEntry

import com.create.nfraggle.filmjournal.data.FilmContract;

/**
 * Created by Nicholas Fragiskatos on 4/15/2018.
 */
public class FilmRollCursorAdapter extends CursorAdapter {

    public FilmRollCursorAdapter (Context context, Cursor cursor)
    {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.list_item_film_roll, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvDescription = (TextView) view.findViewById(R.id.description_textView_film_roll);
        TextView tvDate = (TextView) view.findViewById(R.id.date_textView_film_roll);

        String description = cursor.getString(cursor.getColumnIndexOrThrow(FilmContract.FilmRollEntry.COLUMN_DESCRIPTION));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(FilmRollEntry.COLUMN_DATE));

        tvDescription.setText(description);
        tvDate.setText(date);
    }
}
