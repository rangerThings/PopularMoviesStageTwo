package com.example.andywelsh.popularmoviesstagetwo;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;
import com.example.andywelsh.popularmoviesstagetwo.Data.Model.ReviewItem;
import com.example.andywelsh.popularmoviesstagetwo.Data.Model.VideoItem;
import com.example.andywelsh.popularmoviesstagetwo.UI.MovieViewModel;
import com.example.andywelsh.popularmoviesstagetwo.UI.ReviewsAdapter;
import com.example.andywelsh.popularmoviesstagetwo.UI.TrailersAdapter;
import com.example.andywelsh.popularmoviesstagetwo.Utilities.Constants;
import com.example.andywelsh.popularmoviesstagetwo.Utilities.MovieApiUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_SHORT;


public class DetailActivity extends AppCompatActivity {
    private MovieItem movie;
    private MovieViewModel mMovieViewModel;
    private Boolean movieIsFavorite = false;
    // --Commented out by Inspection (8/4/2018 10:02 PM):private Boolean isOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //using a new instance for detail activity
        //probably could share this
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        //get ids
        TextView titleTv = findViewById(R.id.tv_title);
        TextView overviewTv = findViewById(R.id.tv_overview);
        TextView voteTv = findViewById(R.id.tv_vote_average);
        ImageView posterIv = findViewById(R.id.iv_poster_detail);
        TextView dateTv = findViewById(R.id.tv_date);
        final ImageView favIv = findViewById(R.id.fav_star);

        //get the intent
        Intent inMovie = getIntent();
        movie = inMovie.getParcelableExtra("clickedMovie");

        //check to see if movie exist in favorites database
        try {
            if (mMovieViewModel.doesIdExist(movie.getId())) {
                favIv.setBackgroundResource(R.drawable.ic_star_yellow_24dp);
                movieIsFavorite = true;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Set the UI
        titleTv.setText(movie.getTitle());
        String posterUrl = Constants.POSTER_URL_500 + movie.getPosterPath();
        Picasso.with(this).load(posterUrl).into(posterIv);
        overviewTv.setText(movie.getOverview());
        String voteText = String.valueOf(movie.getVoteAverage()) + getString(R.string.avg_ten);
        voteTv.setText(voteText);
        dateTv.setText(movie.getReleaseDate().substring(0, 4));

        //Set Click Listener on button
        favIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieIsFavorite) {

                    //toast message to user
                    String toastText = movie.getTitle() + " " + (getString(R.string.removed_fav_text));
                    Toast toast = Toast.makeText(DetailActivity.this, toastText, LENGTH_SHORT);
                    toast.show();

                    //change the button text
                    favIv.setBackgroundResource(R.drawable.ic_star_border_black_24dp);

                    //update the variable
                    movieIsFavorite = false;

                    //delete the movie with the current ID
                    mMovieViewModel.deleteMovie(movie.getId());
                } else {

                    String toastText = movie.getTitle() + " " + getString(R.string.add_fav_text);
                    Toast toast = Toast.makeText(DetailActivity.this, toastText, LENGTH_SHORT);
                    toast.show();

                    favIv.setBackgroundResource(R.drawable.ic_star_yellow_24dp);

                    movieIsFavorite = true;

                    //insert movie in database
                    mMovieViewModel.insert(movie);
                }
            }
        });


        //Trailers
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_Trailers);
        TrailersAdapter adapter = new TrailersAdapter(getApplicationContext(), new ArrayList<VideoItem>(0));
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        recyclerView.setAdapter(adapter);

        //Query the Api  and update adapter
        MovieApiUtil.loadTrailers(movie.getId(), adapter);

        //Reviews
        // set up the RecyclerView
        RecyclerView reviewRv = findViewById(R.id.rv_Reviews);
        ReviewsAdapter reviewAdapter = new ReviewsAdapter(getApplicationContext(), new ArrayList<ReviewItem>(0));
        reviewRv.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        reviewRv.setAdapter(reviewAdapter);

        //Query the Api  and update adapter
        MovieApiUtil.loadReviews(reviewAdapter, movie.getId());
    }

}
