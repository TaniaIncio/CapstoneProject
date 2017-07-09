package com.tincio.capstoneproject.presentation.presenter;

import com.tincio.capstoneproject.data.dao.SoccerFieldDao;
import com.tincio.capstoneproject.data.database.PichangaDB;
import com.tincio.capstoneproject.presentation.view.MapsView;

public class MapsPresenter implements MvpPresenter<MapsView> {

    MapsView view;
    SoccerFieldDao repository;
    PichangaDB db;

    public MapsPresenter(PichangaDB db, MapsView view){
        this.view = view;
        this.db = db;
    }
    @Override
    public void setView(MapsView view) {
        this.view = view;
    }

    @Override
    public  void detachView() {
        view = null;
    }

    public void getSoccerField(){
        try{
            repository = new SoccerFieldDao(db);
            view.closeLoading();
            view.getSoccerFields( repository.getSoccerFields());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
