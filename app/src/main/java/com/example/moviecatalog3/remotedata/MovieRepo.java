package com.example.moviecatalog3.remotedata;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviecatalog3.model.MoviesModel;
import com.example.moviecatalog3.model.MoviesRespon;
import com.example.moviecatalog3.reminder.Alarm;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepo {

    private ApiInterface apiInterface;
    private String message;

    public MovieRepo() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MoviesModel>> data = new MutableLiveData<>();

    public LiveData<ArrayList<MoviesModel>> searchMovies(String category, String lang, String apiKey, String query) {
        Call<MoviesRespon> responseSearch = apiInterface.searchMovies(category, apiKey, lang, query);
        responseSearch.enqueue(new Callback<MoviesRespon>() {
            @Override
            public void onResponse(@NonNull Call<MoviesRespon> call, @NonNull Response<MoviesRespon> response) {
                if (response.body() != null) {
                    data.postValue(response.body().getMoviesList());
                } else {
                    message = "Data Not Found";
                    errorMessage.setValue(message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesRespon> call, @NonNull Throwable t) {
                message = "No Internet";
                errorMessage.setValue(message);
            }
        });
        return data;
    }

    public LiveData<ArrayList<MoviesModel>> requestListMovie(String category, String lang, String apiKey) {
        final Call<MoviesRespon> moviesResponseCall = apiInterface.getMovies(category, apiKey, lang);
        moviesResponseCall.enqueue(new Callback<MoviesRespon>() {
            @Override
            public void onResponse(Call<MoviesRespon> call, Response<MoviesRespon> response) {
                if (response.body() != null) {
                    data.postValue(response.body().getMoviesList());
                } else {
                    message = "Data Not Found";
                    errorMessage.setValue(message);
                }

            }

            @Override
            public void onFailure(Call<MoviesRespon> call, Throwable t) {
                message = "No Internet";
                errorMessage.setValue(message);
            }
        });
        return data;
    }

    public void requestReleaseMovie(String apiKey, String gte, String lte, final Context context) {
        Call<MoviesRespon> moviesResponseCall = apiInterface.releaseMovies(apiKey, gte, lte);
        moviesResponseCall.enqueue(new Callback<MoviesRespon>() {
            @Override
            public void onResponse(@NonNull Call<MoviesRespon> call, @NonNull Response<MoviesRespon> response) {
                if (response.body() != null) {
                    for (MoviesModel movies : response.body().getMoviesList()) {
                        Alarm.showNotification(movies.getTitle(), context, movies.getTitle() + " Release Today !", movies.getId(), String.valueOf(movies.getId()), String.valueOf(movies.getId()), 5);
                    }

                } else {
                    message = "Not Release";
                    errorMessage.setValue(message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesRespon> call, @NonNull Throwable t) {
                message = "No Internet";
                errorMessage.setValue(message);
            }
        });
    }

    public LiveData<String> errorRespon() {
        return errorMessage;
    }
}
