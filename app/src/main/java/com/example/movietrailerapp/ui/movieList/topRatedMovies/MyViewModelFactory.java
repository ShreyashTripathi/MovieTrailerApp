package com.example.movietrailerapp.ui.movieList.topRatedMovies;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    MovieRepository movieRepository;
    public MyViewModelFactory(MovieRepository repository) {
        this.movieRepository = repository;
    }

    @NonNull
    @Override

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieListViewModel(movieRepository);
    }
}
