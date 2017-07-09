package com.tincio.capstoneproject.data.service;

import android.os.AsyncTask;

import com.tincio.capstoneproject.data.dao.SoccerFieldDao;
import com.tincio.capstoneproject.data.database.PichangaDB;
import com.tincio.capstoneproject.data.model.SoccerField;
import com.tincio.capstoneproject.data.service.response.SoccerFieldResponse;
import com.tincio.capstoneproject.dominio.callback.MainCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by juan on 21/05/2017.
 */
public class SoccerFieldRepository {

    private SoccerFieldDao dao;
    private MainCallback callback;
    public SoccerFieldRepository(PichangaDB helper, MainCallback callback) {
        dao = new SoccerFieldDao(helper);
        this.callback = callback;
    }

    private SoccerField convertToRecipe(SoccerFieldResponse mResponse){
        SoccerField mRecipe = new SoccerField();
        mRecipe.setId(mResponse.getId().toString());
        mRecipe.setDescription(mResponse.getDescription());
        mRecipe.setName(mResponse.getName());
        mRecipe.setImage(mResponse.getImage());
        mRecipe.setLatitude(mResponse.getLatitude());
        mRecipe.setLongitude(mResponse.getLongitude());
        mRecipe.setPrice(mResponse.getPrice());
        return mRecipe;
    }

    private void saveInDataBase(List<SoccerFieldResponse> list){
        dao.delete();
        for (SoccerFieldResponse response: list){
            insertRecipeAsync(convertToRecipe(response));
        }
    }


    public void insertRecipeAsync(SoccerField mRecipe){
        new InsertRecipe().execute(mRecipe);


    }

    private class InsertRecipe extends AsyncTask<SoccerField,Void,Void> {

        @Override
        protected Void doInBackground(SoccerField... params) {
            try{
                dao.insert(params[0]);
            }catch (Exception e){
                callback.onResponse(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.onResponse("");
        }
    }

    public void getSoccerField() {
        //.client(httpClient.build())
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.serviceNames.BASE_RECIPES)
                .build();
        try {
            WebService service = retrofit.create(WebService.class);
            Call<List<SoccerFieldResponse>> call = service.getSoccerField();
            System.out.println("call url "+call.request().url());
            call.enqueue(new Callback<List<SoccerFieldResponse>>() {
                @Override
                public void onResponse(Call<List<SoccerFieldResponse>> call, Response<List<SoccerFieldResponse>> response) {
                    System.out.println("lista recipe "+ response);
                    /***save in data base*/
                    saveInDataBase(response.body());
                }

                @Override
                public void onFailure(Call<List<SoccerFieldResponse>> call, Throwable t) {
                    System.out.println("error " +
                            "lista recipe "+t.getMessage());
                    callback.onResponse(t.getMessage());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
