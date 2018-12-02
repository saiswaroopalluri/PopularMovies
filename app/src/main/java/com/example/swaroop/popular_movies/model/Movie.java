package com.example.swaroop.popular_movies.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private Double popularity;
    private String posterPath = null;
    private Integer voteAverage = 0;
    private String originalTitle;
    private String overview;
    private String releaseDate;

    public Movie() {

    }

    public Movie(Integer voteCount, Integer id, Boolean video, Integer voteAverage, String title, Double popularity, String posterPath, String originalLanguage, String originalTitle, Integer[] genreIDs, String backdropPath, Boolean adult, String overview, String releaseDate) {

        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }


    public Integer getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Integer voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
