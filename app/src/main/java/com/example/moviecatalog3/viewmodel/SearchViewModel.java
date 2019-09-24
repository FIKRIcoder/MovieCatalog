package com.example.moviecatalog3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalog3.model.MoviesModel;
import com.example.moviecatalog3.remotedata.MovieRepo;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    private LiveData<ArrayList<MoviesModel>> liveDataMovie;
    private MovieRepo moviesRepository;

    public SearchViewModel() {
        moviesRepository = new MovieRepo();
    }

    public void setMovies(String category, String lang, String apiKey, String query) {
        liveDataMovie = moviesRepository.searchMovies(category, lang, apiKey, query);

    }

    public LiveData<ArrayList<MoviesModel>> getMovies() {
        return liveDataMovie;
    }

    public LiveData<String> getError() {
        return moviesRepository.errorRespon();
    }
}
