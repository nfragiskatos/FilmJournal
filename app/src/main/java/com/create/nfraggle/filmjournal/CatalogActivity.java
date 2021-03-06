package com.create.nfraggle.filmjournal;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.create.nfraggle.filmjournal.data.FilmContract;
import com.create.nfraggle.filmjournal.data.FilmDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private FilmDbHelper mFilmDbHelper;
    public static final String LOG_TAG = CatalogActivity.class.getSimpleName();

    private static final int FILM_ROLL_LOADER = 0;

    private FilmRollCursorAdapter mFilmRollCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open FilmRollEditActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, FilmRollEditorActivity.class);
                startActivity(intent);
            }
        });

        mFilmDbHelper = new FilmDbHelper(this);
        mFilmRollCursorAdapter = new FilmRollCursorAdapter(this, null);

        ListView filmRollListView = (ListView) findViewById(R.id.list_view_film_roll);
        View emptyView = findViewById(R.id.empty_view);

        filmRollListView.setAdapter(mFilmRollCursorAdapter);
        filmRollListView.setEmptyView(emptyView);

        filmRollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, FilmRollEditorActivity.class);
                Uri currentFilmRollUri = ContentUris.withAppendedId(FilmContract.FilmRollEntry.CONTENT_URI, id);
                intent.setData(currentFilmRollUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(FILM_ROLL_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_catalog_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        deleteAllFilmRoles();
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllFilmRoles()
    {
        getContentResolver().delete(FilmContract.FilmRollEntry.CONTENT_URI, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String projection[] = {
                FilmContract.FilmRollEntry._ID,
                FilmContract.FilmRollEntry.COLUMN_DESCRIPTION,
                FilmContract.FilmRollEntry.COLUMN_DATE
        };

        return new CursorLoader(this, FilmContract.FilmRollEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mFilmRollCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mFilmRollCursorAdapter.swapCursor(null);
    }
}
