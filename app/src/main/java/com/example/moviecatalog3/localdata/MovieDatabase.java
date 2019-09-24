package com.example.moviecatalog3.localdata;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.moviecatalog3.model.MoviesModel;

@Database(entities = {MoviesModel.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDAO movieDAO();

}
