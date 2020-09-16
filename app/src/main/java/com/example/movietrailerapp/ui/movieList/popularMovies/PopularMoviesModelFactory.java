package com.example.movietrailerapp.ui.movieList.popularMovies;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PopularMoviesModelFactory implements ViewModelProvider.Factory {
    PopularMoviesRepository repository;

    public PopularMoviesModelFactory(PopularMoviesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PopularMoviesViewModel(repository);
    }
}
