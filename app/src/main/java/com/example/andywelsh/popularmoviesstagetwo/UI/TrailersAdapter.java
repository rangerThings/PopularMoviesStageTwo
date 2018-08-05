package com.example.andywelsh.popularmoviesstagetwo.UI;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.VideoItem;
import com.example.andywelsh.popularmoviesstagetwo.R;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    private final LayoutInflater mInflater;
    private List<VideoItem> mTrailers; // Cached copy of trailers
    private final Context mContext;


    public TrailersAdapter(Context context, List<VideoItem> trailerList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mTrailers = trailerList;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView trailerView;

        private TrailerViewHolder(View itemView) {
            super(itemView);
            trailerView = itemView.findViewById(R.id.tv_trailerLink);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            //What happens when clicked?
            onItemClick(getAdapterPosition());

        }
    }


    //method that does something when user clicks a single item
    private void onItemClick(int position) {

        VideoItem current = mTrailers.get(position);

     /*   //Toast Debugging
        String toastText = "Item Clicked: " + String.valueOf(position) + " " + current.getSource();
        Log.d("trailer adapter", toastText);
        Toast toast = Toast.makeText(mContext, toastText, LENGTH_SHORT);
        toast.show();*/

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + current.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + current.getKey()));
        try {
            mContext.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            mContext.startActivity(webIntent);
        }


    }



    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.trailers_rv_item, parent, false);
        return new TrailerViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        VideoItem current = mTrailers.get(position);
        holder.trailerView.setText(current.getName());

    }

    public void updateTrailers(List<VideoItem> trailers) {
        mTrailers= trailers;
        notifyDataSetChanged();

    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTrailers != null)
            return mTrailers.size();
        else return 0;
    }
}
