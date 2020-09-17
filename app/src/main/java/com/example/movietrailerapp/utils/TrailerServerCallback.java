package com.example.movietrailerapp.utils;

import com.example.movietrailerapp.model.TrailerEntity;

import java.util.ArrayList;

public interface TrailerServerCallback {
    void onSuccess(ArrayList<TrailerEntity> trailerEntities);
}
