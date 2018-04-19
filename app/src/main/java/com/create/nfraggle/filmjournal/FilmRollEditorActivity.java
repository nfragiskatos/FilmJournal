package com.create.nfraggle.filmjournal;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.create.nfraggle.filmjournal.data.FilmContract.FilmRollEntry;

import com.create.nfraggle.filmjournal.data.FilmContract;

/**
 * Created by Nicholas Fragiskatos on 4/17/2018.
 */
public class FilmRollEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText mDescriptionEditText;
    private EditText mDateEditText;

    private static final int FILM_ROLL_LOADER = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_film_roll_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_save:
                saveFilmRoll();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }


        return true;
    }

    private void saveFilmRoll()
    {
        String description = mDescriptionEditText.getText().toString().trim();
        String date = mDateEditText.getText().toString().trim();

        if (TextUtils.isEmpty(description) && TextUtils.isEmpty(date)) return;

        ContentValues values = new ContentValues();
        values.put(FilmRollEntry.COLUMN_DESCRIPTION, description);
        values.put(FilmRollEntry.COLUMN_DATE, date);

        Uri uri = getContentResolver().insert(FilmRollEntry.CONTENT_URI, values);

        if (uri == null)
        {
            Toast.makeText(this, "Insert film roll failed!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Film roll saved!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_roll_editor);

        Intent intent = getIntent();
        Uri currentFilmRollUri = intent.getData();

        if (currentFilmRollUri == null)
        {
            setTitle(R.string.film_roll_editor_activity_title_new_roll);
        }
        else
        {
            setTitle(R.string.film_roll_editor_activity_title_edit_roll);
            getLoaderManager().initLoader(FILM_ROLL_LOADER, null, this);
        }

        mDescriptionEditText = (EditText) findViewById(R.id.edit_film_roll_description);
        mDateEditText = (EditText) findViewById(R.id.edit_film_roll_date);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String projection[] = {
                FilmRollEntry._ID,
                FilmRollEntry.COLUMN_DESCRIPTION,
                FilmRollEntry.COLUMN_DATE
        };

        Uri uri = getIntent().getData();

        return new CursorLoader(this, uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || (cursor !=null && cursor.getCount() == 0))
        {
            return;
        }

        cursor.moveToFirst();
        String description = cursor.getString(cursor.getColumnIndexOrThrow(FilmRollEntry.COLUMN_DESCRIPTION));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(FilmRollEntry.COLUMN_DATE));

        mDateEditText.setText(date);
        mDescriptionEditText.setText(description);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mDateEditText.setText(null);
        mDescriptionEditText.setText(null);
    }
}
