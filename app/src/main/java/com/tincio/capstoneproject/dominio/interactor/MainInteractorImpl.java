package com.tincio.capstoneproject.dominio.interactor;

import android.util.Log;

import com.tincio.capstoneproject.data.database.PichangaDB;
import com.tincio.capstoneproject.data.service.SoccerFieldRepository;
import com.tincio.capstoneproject.dominio.callback.MainCallback;

/**
 * Created by tincio on 04/03/17.
 */

public class MainInteractorImpl implements MainInteractor{

    MainCallback callback;
    PichangaDB db;
    public MainInteractorImpl(MainCallback callback, PichangaDB db) {
        this.callback = callback;
        this.db = db;
    }



    @Override
    public void getSoccerField() {
        SoccerFieldRepository repository = new SoccerFieldRepository(db, callback);
        repository.getSoccerField();
    }


    /**FIn Retrofit**/
}
