package com.tincio.capstoneproject.presentation.view;

import android.database.Cursor;

public interface MapsView extends MvpView {
    void getSoccerFields(Cursor listFields);
}
