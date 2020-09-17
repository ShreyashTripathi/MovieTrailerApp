package com.example.movietrailerapp.ui.castInfo.castMovieList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietrailerapp.model.MovieEntity;
import com.example.movietrailerapp.utils.MoviesServerCallback;

import java.util.ArrayList;

public class CastMovieListViewModel extends ViewModel {
    CastMovieListRepository repository;
    MutableLiveData<ArrayList<MovieEntity>> movieLiveData;
    public CastMovieListViewModel(CastMovieListRepository repository) {
        this.repository = repository;
        movieLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<MovieEntity>> getMovieListLiveData(String person_id) {
        repository.getCastMovieList(person_id, new MoviesServerCallback() {
            @Override
            public void OnSuccess(ArrayList<MovieEntity> movieEntities) {
                movieLiveData.setValue(movieEntities);
            }
        });
        return movieLiveData;
    }
}
