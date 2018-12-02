package com.example.swaroop.popular_movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.swaroop.popular_movies.adapter.MovieAdapter;
import com.example.swaroop.popular_movies.model.Movie;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private MovieAdapter mMovieAdapter;
    private Movie[] moviesList;
    private Spinner mSpinner;
    private FetchMoviesTask mFetchMoviesTask;
    private ProgressBar mProgressBar;

    private static final String INTENT_KEY = "movie_detail";
    private static final String SORT_TYPE_POPULAR = "popular";
    private static final String SORT_TYPE_TOP_RATED = "top_rated";
    private static final String SORT_TYPE_DEFAULT = "Select Sort Type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);

        setupSpinner();
        mGridView = findViewById(R.id.gridView);
        loadMovies();

        mFetchMoviesTask =  new FetchMoviesTask();

        // By default get results for type: popular
        mFetchMoviesTask.execute(SORT_TYPE_POPULAR);
    }

    private void setupSpinner() {

        final Map<String, String> map = new HashMap<>();
        map.put("Top Rated", SORT_TYPE_TOP_RATED);
        map.put("Most Popular", SORT_TYPE_POPULAR);

        Set<String> spinnerKeys = map.keySet();
        Log.v("spinner keys",spinnerKeys.toString());
        String[] spinnerArray = spinnerKeys.toArray(new String[spinnerKeys.size()+1]);
        int index = 0;
        spinnerArray[index++] = SORT_TYPE_DEFAULT;
        for(String spinnerKey: spinnerKeys) {
            spinnerArray[index++] = spinnerKey;
        }

        mSpinner = findViewById(R.id.spinner);

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                String item = parent.getItemAtPosition(position).toString();
                String mappedSpinner = map.get(item);
                Log.v("Spinner:",mappedSpinner);
                mFetchMoviesTask = new FetchMoviesTask();
                mFetchMoviesTask.execute(mappedSpinner);
                mSpinner.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpinner.setVisibility(View.GONE);
            }
        });
    }


    private void showSpinner() {
        mSpinner.setVisibility(View.VISIBLE);
        mSpinner.performClick();
    }

    private void loadMovies() {
        if (moviesList == null) {
            return;
        }

        mMovieAdapter = new MovieAdapter(MainActivity.this,moviesList);
        mGridView.setAdapter(mMovieAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = moviesList[position];
                Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
                intent.putExtra(INTENT_KEY,movie);
                startActivity(intent);
            }
        });
    }


    public class FetchMoviesTask extends AsyncTask<String,Void,Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            if (params == null) {
                return null;
            }

            String sortType = params[0];
            URL moviesURL = NetworkConnection.buildUrl(sortType);

            try {
                String movieSearchResults = NetworkConnection.getResponseFromHttpUrl(moviesURL);
                Log.v("Results:",movieSearchResults);
                return JsonUtils.getMoviesFromJSONString(movieSearchResults);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            mProgressBar.setVisibility(View.INVISIBLE);
            moviesList = movies;
            loadMovies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort) {
            showSpinner();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
