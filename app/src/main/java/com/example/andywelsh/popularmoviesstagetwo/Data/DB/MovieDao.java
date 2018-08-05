package com.example.andywelsh.popularmoviesstagetwo.Data.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;

import java.util.List;

//From Google "Room With a View"
    /*In the DAO (data access object), you specify SQL queries and associate them with method calls.
The compiler checks the SQL and generates queries from convenience annotations for common queries, such as @Insert.
The DAO must be an interface or abstract class.
By default, all queries must be executed on a separate thread.
Room uses the DAO to create a clean API for your code.*/

@Dao
public interface MovieDao {

    //insert a single movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieItem movie);

    //delete a single movie with the in Id
    @Query("DELETE FROM movie_table WHERE id = :inId")
    void deleteMovie(Integer inId);

    //return all of the movies
    @Query("SELECT * from movie_table ORDER BY Title ASC")
    LiveData<List<MovieItem>> getAllMovies();

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    //delete all of the movies in the table
//    @Query("DELETE FROM movie_table")
//    void killTable();
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

    //get a specific movie
    @Query("SELECT * from movie_table WHERE id = :inId")
    MovieItem doesIdExist(Integer inId);

}

