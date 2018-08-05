package com.example.andywelsh.popularmoviesstagetwo.UI;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;
import com.example.andywelsh.popularmoviesstagetwo.Data.MovieRepository;
import com.example.andywelsh.popularmoviesstagetwo.Utilities.MovieApiUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class MovieViewModel extends AndroidViewModel {

    private final MovieRepository mRepository;

    private final LiveData<List<MovieItem>> mAllMovies;

    public MovieViewModel(Application application){
        super(application);
        mRepository = new MovieRepository(application);

        Log.d(TAG, "Receiving data from database");
        mAllMovies = mRepository.getAllMovies();
    }


    public LiveData<List<MovieItem>> getAllMovies() {
        return mAllMovies;
    }

    public boolean doesIdExist(Integer inId) throws ExecutionException, InterruptedException {
        MovieItem fromDB = mRepository.doesIdExist(inId);
        return fromDB != null;
    }

    public void insert(MovieItem movie) {
        mRepository.insertMovie(movie);
    }

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public void killTable() {
//        mRepository.killTable();
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

    public void deleteMovie(Integer inId) {
        mRepository.deleteMovie(inId);
    }

    //querying API movies
    public void getApiMovies(String sortType, MoviesAdapter adapter, List<MovieItem> inMovieItems) {
        MovieApiUtil.loadMovies(adapter, sortType, inMovieItems);
    }

}