package com.create.nfraggle.filmjournal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.create.nfraggle.filmjournal.data.FilmContract.FilmRollEntry;

/**
 * Created by Nicholas Fragiskatos on 4/14/2018.
 */
public class FilmDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "film.db";

    public static final String LOG_TAG = FilmDbHelper.class.getSimpleName();

    public static final String SQL_COMMA = ",";

    public static final String SQL_CREATE_FILM_ROLL_ENTRIES =
            "CREATE TABLE " + FilmRollEntry.TABLE_NAME + " ("
            + FilmRollEntry._ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT"  + SQL_COMMA
            + FilmRollEntry.COLUMN_BRAND        + " TEXT NOT NULL"                      + SQL_COMMA
            + FilmRollEntry.COLUMN_SIZE         + " TEXT NOT NULL"                      + SQL_COMMA
            + FilmRollEntry.COLUMN_SPEED        + " INTEGER"                            + SQL_COMMA
            + FilmRollEntry.COLUMN_DATE         + " TEXT"                               + SQL_COMMA
            + FilmRollEntry.COLUMN_DESCRIPTION  + " TEXT"
            + ");";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FilmRollEntry.TABLE_NAME;

    public FilmDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_FILM_ROLL_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
