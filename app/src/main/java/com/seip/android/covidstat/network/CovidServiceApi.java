package com.seip.android.covidstat.network;

import com.seip.android.covidstat.models.CovidResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CovidServiceApi {
    @GET
    Call<CovidResponseModel> getCurrentData(@Url String endUrl);

}
