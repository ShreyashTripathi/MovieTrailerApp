package com.example.movietrailerapp.ui.movieInfo.castListInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CastListViewModelFactory implements ViewModelProvider.Factory {
    CastListRepository castListRepository;

    public CastListViewModelFactory(CastListRepository castListRepository) {
        this.castListRepository = castListRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CastListViewModel(castListRepository);
    }
}
