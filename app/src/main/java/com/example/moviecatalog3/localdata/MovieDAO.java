package com.example.moviecatalog3.localdata;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviecatalog3.model.MoviesModel;

@Dao
public interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(MoviesModel movies);

    @Query("Select * from tb_movie Where id LIKE :search")
    MoviesModel[] checkMovies(int search);

    @Delete
    int deleteMovie(MoviesModel movies);


    @Query("SELECT * From tb_movie Where type LIKE:type")
    MoviesModel[] readWithType(String type);

    @Query("SELECT * From tb_movie Where type LIKE:type")
    Cursor readMovies(String type);

}
