package com.tincio.capstoneproject.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tincio.capstoneproject.data.provider.SoccerFieldContract;

/**
 * Created by juan on 4/07/2017.
 */

public class PichangaDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Pichanga.db";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SoccerFieldContract.SoccerEntry.TABLE_NAME;

    public PichangaDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addSoccerFieldTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    private void addSoccerFieldTable(SQLiteDatabase db){
        db.execSQL(
                "CREATE TABLE " + SoccerFieldContract.SoccerEntry.TABLE_NAME + " (" +
                        SoccerFieldContract.SoccerEntry._ID + " INTEGER PRIMARY KEY , " +
                        SoccerFieldContract.SoccerEntry.COLUMN_NAME + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_DESCRIPTION + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_LATITUDE + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_LONGITUDE + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_AVAILABLE + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_ADDRESS + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_PRICE + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_IMAGE + " TEXT  NULL, " +
                        SoccerFieldContract.SoccerEntry.COLUMN_SERVICE + "  TEXT  NULL )" );
                    /*    "FOREIGN KEY (" + Table.SoccerField.COLUMN_ID + ") " +
                        "REFERENCES " +Table.SoccerField.COLUMN_IDE + " (" + GenreEntry._ID + "));"*/

    }


}
