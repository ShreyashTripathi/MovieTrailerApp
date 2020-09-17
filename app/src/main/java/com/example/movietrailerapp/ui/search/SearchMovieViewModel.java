package com.example.movietrailerapp.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietrailerapp.model.MovieEntity;
import com.example.movietrailerapp.utils.MoviesServerCallback;

import java.util.ArrayList;

public class SearchMovieViewModel extends ViewModel {
    SearchMovieRepository repository;
    MutableLiveData<ArrayList<MovieEntity>> mutableLiveData;

    public SearchMovieViewModel(SearchMovieRepository repository) {
        mutableLiveData = new MutableLiveData<>();
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<MovieEntity>> getSearchedMovie(String query) {
        repository.getSearchedMovie(query, new MoviesServerCallback() {
            @Override
            public void OnSuccess(ArrayList<MovieEntity> movieEntities) {
                mutableLiveData.setValue(movieEntities);
            }
        });
        return mutableLiveData;
    }
}
