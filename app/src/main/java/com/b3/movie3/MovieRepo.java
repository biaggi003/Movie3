package com.b3.movie3;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MovieRepo {
    private static MovieRepo movieRepo;
    private Service service;

    static MovieRepo getInstance() {
        if (movieRepo == null) {
            movieRepo = new MovieRepo();
        }
        return movieRepo;
    }

    private MovieRepo() {
        service = Base.createService();
    }

    MutableLiveData<MovieResponse> getMovie() {
        final MutableLiveData<MovieResponse> movieData = new MutableLiveData<>();
        service.getMovieList(BuildConfig.API_TOKEN, BuildConfig.ORI_LANGUAGE).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }
}
