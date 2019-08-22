package com.abedafnan.popularmovies;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRecycler.findViewById(R.id.recycler_movies);

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
                    // TODO: sort by Top Rated
                } else {
                    // TODO: sort by Most Popular
                }
            }
        });
        builder.show();
    }
}
