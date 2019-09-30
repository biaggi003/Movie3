package com.b3.movie3;

import com.google.gson.annotations.SerializedName;

public class TVShow {
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("overview")
    private String overview;

    public TVShow(String name, String posterPath, String firstAirDate, Double voteAverage, String originalLanguage, String overview) {
        this.name = name;
        this.posterPath = posterPath;
        this.firstAirDate = firstAirDate;
        this.voteAverage = voteAverage;
        this.originalLanguage = originalLanguage;
        this.overview = overview;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOverview() {
        return overview;
    }
}
