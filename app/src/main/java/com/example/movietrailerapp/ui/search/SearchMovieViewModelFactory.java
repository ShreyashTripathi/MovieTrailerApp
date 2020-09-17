package com.example.movietrailerapp.ui.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchMovieViewModelFactory implements ViewModelProvider.Factory {
    SearchMovieRepository repository;

    public SearchMovieViewModelFactory(SearchMovieRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchMovieViewModel(repository);
    }
}
