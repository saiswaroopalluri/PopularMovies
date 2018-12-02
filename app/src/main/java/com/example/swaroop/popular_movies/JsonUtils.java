package com.example.swaroop.popular_movies;


import com.example.swaroop.popular_movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

    public static Movie[] getMoviesFromJSONString(String movieJSONString) throws JSONException {

        final String MOVIES_RESULTS = "results";

        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_ID = "id";
        final String MOVIE_VIDEO = "video";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        final String MOVIE_TITLE = "title";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
        final String MOVIE_ORIGINAL_TITLE = "original_title";
        final String MOVIE_GENRE_IDS = "genre_ids";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_ADULT = "adult";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";

        /* Movie array to hold each movies data */
        Movie[] parsedMoviesArray;

        JSONObject moviesJSON = new JSONObject(movieJSONString);

        JSONArray resultsArray = moviesJSON.getJSONArray(MOVIES_RESULTS);

        parsedMoviesArray = new Movie[resultsArray.length()];

        for (int i = 0; i<resultsArray.length();i++) {
            Integer voteCount, id, voteAverage;
            Boolean video, adult;
            String title, posterPath, originalLanguage, originalTitle, backdropPath, overview, releaseDate;
            Double popularity;
            Integer[] genreIDs = null;

            JSONObject movieJSONObject = resultsArray.getJSONObject(i);
            voteCount = movieJSONObject.getInt(MOVIE_VOTE_COUNT);
            id = movieJSONObject.getInt(MOVIE_ID);
            voteAverage = movieJSONObject.getInt(MOVIE_VOTE_AVERAGE);

            video = movieJSONObject.getBoolean(MOVIE_VIDEO);
            adult = movieJSONObject.getBoolean(MOVIE_ADULT);

            title = movieJSONObject.getString(MOVIE_TITLE);
            posterPath = movieJSONObject.getString(MOVIE_POSTER_PATH);
            originalLanguage = movieJSONObject.getString(MOVIE_ORIGINAL_LANGUAGE);
            originalTitle = movieJSONObject.getString(MOVIE_ORIGINAL_TITLE);
            backdropPath = movieJSONObject.getString(MOVIE_BACKDROP_PATH);
            overview = movieJSONObject.getString(MOVIE_OVERVIEW);
            releaseDate = movieJSONObject.getString(MOVIE_RELEASE_DATE);

            popularity = movieJSONObject.getDouble(MOVIE_POPULARITY);

            JSONArray genreJSONArray = movieJSONObject.getJSONArray(MOVIE_GENRE_IDS);
            genreIDs = new Integer[genreJSONArray.length()];
            for (int j = 0;j<genreJSONArray.length();j++) {
                genreIDs[j] = genreJSONArray.optInt(j);
            }

            parsedMoviesArray[i] = new Movie(voteCount, id, video, voteAverage, title, popularity, posterPath, originalLanguage, originalTitle, genreIDs, backdropPath, adult, overview, releaseDate);

        }

        return  parsedMoviesArray;
    }
}

