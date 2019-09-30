package com.b3.movie3;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowRepo {
    private static TVShowRepo tvShowRepo;
    private static Service service;

    public static TVShowRepo getInstance(){
        if (tvShowRepo == null){
            tvShowRepo = new TVShowRepo();
        }
        return tvShowRepo;
    }

    private TVShowRepo() {
        service = Base.createService();
    }

    public static MutableLiveData<TVResponse> getTvs(String apiKey, String oriLanguage){
        final MutableLiveData<TVResponse> tvData = new MutableLiveData<>();
        service.getTVList(apiKey, oriLanguage).enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVResponse> call, @NonNull Response<TVResponse> response) {
                if (response.isSuccessful()){
                    tvData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TVResponse> call, @NonNull Throwable t) {
                tvData.setValue(null);
            }
        });
        return tvData;
    }
}

