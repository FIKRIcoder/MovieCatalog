package com.example.moviecatalog3.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesRespon {
    @SerializedName("results")
    private ArrayList<MoviesModel> moviesList;


    public MoviesRespon(ArrayList<MoviesModel> moviesList) {
        this.moviesList = moviesList;
    }

    public ArrayList<MoviesModel> getMoviesList() {
        return moviesList;
    }
}

