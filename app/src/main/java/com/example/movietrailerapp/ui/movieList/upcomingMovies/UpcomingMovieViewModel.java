package com.example.movietrailerapp.ui.movieList.upcomingMovies;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietrailerapp.model.MovieEntity;
import com.example.movietrailerapp.utils.MoviesServerCallback;

import java.util.ArrayList;

public class UpcomingMovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<MovieEntity>> movieList;
    private UpcomingMoviesRepository repository;

    public UpcomingMovieViewModel(UpcomingMoviesRepository repository) {
        this.repository = repository;
        movieList = new MutableLiveData<>();
    }
    public MutableLiveData<ArrayList<MovieEntity>> getMovieList(String type) {
        repository.getUpcomingMovieEntities(type, new MoviesServerCallback() {
            @Override
            public void OnSuccess(ArrayList<MovieEntity> movieEntities) {
                Log.d("MovieListViewModel","ArraySize is: " + movieEntities.size());
                movieList.setValue(movieEntities);
            }
        });

        return movieList;
    }
}
