package com.example.movietrailerapp.ui.movieInfo.castListInfo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietrailerapp.model.MovieCast;
import com.example.movietrailerapp.utils.CastServerCallback;

import java.util.ArrayList;

public class CastListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<MovieCast>> movieCastMutableLiveData;
    private CastListRepository castListRepository;

    public CastListViewModel(CastListRepository castListRepository) {
        movieCastMutableLiveData = new MutableLiveData<>();
        this.castListRepository = castListRepository;
    }
    public MutableLiveData<ArrayList<MovieCast>> getCastData(String movie_id){
        castListRepository.getMovieCasts(movie_id, new CastServerCallback() {
            @Override
            public void onSuccess(ArrayList<MovieCast> movieCasts) {
                movieCastMutableLiveData.setValue(movieCasts);
            }
        });
        return movieCastMutableLiveData;
    }
}
