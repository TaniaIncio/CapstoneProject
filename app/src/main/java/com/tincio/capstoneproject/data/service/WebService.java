package com.tincio.capstoneproject.data.service;

import com.tincio.capstoneproject.data.service.response.SoccerFieldResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {
    @GET("fieldsoccer")
    Call<List<SoccerFieldResponse>> getSoccerField();

}
