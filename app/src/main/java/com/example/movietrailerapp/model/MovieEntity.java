package com.example.movietrailerapp.model;

import java.util.ArrayList;

public class MovieEntity {
    private int movieId;
    private String originalTitle;
    private String movieOverview;
    private int rating;
    private String posterImagePath;
    private ArrayList<Integer> genres;
    private String backdropImagePath;
    private String releaseDate;

    public MovieEntity() {
    }

    public MovieEntity(int movieId, String originalTitle, String movieOverview, int rating, String posterImagePath, ArrayList<Integer> genres, String backdropImagePath, String releaseDate) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.movieOverview = movieOverview;
        this.rating = rating;
        this.posterImagePath = posterImagePath;
        this.genres = genres;
        this.backdropImagePath = backdropImagePath;
        this.releaseDate = releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Integer> genres) {
        this.genres = genres;
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }

    public void setBackdropImagePath(String backdropImagePath) {
        this.backdropImagePath = backdropImagePath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
