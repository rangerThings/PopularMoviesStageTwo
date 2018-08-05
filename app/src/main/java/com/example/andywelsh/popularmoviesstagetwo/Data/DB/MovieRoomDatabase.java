package com.example.andywelsh.popularmoviesstagetwo.Data.DB;

//From Google "Room With a View" codelab
/*
Room is a database layer on top of an SQLite database. Room takes care of mundane tasks
that you used to handle with an SQLiteOpenHelper.
*/

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;

@Database(entities = {MovieItem.class}, version =1)
public abstract class MovieRoomDatabase extends RoomDatabase {

    //create the dao
    public abstract MovieDao movieDao();

    //get an instance of the database if it exists, or create it
    private static MovieRoomDatabase INSTANCE;

    //Singleton so only one instance of database exists
    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}