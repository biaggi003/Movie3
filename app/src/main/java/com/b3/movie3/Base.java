package com.b3.movie3;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Base {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    static Retrofit getBase() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    static <S> S createService() {
        return retrofit.create((Class<S>) Service.class);
    }
}
