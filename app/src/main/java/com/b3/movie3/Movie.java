package com.b3.movie3;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Movie {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("vote_average")
    private Double voteAverage;


    public Movie(String posterPath, String overview, String releaseDate, String originalLanguage, String title, Double voteAverage) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    Movie(JSONObject movie) {
    }

    String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
    String getOverview() {
        return overview;
    }
    String getReleaseDate() {
        return releaseDate;
    }
    String getOriginalLanguage() {
        return originalLanguage;
    }
    String getTitle() {
        return title;
    }
    Double getVoteAverage() {
        return voteAverage;
    }
}

