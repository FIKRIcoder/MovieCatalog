package com.example.moviecatalog3.localdata;

import com.example.moviecatalog3.model.MoviesModel;

public interface AsyncTaskLocalData {

    void onPostExecute(MoviesModel[] object);
}
