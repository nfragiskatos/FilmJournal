package com.create.nfraggle.filmjournal.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.create.nfraggle.filmjournal.data.FilmContract.FilmRollEntry;

/**
 * Created by Nicholas Fragiskatos on 4/15/2018.
 */
public class FilmRollProvider extends ContentProvider {

    private FilmDbHelper mFilmDbHelper;

    private static final int FILM_ROLL = 100;
    private static final int FILM_ROLL_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FilmContract.CONTENT_AUTHORITY, FilmContract.PATH_FILM_ROLLS, FILM_ROLL);
        sUriMatcher.addURI(FilmContract.CONTENT_AUTHORITY, FilmContract.PATH_FILM_ROLLS + "/#", FILM_ROLL_ID);
    }

    public static final String LOG_TAG = FilmRollProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mFilmDbHelper = new FilmDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mFilmDbHelper.getReadableDatabase();

        Cursor cursor;

        switch (sUriMatcher.match(uri))
        {
            // whole table
            case FILM_ROLL:
                cursor = db.query(FilmRollEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            // single row
            case FILM_ROLL_ID:
                selection = FilmRollEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(FilmRollEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match)
        {
            case FILM_ROLL:
                return FilmRollEntry.CONTENT_LIST_TYPE;
            case FILM_ROLL_ID:
                return FilmRollEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        switch (sUriMatcher.match(uri))
        {
            case FILM_ROLL:
                return insertFilmRoll(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertFilmRoll(Uri uri, ContentValues contentValues)
    {
        /**
         * Do some data validation
         */
        Integer size = contentValues.getAsInteger(FilmRollEntry.COLUMN_SIZE);
        if (!FilmRollEntry.isValidSize(size))
        {
            throw new IllegalArgumentException("Not a valid film size");
        }

        Integer speed = contentValues.getAsInteger(FilmRollEntry.COLUMN_SPEED);
        if (speed < 0)
        {
            throw new IllegalArgumentException("Not a valid film speed");
        }

        SQLiteDatabase db = mFilmDbHelper.getWritableDatabase();

        long id = db.insert(FilmRollEntry.TABLE_NAME, null, contentValues);

        if (id == -1)
        {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mFilmDbHelper.getWritableDatabase();
        int rowsDeleted;

        switch (sUriMatcher.match(uri))
        {
            case FILM_ROLL:
                rowsDeleted = db.delete(FilmRollEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FILM_ROLL_ID:
                selection = FilmRollEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(FilmRollEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }

        if (rowsDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (sUriMatcher.match(uri))
        {
            case FILM_ROLL:
                return updateFilmRoll(uri, contentValues, selection, selectionArgs);
                break;
            case FILM_ROLL_ID:
                selection = FilmRollEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateFilmRoll(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateFilmRoll(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {

    }
}
