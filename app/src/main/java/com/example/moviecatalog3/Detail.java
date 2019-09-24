package com.example.moviecatalog3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.moviecatalog3.localdata.AsyncOperation;
import com.example.moviecatalog3.localdata.AsyncTaskLocalData;
import com.example.moviecatalog3.localdata.MovieDatabase;
import com.example.moviecatalog3.model.MoviesModel;
import com.example.moviecatalog3.remotedata.Constanta;

public class Detail extends AppCompatActivity implements AsyncTaskLocalData {


    private String[] params;
    private MovieDatabase dbMovie;
    private MoviesModel mv;
    TextView txtKet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView imgMovie = findViewById(R.id.img_movie);
        TextView txtTitle = findViewById(R.id.txt_title);
        TextView txtDes = findViewById(R.id.txt_desc);
        TextView txtDate = findViewById(R.id.txt_date);
        dbMovie = Room.databaseBuilder(this.getApplicationContext(), MovieDatabase.class, "MovieDb").build();
        Intent i = getIntent();
        String title, date;
        mv = i.getParcelableExtra("movies");
        String type = i.getStringExtra("type");
        mv.setType(type);
        if (mv != null) {
            Glide.with(this).load(Constanta.BASE_URL_IMG + mv.getImg()).into(imgMovie);
            if (mv.getTitle() != null) {
                title = mv.getTitle();
            } else {
                title = mv.getTv_title();
            }
            txtTitle.setText(title);
            txtDes.setText(mv.getDescription());
            date = mv.getDate() != null ? mv.getDate() : mv.getTv_date();
            txtDate.setText(date);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        CardView btnSuka = findViewById(R.id.btnSuka);
        txtKet = findViewById(R.id.txt_ket);
        btnSuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtKet.getText().toString().toLowerCase().equals(getString(R.string.like).toLowerCase())) {
                    new AsyncOperation(Detail.this, dbMovie, mv).execute(new String[]{"insert", ""});
                    txtKet.setText(getResources().getString(R.string.unlike));
                } else {
                    new AsyncOperation(Detail.this, dbMovie, mv).execute(new String[]{"delete", ""});
                    txtKet.setText(getResources().getString(R.string.like));
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        params = new String[]{"check", String.valueOf(mv.getId())};
        new AsyncOperation(this, dbMovie, mv).execute(params);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onPostExecute(MoviesModel[] object) {
        if (object != null) {
            if (object.length > 0) {
                txtKet.setText(R.string.unlike);
            }
        }
    }
}

