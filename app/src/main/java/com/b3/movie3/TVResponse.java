package com.b3.movie3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVResponse {
    @SerializedName("results")
    private List<TVShow> results;

    public List<TVShow> getResults() {
        return results;
    }
}
