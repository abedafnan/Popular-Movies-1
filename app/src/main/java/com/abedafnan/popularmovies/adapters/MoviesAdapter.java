package com.abedafnan.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abedafnan.popularmovies.R;
import com.abedafnan.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> mMoviesList;

    public MoviesAdapter(List<Movie> movies) {
        mMoviesList = movies;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_movie, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        Movie currentMovie = mMoviesList.get(i);

        String posterUrl = "http://image.tmdb.org/t/p/w185/" + currentMovie.getPosterPath();
        Picasso.get().load(posterUrl).into(moviesViewHolder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImageView;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.imageView_poster);
        }
    }
}
