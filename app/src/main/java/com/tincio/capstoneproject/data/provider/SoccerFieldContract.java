package com.tincio.capstoneproject.data.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class SoccerFieldContract {

    public static final String AUTHORITY = "com.tincio.capstoneproject";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FIELD = "soccerfield";

    //public static final long INVALID_PLANT_ID = -1;

    public static final class SoccerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FIELD).build();

        public static final String TABLE_NAME = "soccerfield";
        //public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_AVAILABLE = "available";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_SERVICE = "service";
        public static final String COLUMN_IMAGE = "image";
    }
}
