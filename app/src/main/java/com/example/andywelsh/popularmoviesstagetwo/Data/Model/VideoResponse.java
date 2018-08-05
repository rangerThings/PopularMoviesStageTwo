package com.example.andywelsh.popularmoviesstagetwo.Data.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//generated from http://jsonschema2pojo.org

public class VideoResponse {
// --Commented out by Inspection START (8/4/2018 10:07 PM):
//    @SerializedName("id")
//    @Expose
//    private Integer id;
// --Commented out by Inspection STOP (8/4/2018 10:07 PM)
    @SerializedName("results")
    @Expose
    private final List<VideoItem> results = null;

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public Integer getId() {
//        return id;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public void setId(Integer id) {
//        this.id = id;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)

    public List<VideoItem> getResults() {
        return results;
    }

// --Commented out by Inspection START (8/4/2018 10:02 PM):
//    public void setResults(List<VideoItem> results) {
//        this.results = results;
//    }
// --Commented out by Inspection STOP (8/4/2018 10:02 PM)


}
