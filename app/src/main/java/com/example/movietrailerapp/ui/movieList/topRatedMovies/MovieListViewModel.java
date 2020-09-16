package com.example.movietrailerapp.ui.movieList.topRatedMovies;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietrailerapp.model.MovieEntity;
import com.example.movietrailerapp.utils.MoviesServerCallback;

import java.util.ArrayList;

public class MovieListViewModel extends ViewModel {

    private MutableLiveData < ArrayList<MovieEntity> > movieList;
    private MovieRepository repository;

    public MovieListViewModel(MovieRepository repository_) {
        movieList = new MutableLiveData<>();
        this.repository = repository_;
    }

    public MutableLiveData<ArrayList<MovieEntity>> getMovieList(String type) {
        repository.getMovieEntities(type, new MoviesServerCallback() {
            @Override
            public void OnSuccess(ArrayList<MovieEntity> movieEntities) {
                Log.d("MovieListViewModel","ArraySize is: " + movieEntities.size());
                movieList.setValue(movieEntities);
            }
        });

        return movieList;
    }
}
