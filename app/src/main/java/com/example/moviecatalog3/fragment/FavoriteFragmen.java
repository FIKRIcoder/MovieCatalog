package com.example.moviecatalog3.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalog3.R;
import com.example.moviecatalog3.adapter.ViewPagerAdapter;
import com.example.moviecatalog3.fragment.favoritefragment.MoviesFavoriteFragment;
import com.example.moviecatalog3.fragment.favoritefragment.TVFavoriteFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteFragmen extends Fragment  {
    private String[] title;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        setResource();
        if (title.length > 0 && setFragment().size() > 0) {
            if (getFragmentManager() != null) {
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), setFragment(), setTitle());
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        }
    }


    private List<String> setTitle() {
        return new ArrayList<>(Arrays.asList(title));
    }

    private void setResource() {
        title = getResources().getStringArray(R.array.title_tab);
    }

    private List<Fragment> setFragment() {
        List<Fragment> listFrag = new ArrayList<>();
        listFrag.add(new MoviesFavoriteFragment());
        listFrag.add(new TVFavoriteFragment());
        return listFrag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

}
