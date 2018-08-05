package com.example.andywelsh.popularmoviesstagetwo.UI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;
import com.example.andywelsh.popularmoviesstagetwo.DetailActivity;
import com.example.andywelsh.popularmoviesstagetwo.R;
import com.example.andywelsh.popularmoviesstagetwo.Utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private List<MovieItem> mMovies; // Cached copy of words
    private final Context mContext;

    //constructor takes context and movie array passed in from MainActivity
    public MoviesAdapter(Context context, List<MovieItem> moviesList) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mMovies = moviesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView posterView;

        private ViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.iv_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //What happens when clicked?
            onItemClick(getAdapterPosition());
            notifyDataSetChanged();


        }
    }

    //method that does something when user clicks a single item
    private void onItemClick(int position) {

        Intent detailIntent = new Intent(mContext, DetailActivity.class);
        detailIntent.putExtra("clickedMovie", mMovies.get(position));
        mContext.startActivity(detailIntent);


        /*//Toast Debugging
        String toastText = "Item Clicked: " + String.valueOf(position);
        Toast toast = Toast.makeText(mContext, toastText, LENGTH_SHORT);
        toast.show();*/
    }


    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.movies_grid_item, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem current = mMovies.get(position);
        String posterUrl = Constants.POSTER_URL_500 + "/" + current.getPosterPath();
        Picasso.with(mContext).load(posterUrl).into(holder.posterView);
    }

    public void updateMovies(List<MovieItem> movies) {
        mMovies= movies;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mMovies != null)
            return mMovies.size();
        else return 0;
    }
}
