package com.example.andywelsh.popularmoviesstagetwo.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;
import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieResponse;
import com.example.andywelsh.popularmoviesstagetwo.Data.Model.ReviewResponse;
import com.example.andywelsh.popularmoviesstagetwo.Data.Model.VideoResponse;

import com.example.andywelsh.popularmoviesstagetwo.UI.MoviesAdapter;
import com.example.andywelsh.popularmoviesstagetwo.UI.ReviewsAdapter;
import com.example.andywelsh.popularmoviesstagetwo.UI.TrailersAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MovieApiUtil {

    private static MovieInterface mService;
    private static Retrofit retrofit = null;

    //Singleton pattern for retrofit client, create instance if one doesn't exists
    //Hooked by getMovieService
    private static Retrofit getClient(String baseUrl) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //Returns movie service with defined interface
    private static MovieInterface getMovieService() {
        return getClient(Constants.MOVIES_BASE_URL).create(MovieInterface.class);

    }

    //The request to Movie API services
    interface MovieInterface {
        @GET("/3/movie/{sort_type}")
        Call<MovieResponse> getAnswers(@Path("sort_type") String sort_type, @Query("api_key") String api_key);

        @GET("3/movie/{movie_id}/reviews")
        Call<ReviewResponse> getReview(@Path("movie_id") Integer movieId, @Query("api_key") String api_key);

        //For Trailers
        @GET("3/movie/{movie_id}/videos")
        Call<VideoResponse> getTrailer(@Path("movie_id") Integer movieId, @Query("api_key") String api_key);

    }

    //BLOCK OF ASYNC METHODS
    //Redundant, but probably not worth building a generic method for

    //Actual async call to get results from the movie api or local database
    //
    public static void loadMovies(final MoviesAdapter adapter, final String sort_type, List<MovieItem> inMovieItems) {

        //sort_type is a favorite, no need to pull from API
        if (sort_type.equals(Constants.SORT_FAVS)) {

            adapter.updateMovies(inMovieItems);
        } else {//sort type is not a favorite
            mService = getMovieService();
            mService.getAnswers(sort_type, Constants.API_KEY).enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                    if (response.isSuccessful()) {
                        Log.d("Movies API Util", "posts loaded from API");
                        List<MovieItem>
                                movieQuery = (response.body()).getResults();
                        adapter.updateMovies(movieQuery);


                    } else {
                        int statusCode = response.code();
                        Log.d("Failed Status Code", String.valueOf(statusCode));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Log.d("MainActivity", "error loading from API");

                }

            });
        }
    }


    //query the trailer endpoint
    public static void loadTrailers(Integer movieId, final TrailersAdapter adapter) {

        mService = getMovieService();
        mService.getTrailer(movieId, Constants.API_KEY).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoResponse> call, @NonNull Response<VideoResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("Trailers Api:", "trailers loaded from API");
                    adapter.updateTrailers(response.body().getResults());

                } else {
                    int statusCode = response.code();
                    Log.d("Status Code", String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoResponse> call, @NonNull Throwable t) {
                Log.d("Trailers Api:", "error loading from API");
            }
        });
    }

    //hit the review endpoint
    public static void loadReviews(final ReviewsAdapter adapter, Integer movieId) {

        mService = getMovieService();
        mService.getReview(movieId, Constants.API_KEY).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("Reviews API Util", "reviews loaded from API");
                    adapter.updateReviews(response.body().getResults());

                } else {
                    int statusCode = response.code();
                    Log.d("Failed Status Code", String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                Log.d("Reviews API:", "error loading from API");

            }

        });
    }
    //checks network connection, does not specifically see if there is active internet (no ping)
    //Simplest answer from https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    //Slightly modified

    public static Boolean isOnline(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}