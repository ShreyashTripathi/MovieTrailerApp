package com.example.movietrailerapp.ui.castInfo.castMovieList;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.movietrailerapp.ui.movieInfo.castListInfo.CastListViewModel;

public class CastMovieListViewModelFactory implements ViewModelProvider.Factory {

    CastMovieListRepository repository;

    public CastMovieListViewModelFactory(CastMovieListRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CastMovieListViewModel(repository);
    }
}
