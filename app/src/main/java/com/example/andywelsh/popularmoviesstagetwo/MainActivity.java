package com.example.andywelsh.popularmoviesstagetwo;


import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;
import com.example.andywelsh.popularmoviesstagetwo.UI.MovieViewModel;
import com.example.andywelsh.popularmoviesstagetwo.UI.MoviesAdapter;
import com.example.andywelsh.popularmoviesstagetwo.Utilities.Constants;
import com.example.andywelsh.popularmoviesstagetwo.Utilities.MovieApiUtil;

import java.util.ArrayList;
import java.util.List;

//Popular Movies App Two for Udacity Nanodegree
//API Key must be updated in Constants for the application to work.

public class MainActivity extends AppCompatActivity {

    private MovieViewModel mMainViewModel;
    private MoviesAdapter adapter;
    private List<MovieItem> favMovies;
    private String sortPref;
    private SharedPreferences prefs;
    private Boolean isOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the application for  viewmodel
        Application application = getApplication();

        //get shared preferences
        prefs = this.getSharedPreferences(getString(R.string.pref_sort), Context.MODE_PRIVATE);
        sortPref = prefs.getString(getString(R.string.pref_sort), Constants.DEFAULT_SORT);
        String sortText = prefs.getString(getString(R.string.pref_text), Constants.DEFAULT_SORT_MESSAGE);
        this.setTitle(sortText);

        //get recyclerview id and create an adapter with an empty array
        RecyclerView recyclerView = findViewById(R.id.rv_Movies);
        adapter = new MoviesAdapter(application, new ArrayList<MovieItem>(0));

        isOnline = MovieApiUtil.isOnline(this);
        //if the device is not connected to the network
        if (!isOnline) {
            //change the title
            this.setTitle(getString(R.string.offline_mode));
        }

        //create a viewmodel instance
        mMainViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMainViewModel.getAllMovies().observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(@Nullable List<MovieItem> movieItems) {

                favMovies = movieItems;
                if (sortPref.equals(Constants.SORT_FAVS)) {
                    Log.d("Live Data:", "OnChange triggered.");
                    adapter.updateMovies(movieItems);
                }
            }
        });


        // set up the rest of the RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, Constants.COLUMNS));
        recyclerView.setAdapter(adapter);

        //Async Movie API request in MovieApiUtil, requires interface and adapter
        //Adapter updates after request is successful
        //Uses shared preference sortPref
        //Passes in cached movie for current sort
        mMainViewModel.getApiMovies(sortPref, adapter, favMovies);
    }

    //create and inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem popSpinner = menu.findItem(R.id.dropdown_highest);
        MenuItem topSpinner = menu.findItem(R.id.dropdown_popular);

        //hide the pop and top options if the device is not online
        if (!isOnline){
        //hide the other buttons
        popSpinner.setVisible(false);
        topSpinner.setVisible(false);}

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        SharedPreferences.Editor editor = prefs.edit();
        switch (itemThatWasClickedId) {
            case (R.id.dropdown_highest):
                //change sort references so main screen loads this preference next time
                editor.putString(getString(R.string.pref_sort), Constants.SORT_RATED);
                editor.putString(getString(R.string.pref_text), Constants.TOP_RATED_MESSAGE);
                MainActivity.this.setTitle(Constants.TOP_RATED_MESSAGE);
                editor.apply();

                sortPref = prefs.getString(getString(R.string.pref_sort), Constants.SORT_RATED);
                Log.d("Sort TopRated Menu:", sortPref);

                mMainViewModel.getApiMovies(sortPref, adapter, favMovies);

                return true;

            case (R.id.dropdown_popular):
                //change sort references so main screen loads this preference next time
                editor.putString(getString(R.string.pref_sort), Constants.SORT_POPULAR);
                editor.putString(getString(R.string.pref_text), Constants.POPULAR_SORT_MESSAGE);
                MainActivity.this.setTitle(Constants.POPULAR_SORT_MESSAGE);
                editor.apply();

                sortPref = prefs.getString(getString(R.string.pref_sort), Constants.SORT_POPULAR);
                Log.d("Sort Popular Menu:", sortPref);

                mMainViewModel.getApiMovies(sortPref, adapter, favMovies);


                return true;
            case (R.id.dropdown_favorites):
                //change sort references so main screen loads this preference next time
                editor.putString(getString(R.string.pref_sort), Constants.SORT_FAVS);
                editor.putString(getString(R.string.pref_text), Constants.FAV_SORT_MESSAGE);
                MainActivity.this.setTitle(Constants.FAV_SORT_MESSAGE);
                editor.apply();

                sortPref = prefs.getString(getString(R.string.pref_sort), Constants.SORT_FAVS);
                Log.d("Sort Fav Menu:", sortPref);


                mMainViewModel.getApiMovies(sortPref, adapter, favMovies);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

}
