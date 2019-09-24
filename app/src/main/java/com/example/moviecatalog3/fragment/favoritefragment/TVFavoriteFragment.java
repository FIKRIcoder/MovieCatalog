package com.example.moviecatalog3.fragment.favoritefragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalog3.R;
import com.example.moviecatalog3.adapter.MoviesAdapter;
import com.example.moviecatalog3.localdata.AsyncOperation;
import com.example.moviecatalog3.localdata.AsyncTaskLocalData;
import com.example.moviecatalog3.localdata.MovieDatabase;
import com.example.moviecatalog3.model.MoviesModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TVFavoriteFragment extends Fragment implements AsyncTaskLocalData {

    private MovieDatabase dbMovie;
    private RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        if (this.getContext() != null) {
            dbMovie = Room.databaseBuilder(this.getContext(), MovieDatabase.class, "MovieDb").build();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        AsyncOperation asyncOperation = new AsyncOperation(this, dbMovie, null);
        String[] params = {"read", null,"TV"};
        asyncOperation.execute(params);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvfavorite, container, false);
    }

    @Override
    public void onPostExecute(MoviesModel[] movies) {
        List<MoviesModel> listMovies = new ArrayList<>(Arrays.asList(movies));
        MoviesAdapter moviesAdapter = new MoviesAdapter(listMovies, this.getContext(), "Movie");
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setHasFixedSize(true);
    }
}
