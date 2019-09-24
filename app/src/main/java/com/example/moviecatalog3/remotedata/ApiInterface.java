package com.example.moviecatalog3.remotedata;

import com.example.moviecatalog3.model.MoviesRespon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/discover/{category}")
    Call<MoviesRespon> getMovies(@Path("category") String category, @Query("api_key") String api_key, @Query("language") String lang);

    @GET("3/search/{category}")
    Call<MoviesRespon> searchMovies(@Path("category") String category, @Query("api_key") String apiKey, @Query("language") String lang, @Query("query") String query);

    @GET("3/discover/movie")
    Call<MoviesRespon> releaseMovies(@Query("api_key") String apiKey, @Query("primary_release_date.gte") String primaryDateGte, @Query("primary_release_date.lte") String primaryDateLte);

}
