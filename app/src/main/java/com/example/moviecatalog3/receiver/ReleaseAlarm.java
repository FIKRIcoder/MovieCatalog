package com.example.moviecatalog3.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.moviecatalog3.remotedata.Constanta;
import com.example.moviecatalog3.remotedata.MovieRepo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReleaseAlarm extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        @SuppressLint("SimpleDateFormat") String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        MovieRepo moviesRepository = new MovieRepo();
        moviesRepository.requestReleaseMovie(Constanta.API_KEY, strDate, strDate, context);
    }
}
