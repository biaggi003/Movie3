package com.b3.movie3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieResponse> mutableLiveData;

    public void init(){
        if (mutableLiveData != null) {
            return;
        }
        MovieRepo movieRepo = MovieRepo.getInstance();
        mutableLiveData = movieRepo.getMovie();
    }

    public LiveData<MovieResponse> getMovieRepo() {
        return mutableLiveData;
    }
}
