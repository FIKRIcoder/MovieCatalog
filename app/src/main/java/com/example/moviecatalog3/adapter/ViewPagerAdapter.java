package com.example.moviecatalog3.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
        private List<Fragment> listMenu;
        private List<String> listTitle;

        public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> listMenu, List<String> title) {
            super(fm);
            this.listMenu = listMenu;
            this.listTitle = title;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return listMenu.get(position);
        }

        @Override
        public int getCount() {
            return listMenu.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position);
        }
}


