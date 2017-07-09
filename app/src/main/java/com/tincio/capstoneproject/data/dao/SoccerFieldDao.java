package com.tincio.capstoneproject.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tincio.capstoneproject.data.database.PichangaDB;
import com.tincio.capstoneproject.data.model.SoccerField;
import com.tincio.capstoneproject.data.provider.SoccerFieldContract;

/**
 * Created by juan on 4/07/2017.
 */

public class SoccerFieldDao {

    private PichangaDB mDbHelper;

    public  SoccerFieldDao(PichangaDB mPcihangaDB){
        mDbHelper = mPcihangaDB;
    }

    public void delete(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(SoccerFieldContract.SoccerEntry.TABLE_NAME, null, null);
    }

    public long insert(SoccerField mSoccer){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_ADDRESS, mSoccer.getAddress());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_AVAILABLE, mSoccer.getAvailable());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_DESCRIPTION, mSoccer.getDescription());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_LATITUDE, mSoccer.getLatitude());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_LONGITUDE, mSoccer.getLongitude());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_NAME, mSoccer.getName());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_PRICE, mSoccer.getPrice());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_SERVICE, mSoccer.getAddress());
        values.put(SoccerFieldContract.SoccerEntry.COLUMN_IMAGE, mSoccer.getImage());
        values.put(SoccerFieldContract.SoccerEntry._ID, mSoccer.getId());

        long newRowId = db.insert(SoccerFieldContract.SoccerEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public Cursor getSoccerFields(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                SoccerFieldContract.SoccerEntry._ID,
                SoccerFieldContract.SoccerEntry.COLUMN_DESCRIPTION,
                SoccerFieldContract.SoccerEntry.COLUMN_LONGITUDE,
                SoccerFieldContract.SoccerEntry.COLUMN_LATITUDE,
                SoccerFieldContract.SoccerEntry.COLUMN_ADDRESS,
                SoccerFieldContract.SoccerEntry.COLUMN_NAME,
                SoccerFieldContract.SoccerEntry.COLUMN_SERVICE,
                SoccerFieldContract.SoccerEntry.COLUMN_IMAGE,
                SoccerFieldContract.SoccerEntry.COLUMN_PRICE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = "";//FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = null;// { "My Title" };

// How you want the results sorted in the resulting Cursor
        String sortOrder = "";
                //FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor c = db.query(
                SoccerFieldContract.SoccerEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }
}
