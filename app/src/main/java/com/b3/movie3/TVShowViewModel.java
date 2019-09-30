package com.b3.movie3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TVShowViewModel extends ViewModel {
    private MutableLiveData<TVResponse> mutableLiveData;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        TVShowRepo tvShowRepo = TVShowRepo.getInstance();
        mutableLiveData = TVShowRepo.getTvs(BuildConfig.API_TOKEN, BuildConfig.ORI_LANGUAGE);
    }

    public LiveData<TVResponse> getTVRepository() {
        return mutableLiveData;
    }
}