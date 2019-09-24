package com.example.moviecatalog3;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalog3.adapter.MoviesAdapter;
import com.example.moviecatalog3.fragment.FavoriteFragmen;
import com.example.moviecatalog3.fragment.MoviesFragment;
import com.example.moviecatalog3.fragment.MoviesTVFragment;
import com.example.moviecatalog3.localdata.SharedPreference;
import com.example.moviecatalog3.model.MoviesModel;
import com.example.moviecatalog3.remotedata.Constanta;
import com.example.moviecatalog3.viewmodel.SearchViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SharedPreference sharedPreference;
    FrameLayout containerFragment;
    BottomNavigationView bottomNavigationView;

    private String lang;

    final Fragment fragMovie = new MoviesFragment();
    final Fragment fragmentMovieTv = new MoviesTVFragment();
    final Fragment fragmentFavorite = new FavoriteFragmen();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragMovie;
    private ProgressBar progressBar;
    private SearchViewModel searchViewModel;
    private TextView txtMessage;
    private RecyclerView resultSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference(this);
        setLanguage(sharedPreference.getReferences("lang2"));
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        containerFragment = findViewById(R.id.container);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        progressBar = findViewById(R.id.progressBar);
        resultSearch = findViewById(R.id.resultSearch);
        txtMessage = findViewById(R.id.message);
        lang = sharedPreference.getReferences("lang2");
        if (sharedPreference.getReferences("lang").isEmpty() && sharedPreference.getReferences("lang2").isEmpty()) {
            sharedPreference.setPref("en-US", "en");
        }
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        if (savedInstanceState != null) {
            switch (savedInstanceState.getInt("fragState")) {
                case R.id.tabMovie:
                    active = fragMovie;
                    fm.beginTransaction().add(R.id.container, fragmentFavorite, fragmentFavorite.getClass().getSimpleName()).hide(fragmentFavorite).commit();
                    fm.beginTransaction().add(R.id.container, fragmentMovieTv, fragmentMovieTv.getClass().getSimpleName()).hide(fragmentMovieTv).commit();
                    break;
                case R.id.tabTv:
                    active = fragmentMovieTv;
                    fm.beginTransaction().add(R.id.container, fragMovie, fragMovie.getClass().getSimpleName()).hide(fragMovie).commit();
                    fm.beginTransaction().add(R.id.container, fragmentFavorite, fragmentFavorite.getClass().getSimpleName()).hide(fragmentFavorite).commit();
                    break;
                case R.id.favorite:
                    active = fragmentFavorite;
                    fm.beginTransaction().add(R.id.container, fragMovie, fragMovie.getClass().getSimpleName()).hide(fragMovie).commit();
                    fm.beginTransaction().add(R.id.container, fragmentMovieTv, fragmentMovieTv.getClass().getSimpleName()).hide(fragmentMovieTv).commit();
                    break;
            }
            fm.beginTransaction().add(R.id.container, active, active.getClass().getSimpleName()).commit();
        } else {
            fm.beginTransaction().add(R.id.container, fragmentFavorite, fragmentFavorite.getClass().getSimpleName()).hide(fragmentFavorite).commit();
            fm.beginTransaction().add(R.id.container, fragmentMovieTv, fragmentMovieTv.getClass().getSimpleName()).hide(fragmentMovieTv).commit();
            fm.beginTransaction().add(R.id.container, fragMovie, fragMovie.getClass().getSimpleName()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tabMovie:
                        fm.beginTransaction().hide(active).show(fragMovie).commit();
                        active = fragMovie;
                        return true;
                    case R.id.tabTv:
                        fm.beginTransaction().hide(active).show(fragmentMovieTv).commit();
                        active = fragmentMovieTv;
                        return true;
                    case R.id.favorite:
                        fm.beginTransaction().hide(active).show(fragmentFavorite).commit();
                        active = fragmentFavorite;
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("fragState", bottomNavigationView.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.language) {
            Intent gotoLang = new Intent(this, Language.class);
            startActivity(gotoLang);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!lang.equals(sharedPreference.getReferences("lang2"))) {
            for (Fragment fragment : fm.getFragments()) {
                fm.beginTransaction().remove(fragment).commit();
            }
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (!query.isEmpty()) {
                        String category = null;
                        if (active == fragMovie) {
                            category = "movie";
                        }
                        if (active == fragmentMovieTv) {
                            category = "tv";
                        }
                        searchViewModel.setMovies(category, sharedPreference.getReferences("lang"), Constanta.API_KEY, query);
                        searchViewModel.getMovies().observe(MainActivity.this, getSearch);
                        progressBar.setVisibility(View.VISIBLE);
                        txtMessage.setVisibility(View.GONE);
                    }
                    return true;
                }


                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty()) {
                        containerFragment.setVisibility(View.VISIBLE);
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        resultSearch.setVisibility(View.GONE);
                        txtMessage.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        resultSearch.setAdapter(null);
                        containerFragment.setVisibility(View.GONE);
                        bottomNavigationView.setVisibility(View.GONE);
                        resultSearch.setVisibility(View.VISIBLE);
                        txtMessage.setVisibility(View.GONE);
                    }
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    private Observer<ArrayList<MoviesModel>> getSearch = new Observer<ArrayList<MoviesModel>>() {
        @Override
        public void onChanged(ArrayList<MoviesModel> movies) {
            if (movies.size() > 0) {
                MoviesAdapter recycleMoviesAdapter = new MoviesAdapter(movies, MainActivity.this, "Movie");
                RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
                resultSearch.setLayoutManager(lm);
                resultSearch.setAdapter(recycleMoviesAdapter);
                recycleMoviesAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                txtMessage.setText("Data Not Found");
                txtMessage.setVisibility(View.VISIBLE);
            }
        }
    };

    public void setLanguage(String language) {
        Locale locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration configuration = res.getConfiguration();
        configuration.locale = locale;
        res.updateConfiguration(configuration, dm);
    }
}
