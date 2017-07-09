package com.tincio.capstoneproject.presentation.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.tincio.capstoneproject.R;
import com.tincio.capstoneproject.data.provider.SoccerFieldContract;

import static com.tincio.capstoneproject.data.provider.SoccerFieldContract.BASE_CONTENT_URI;
import static com.tincio.capstoneproject.data.provider.SoccerFieldContract.PATH_FIELD;


public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Cursor mCursor;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        Uri PLANT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FIELD).build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                PLANT_URI,
                null,
                null,
                null,
                SoccerFieldContract.SoccerEntry.COLUMN_NAME
        );
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        int nameValue = mCursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_NAME);
        String name = mCursor.getString(nameValue);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_app_widget);
        views.setTextViewText(R.id.appwidget_text, name);
        Bundle extras = new Bundle();
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.appwidget_text, fillInIntent);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

