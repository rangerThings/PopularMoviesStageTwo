package com.example.andywelsh.popularmoviesstagetwo.Utilities;

public class Constants {
    //API key must be added in order for app to run
    public final static String API_KEY = "PUT YOUR API KEY HERE";


    //Constants for URL
    public final static String MOVIES_BASE_URL = "http://api.themoviedb.org/";
    public final static String SORT_POPULAR = "popular";
    public final static String SORT_RATED = "top_rated";
    public final static String SORT_FAVS = "favorite";

    //Constants for Sharedpreferences
    public static final String DEFAULT_SORT = "popular";
    public static final String DEFAULT_SORT_MESSAGE = "Sorted by Popularity";
    public static final String POPULAR_SORT_MESSAGE = "Sorted by Popularity";
    public static final String TOP_RATED_MESSAGE = "Sorted by Top Rated";
    public static final String FAV_SORT_MESSAGE = "Sorted by Favorite";
    public static final int COLUMNS = 2;

    public static final String POSTER_URL_500 = "http://image.tmdb.org/t/p/w500";



}
