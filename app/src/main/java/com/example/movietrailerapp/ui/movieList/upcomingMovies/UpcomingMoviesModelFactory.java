package com.example.movietrailerapp.ui.movieList.upcomingMovies;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UpcomingMoviesModelFactory implements ViewModelProvider.Factory{

    UpcomingMoviesRepository repository;

    public UpcomingMoviesModelFactory(UpcomingMoviesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UpcomingMovieViewModel(repository);
    }
}
