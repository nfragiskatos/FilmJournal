package com.create.nfraggle.filmjournal;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by Nicholas Fragiskatos on 4/17/2018.
 */
public class FilmRollEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText mDescriptionEditText;
    private EditText mDateEditText;

    private static final int FILM_ROLL_LOADER = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_roll_editor);

        mDescriptionEditText = (EditText) findViewById(R.id.edit_film_roll_description);
        mDateEditText = (EditText) findViewById(R.id.edit_film_roll_date);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
