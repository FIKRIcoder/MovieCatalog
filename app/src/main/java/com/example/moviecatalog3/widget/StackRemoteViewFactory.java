package com.example.moviecatalog3.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.moviecatalog3.R;
import com.example.moviecatalog3.localdata.MovieDatabase;
import com.example.moviecatalog3.model.MoviesModel;
import com.example.moviecatalog3.remotedata.Constanta;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ArrayList<Bitmap> widgetBitmap = new ArrayList<>();

    StackRemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        MovieDatabase dbMovie = Room.databaseBuilder(context, MovieDatabase.class, "MovieDb").build();
        MoviesModel[] movies = dbMovie.movieDAO().readWithType("Movie");
        widgetBitmap.clear();
        for (MoviesModel m : movies) {
            try {
                Bitmap bitmap = Glide.with(context).asBitmap().load(Constanta.BASE_URL_POSTER + m.getImg()).submit().get();
                widgetBitmap.add(bitmap);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetBitmap.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_items);
        remoteViews.setImageViewBitmap(R.id.imageView, widgetBitmap.get(i));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
