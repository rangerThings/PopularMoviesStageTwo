package com.example.andywelsh.popularmoviesstagetwo.Data.Model;

import com.example.andywelsh.popularmoviesstagetwo.Data.Model.MovieItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

// --Commented out by Inspection START (8/4/2018 10:07 PM):
//    @SerializedName("page")
//    @Expose
//    private Integer page;
// --Commented out by Inspection STOP (8/4/2018 10:07 PM)
// --Commented out by Inspection START (8/4/2018 10:07 PM):
//    @SerializedName("total_results")
//    @Expose
//    private Integer totalResults;
// --Commented out by Inspection STOP (8/4/2018 10:07 PM)
// --Commented out by Inspection START (8/4/2018 10:07 PM):
//    @SerializedName("total_pages")
//    @Expose
//    private Integer totalPages;
// --Commented out by Inspection STOP (8/4/2018 10:07 PM)
    @SerializedName("results")
    @Expose
    private final List<MovieItem> results = null;

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    /*@SerializedName("reviewResults")
//    @Expose
//    private List<ReviewItem> reviewResults =null;
//*/
//    public Integer getPage() {
//        return page;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public void setPage(Integer page) {
//        this.page = page;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public Integer getTotalResults() {
//        return totalResults;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public void setTotalResults(Integer totalResults) {
//        this.totalResults = totalResults;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public Integer getTotalPages() {
//        return totalPages;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public void setTotalPages(Integer totalPages) {
//        this.totalPages = totalPages;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

    public List<MovieItem> getResults() {
        return results;
    }
// --Commented out by Inspection START (8/4/2018 10:02 PM):
///*
//    public List<ReviewItem> getReviewResults(){return reviewResults;}
//*/
//    public void setResults(List<MovieItem> results) {
//        this.results = results;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

}
