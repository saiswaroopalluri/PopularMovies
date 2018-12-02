package com.example.swaroop.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swaroop.popular_movies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private ImageView moviePoster;
    private TextView movieOverview;
    private TextView movieReleaseDate;
    private TextView movieRating;
    private TextView movieTitle;

    private static final String INTENT_KEY = "movie_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        moviePoster = findViewById(R.id.moviePoster);
        movieReleaseDate = findViewById(R.id.movieReleaseDate);
        movieRating = findViewById(R.id.movieUserRating);
        movieOverview = findViewById(R.id.movieOverview);
        movieTitle = findViewById(R.id.TitleText);

        Intent intentCalled = getIntent();
        if (intentCalled != null) {
            if (intentCalled.hasExtra(INTENT_KEY)) {
                Movie movie = (Movie) intentCalled.getSerializableExtra(INTENT_KEY);

                String imageURL = "http://image.tmdb.org/t/p/w185"+movie.getPosterPath();
                Picasso.with(this).load(imageURL).into(moviePoster);

                movieTitle.setText(movie.getTitle());
                movieReleaseDate.setText("Release-Date:\n "+movie.getReleaseDate());
                movieRating.setText("Ratings:\n "+movie.getVoteAverage().toString()+"/10");
                movieOverview.setText("OverView:"+movie.getOverview());

            }
        }

    }
}

