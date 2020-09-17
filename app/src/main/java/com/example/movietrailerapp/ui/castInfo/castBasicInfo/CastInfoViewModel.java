package com.example.movietrailerapp.ui.castInfo.castBasicInfo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietrailerapp.model.CastInfo;
import com.example.movietrailerapp.utils.CastInfoServerCallback;

public class CastInfoViewModel extends ViewModel {
    CastInfoRepository repository;
    MutableLiveData<CastInfo> castInfoMutableLiveData;
    public CastInfoViewModel(CastInfoRepository repository) {
        this.repository = repository;
        castInfoMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<CastInfo> getCastInfo(String person_name)
    {
        repository.getCastInfo(person_name, new CastInfoServerCallback() {
            @Override
            public void onSuccess(CastInfo cast) {
                castInfoMutableLiveData.setValue(cast);
            }
        });
        return castInfoMutableLiveData;
    }
}
