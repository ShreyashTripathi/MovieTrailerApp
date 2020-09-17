package com.example.movietrailerapp.ui.castInfo.castBasicInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CastInfoViewModelFactory implements ViewModelProvider.Factory {
    CastInfoRepository repository;

    public CastInfoViewModelFactory(CastInfoRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CastInfoViewModel(repository);
    }
}
