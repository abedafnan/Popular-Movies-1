package com.abedafnan.popularmovies;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abedafnan.popularmovies.adapters.MoviesAdapter;
import com.abedafnan.popularmovies.models.Movie;
import com.abedafnan.popularmovies.models.MovieResponse;
import com.abedafnan.popularmovies.utils.GetDataInterface;
import com.abedafnan.popularmovies.utils.NetworkUtils;
import com.abedafnan.popularmovies.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMoviesRecycler;
    private ProgressBar mProgressBar;
    private MoviesAdapter mAdapter;

    public static final String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Load popular movies
        showPopularMovies();
    }

    private void showPopularMovies() {
        // Show the progressbar while waiting for data to load
        mProgressBar = findViewById(R.id.progressbar);
        mProgressBar.setVisibility(View.VISIBLE);

        if (NetworkUtils.hasNetworkConnection(this)) {
            GetDataInterface service = RetrofitClient.getRetrofitInstance().create(GetDataInterface.class);
            Call<MovieResponse> call = service.getPopularMovies(API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    mProgressBar.setVisibility(View.GONE);
                    MovieResponse popularMoviesResponse = response.body();
                    if (popularMoviesResponse != null) {
                        generateMovieList(popularMoviesResponse.getMovies());
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    mProgressBar.setVisibility(View.GONE);
                    Log.e("onFailure", t.toString());
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showTopRatedMovies() {
        // Show the progressbar while waiting for data to load
        mProgressBar = findViewById(R.id.progressbar);
        mProgressBar.setVisibility(View.VISIBLE);

        if (NetworkUtils.hasNetworkConnection(this)) {
            GetDataInterface service = RetrofitClient.getRetrofitInstance().create(GetDataInterface.class);
            Call<MovieResponse> call = service.getTopRatedMovies(API_KEY);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    mProgressBar.setVisibility(View.GONE);

                    MovieResponse popularMoviesResponse = response.body();
                    if (popularMoviesResponse != null) {
                        generateMovieList(popularMoviesResponse.getMovies());
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    mProgressBar.setVisibility(View.GONE);
                    Log.e("onFailure", t.toString());
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void generateMovieList(List<Movie> movies) {
        mMoviesRecycler = findViewById(R.id.recycler_movies);
        mMoviesRecycler.setHasFixedSize(true);
        // Set the adapter
        MoviesAdapter adapter = new MoviesAdapter(movies);
        mMoviesRecycler.setAdapter(adapter);
        // Set the layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(
                MainActivity.this, 2, GridLayoutManager.VERTICAL, true);
        mMoviesRecycler.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            sort();
            return true;
        }
        return false;
    }

    // Display a dialog to sort movies by most popular or top rated
    public void sort() {
        CharSequence[] sorting = {"Most Popular", "Top Rated"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort By:");
        builder.setItems(sorting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    showTopRatedMovies();
                } else {
                    showPopularMovies();
                }
            }
        });
        builder.show();
    }
}
