package com.example.movietrailerapp.utils;

import com.example.movietrailerapp.model.MovieEntity;

import java.util.ArrayList;

public interface MoviesServerCallback {
    void OnSuccess(ArrayList<MovieEntity> movieEntities);
}
