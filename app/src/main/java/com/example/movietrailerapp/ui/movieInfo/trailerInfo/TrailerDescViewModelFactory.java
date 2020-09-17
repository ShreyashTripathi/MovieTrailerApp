package com.example.movietrailerapp.ui.movieInfo.trailerInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrailerDescViewModelFactory implements ViewModelProvider.Factory {

    TrailerDescRepository repository;

    public TrailerDescViewModelFactory(TrailerDescRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrailerDescViewModel(repository);
    }
}
