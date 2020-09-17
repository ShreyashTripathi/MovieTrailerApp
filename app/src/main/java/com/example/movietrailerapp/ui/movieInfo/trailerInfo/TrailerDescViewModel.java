package com.example.movietrailerapp.ui.movieInfo.trailerInfo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietrailerapp.model.MovieCast;
import com.example.movietrailerapp.model.TrailerEntity;
import com.example.movietrailerapp.utils.CastServerCallback;
import com.example.movietrailerapp.utils.TrailerServerCallback;

import java.util.ArrayList;

public class TrailerDescViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TrailerEntity>> trailerMutableLiveData;
    private TrailerDescRepository trailerDescRepository;

    public TrailerDescViewModel(TrailerDescRepository repository) {
        this.trailerDescRepository = repository;
        trailerMutableLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<ArrayList<TrailerEntity>> getTrailers(String movie_id)
    {
        trailerDescRepository.getMovieTrailers(movie_id, new TrailerServerCallback() {
            @Override
            public void onSuccess(ArrayList<TrailerEntity> trailerEntities) {
                trailerMutableLiveData.setValue(trailerEntities);
            }
        });
        return trailerMutableLiveData;
    }
}
