package com.example.movietrailerapp.utils;

import com.example.movietrailerapp.model.MovieCast;

import java.util.ArrayList;

public interface CastServerCallback {
    void onSuccess(ArrayList<MovieCast> movieCasts);
}
