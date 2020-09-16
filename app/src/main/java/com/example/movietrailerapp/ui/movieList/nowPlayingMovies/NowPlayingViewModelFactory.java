package com.example.movietrailerapp.ui.movieList.nowPlayingMovies;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NowPlayingViewModelFactory implements ViewModelProvider.Factory {

    NowPlayingMoviesRepository repository;

    public NowPlayingViewModelFactory(NowPlayingMoviesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NowPlayingMoviesViewModel(repository);
    }
}
