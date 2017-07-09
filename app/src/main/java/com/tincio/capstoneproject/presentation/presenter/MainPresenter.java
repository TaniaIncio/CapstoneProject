package com.tincio.capstoneproject.presentation.presenter;

import com.tincio.capstoneproject.data.database.PichangaDB;
import com.tincio.capstoneproject.data.service.SoccerFieldRepository;
import com.tincio.capstoneproject.dominio.callback.MainCallback;
import com.tincio.capstoneproject.presentation.view.MainView;
public class MainPresenter implements MvpPresenter<MainView>, MainCallback {

    MainView view;
    SoccerFieldRepository repository;
    PichangaDB db;

    public MainPresenter(PichangaDB db, MainView view){
        this.view = view;
        this.db = db;
    }
    @Override
    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public  void detachView() {
        view = null;
    }

    public void getSoccerField(){
        try{
            repository = new SoccerFieldRepository(db, this);
            repository.getSoccerField();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onResponse(String mensajes) {
        view.closeLoading();
        view.responseSoccerField(mensajes);
    }
}
