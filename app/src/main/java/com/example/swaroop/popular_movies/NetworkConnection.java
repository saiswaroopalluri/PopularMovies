package com.example.swaroop.popular_movies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkConnection {

    private static final String TAG = NetworkConnection.class.getSimpleName();

    private static final String MOVIE_SCHEME = "https";
    private static final String MOVIE_BASE_URL = "api.themoviedb.org";
    private static final String MOVIE_APPENDED_PATH = "3/movie";

    private static final String MOVIE_API_KEY = "1ea0beb1f444417ec6210654c7a87f1b";
    private static final String MOVIE_QUERY_API_PARAM = "api_key";


    public static URL buildUrl(String sortType) {

        Uri.Builder builder = new Uri.Builder();
        Uri uri =  builder.scheme(MOVIE_SCHEME)
                .authority(MOVIE_BASE_URL)
                .appendEncodedPath(MOVIE_APPENDED_PATH)
                .appendPath(sortType)
                .appendQueryParameter(MOVIE_QUERY_API_PARAM,MOVIE_API_KEY)
                .build();


        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;

    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d(TAG, "Built URI " + url);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Log.v("input stream", in.toString());

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}

