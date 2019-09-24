package com.example.moviecatalog3.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviecatalog3.Detail;
import com.example.moviecatalog3.R;
import com.example.moviecatalog3.model.MoviesModel;
import com.example.moviecatalog3.remotedata.Constanta;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<MoviesModel> listMovies;
    private Context context;
    private String type;

    public MoviesAdapter(List<MoviesModel> listMovies, Context context, String type) {
        this.listMovies = listMovies;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.movies_row, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MoviesModel moviesModel = listMovies.get(position);
        Glide.with(context).load(Constanta.BASE_URL_IMG.concat(moviesModel.getImg())).into(holder.imgMovie);
        holder.txtDesc.setText(moviesModel.getDescription());
        if (moviesModel.getDate() != null) {
            holder.txtDate.setText(moviesModel.getDate());
        } else {
            holder.txtDate.setText(moviesModel.getTv_date());
        }
        if (moviesModel.getTitle() != null) {
            holder.txtTitle.setText(moviesModel.getTitle());
        } else {
            holder.txtTitle.setText(moviesModel.getTv_title());
        }
        holder.cardView.setOnClickListener(view -> {
            Intent gotoDetails = new Intent(context, Detail.class);
            gotoDetails.putExtra("type",type);
            gotoDetails.putExtra("movies", moviesModel);
            context.startActivity(gotoDetails);
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMovie;
        TextView txtTitle;
        TextView txtDate;
        TextView txtDesc;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            cardView = itemView.findViewById(R.id.card_view);
        }

    }
}
