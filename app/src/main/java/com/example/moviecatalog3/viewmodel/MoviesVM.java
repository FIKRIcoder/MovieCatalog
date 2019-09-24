package com.example.moviecatalog3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalog3.model.MoviesModel;
import com.example.moviecatalog3.model.MoviesRespon;
import com.example.moviecatalog3.remotedata.MovieRepo;

import java.util.ArrayList;

public class MoviesVM extends ViewModel {
    private LiveData<ArrayList<MoviesModel>> moviesLiveData;
    private MovieRepo moviesRepository;

    public MoviesVM() {
        moviesRepository = new MovieRepo();
    }

    public void setMovies(String category, String lang, String apiKey) {
        moviesLiveData = moviesRepository.requestListMovie(category, lang, apiKey );
    }

    public LiveData<ArrayList<MoviesModel>> getMovies() {
        return moviesLiveData;
    }

    public LiveData<String> getError() {
        return moviesRepository.errorRespon();
    }
}
