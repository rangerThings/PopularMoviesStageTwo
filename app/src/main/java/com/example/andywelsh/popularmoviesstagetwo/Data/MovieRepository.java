package com.example.andywelsh.popularmoviesstagetwo.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.andywelsh.popularmoviesstagetwo.Data.DB.MovieDao;
import com.example.andywelsh.popularmoviesstagetwo.Data.DB.MovieRoomDatabase;
import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieRepository {

    private final MovieDao mMovieDao;
    private final LiveData<List<MovieItem>> mAllMovies;

    // --Commented out by Inspection (8/4/2018 10:02 PM):private List<MovieItem> apiMoviesList;


    public MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    //bulk inserts all movies in the api list
//    //Used for debugging, left in for future projects
//    public void bulkInsert(List<MovieItem> movies){
//        for (MovieItem movie: movies){
//            insertMovie(movie);
//        }
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)


// --Commented out by Inspection START (8/4/2018 10:07 PM):
//    //deletes all entries
//    public void killTable(){
//        new deleteAsyncTask(mMovieDao).execute();
//    }
// --Commented out by Inspection STOP (8/4/2018 10:07 PM)

    //delete single movie
    public void deleteMovie(Integer inId){
        new deleteAsyncTask(mMovieDao).execute(inId);
    }

  //pulling the movies from the db
    public LiveData<List<MovieItem>> getAllMovies(){
        return mAllMovies;
    }

    //checks to see if idExists
    public MovieItem doesIdExist(Integer inId) throws ExecutionException, InterruptedException {
        return new queryAsyncTask(mMovieDao).execute(inId).get();
    }

    //insert single movie into database
    public void insertMovie(MovieItem movie) {
        new insertAsyncTask(mMovieDao).execute(movie);
    }



    //use async to retrieve movie from database, won't block ui thread
    private static class queryAsyncTask extends android.os.AsyncTask<Integer, Void, MovieItem> {

        private final MovieDao mAsyncTaskDao;

        queryAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected MovieItem doInBackground(final Integer... params) {
            return mAsyncTaskDao.doesIdExist(params[0]);
        }
    }

    //use async to insert movie into database, won't block ui thread
    private static class insertAsyncTask extends android.os.AsyncTask<MovieItem, Void, Void> {

        private final MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MovieItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //use async to delete movie into database, won't block ui thread
    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final MovieDao mAsyncTaskDao;

        deleteAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            mAsyncTaskDao.deleteMovie(params[0]);
            return null;
        }
    }

}

