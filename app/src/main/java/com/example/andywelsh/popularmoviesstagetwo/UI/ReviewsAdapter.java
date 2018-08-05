package com.example.andywelsh.popularmoviesstagetwo.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.ReviewItem;
import com.example.andywelsh.popularmoviesstagetwo.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private List<ReviewItem> mReviews; // Cached copy of words
    private final Context mContext;

    //constructor takes context and movie array passed in from MainActivity
    public ReviewsAdapter(Context context, List<ReviewItem> reviewList) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mReviews = reviewList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView reviewView;

        private ViewHolder(View itemView) {
            super(itemView);
            reviewView = itemView.findViewById(R.id.tv_reviews);

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

        ReviewItem current = mReviews.get(position);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
        mContext.startActivity(webIntent);


        /*//Toast Debugging
        String toastText = "Item Clicked: " + String.valueOf(position);
        Toast toast = Toast.makeText(mContext, toastText, LENGTH_SHORT);
        toast.show();*/
    }


    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.reviews_rv_item, viewGroup, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem current = mReviews.get(position);
        holder.reviewView.setText(current.getUrl());

    }

    public void updateReviews(List<ReviewItem> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mReviews != null)
            return mReviews.size();
        else return 0;
    }
}
