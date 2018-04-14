package com.create.nfraggle.filmjournal.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Nicholas Fragiskatos on 4/14/2018.
 */
public final class FilmContract {

    public static final String CONTENT_AUTHORITY = "com.create.nfraggle.filmjournal";

    public static final Uri BASE_CONTENT_AUTHORITY = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FILM_ROLLS = "filmRolls";

    private FilmContract (){};

    public static final class FilmRollEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "filmRolls";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_AUTHORITY, PATH_FILM_ROLLS);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILM_ROLLS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILM_ROLLS;


        /**
         * Define the table Columns
         */

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_DATE = "date";
    }
}
