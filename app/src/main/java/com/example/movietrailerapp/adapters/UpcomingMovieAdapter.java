package com.example.movietrailerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietrailerapp.ui.movieInfo.MovieDesc;
import com.example.movietrailerapp.R;
import com.example.movietrailerapp.model.MovieEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingMovieViewHolder> {
    ArrayList<MovieEntity> movieEntities;
    Context context;

    public UpcomingMovieAdapter(ArrayList<MovieEntity> movieEntities, Context context) {
        this.movieEntities = movieEntities;
        this.context = context;
    }

    @NonNull
    @Override
    public UpcomingMovieAdapter.UpcomingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.movie_card,parent,false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new UpcomingMovieAdapter.UpcomingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMovieAdapter.UpcomingMovieViewHolder holder, int position) {
        final MovieEntity movieEntity = movieEntities.get(position);
        Picasso.with(context).load(movieEntity.getPosterImagePath()).into(holder.movieImg);
        if(movieEntity.getOriginalTitle().length() > 15)
            holder.movieName.setTextSize(10.0f);
        else
            holder.movieName.setTextSize(15.0f);
        holder.movieName.setText(movieEntity.getOriginalTitle());
        holder.movieRating.setRating(movieEntity.getRating());
        holder.releaseDate.setText(movieEntity.getReleaseDate());
        holder.movieImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDesc.class);
                intent.putExtra("backdrop_url",movieEntity.getBackdropImagePath());
                intent.putExtra("poster_url",movieEntity.getPosterImagePath());
                intent.putExtra("overview_txt",movieEntity.getMovieOverview());
                intent.putExtra("rating",movieEntity.getRating());
                intent.putExtra("movie_title",movieEntity.getOriginalTitle());
                intent.putExtra("movie_id",movieEntity.getMovieId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieEntities.size();
    }

    public static class UpcomingMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImg;
        TextView movieName,releaseDate;
        RatingBar movieRating;
        public UpcomingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.card_movie_img);
            movieName = itemView.findViewById(R.id.card_movie_name);
            movieRating = itemView.findViewById(R.id.card_movie_rating);
            releaseDate = itemView.findViewById(R.id.card_movie_release_date);
        }
    }
}
