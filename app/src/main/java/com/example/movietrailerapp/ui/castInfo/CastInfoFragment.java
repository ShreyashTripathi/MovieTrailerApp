package com.example.movietrailerapp.ui.castInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietrailerapp.R;
import com.example.movietrailerapp.adapters.MoviesAdapter;
import com.example.movietrailerapp.model.CastInfo;
import com.example.movietrailerapp.model.MovieEntity;
import com.example.movietrailerapp.ui.castInfo.castBasicInfo.CastInfoRepository;
import com.example.movietrailerapp.ui.castInfo.castBasicInfo.CastInfoViewModel;
import com.example.movietrailerapp.ui.castInfo.castBasicInfo.CastInfoViewModelFactory;
import com.example.movietrailerapp.ui.castInfo.castMovieList.CastMovieListRepository;
import com.example.movietrailerapp.ui.castInfo.castMovieList.CastMovieListViewModel;
import com.example.movietrailerapp.ui.castInfo.castMovieList.CastMovieListViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastInfoFragment extends BottomSheetDialogFragment {
    private CircleImageView profile_pic;
    private TextView name_tv,birthday_tv,gender_tv,bio_tv,pob_tv;
    private RecyclerView cast_movie_rv;
    private String personName;
    private LinearLayout birthday_layout,gender_layout,pob_layout,bio_layout,cast_info_layout;
    private CastInfoViewModel castInfoViewModel;
    private CastMovieListViewModel castMovieListViewModel;
    private ProgressBar cast_movie_pb,cast_info_pb;

    public static CastInfoFragment newInstance() {
        return new CastInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.cast_bottom_sheet,container,false);
        initializeUI(view);
        castInfoViewModel = new ViewModelProvider(this,new CastInfoViewModelFactory(new CastInfoRepository(requireActivity()))).get(CastInfoViewModel.class);
        castMovieListViewModel = new ViewModelProvider(this,new CastMovieListViewModelFactory(new CastMovieListRepository(requireActivity()))).get(CastMovieListViewModel.class);
        setCastBasicInfo();

        return view;
    }

    private void setCastMovieList(String person_id) {
        MutableLiveData<ArrayList<MovieEntity>> movieList = castMovieListViewModel.getMovieListLiveData(person_id);
        if(movieList != null) {
            movieList.observe(this, new Observer<ArrayList<MovieEntity>>() {
                @Override
                public void onChanged(ArrayList<MovieEntity> movieEntities) {
                    disableViews(cast_movie_pb);
                    enableViews(cast_movie_rv);
                    MoviesAdapter moviesAdapter = new MoviesAdapter(movieEntities, requireActivity());
                    cast_movie_rv.setAdapter(moviesAdapter);
                }
            });
        }
    }

    private void setCastBasicInfo() {
        MutableLiveData<CastInfo> castInfo = castInfoViewModel.getCastInfo(personName);
        castInfo.observe(this, new Observer<CastInfo>() {
            @Override
            public void onChanged(final CastInfo castInfo) {
                disableViews(cast_info_pb);
                enableViews(cast_info_layout);
                setCastUIData(castInfo);
                setCastMovieList(castInfo.getPerson_id());
            }
        });
    }

    private void initializeUI(View view) {
        profile_pic = view.findViewById(R.id.profile_pic_cast);
        name_tv = view.findViewById(R.id.name_cast);
        birthday_tv = view.findViewById(R.id.birthday_cast);
        gender_tv = view.findViewById(R.id.gender_cast);
        bio_tv = view.findViewById(R.id.bio_cast);
        pob_tv = view.findViewById(R.id.pob_cast);
        cast_movie_rv = view.findViewById(R.id.movies_rv_cast);
        cast_movie_pb = view.findViewById(R.id.progress_bar_cast_movie);
        cast_info_pb = view.findViewById(R.id.progress_bar_cast_info);
        cast_info_layout = view.findViewById(R.id.layout_cast_info);

        LinearLayoutManager llm1 = new LinearLayoutManager(requireActivity());
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        cast_movie_rv.setLayoutManager(llm1);
        cast_movie_rv.setHasFixedSize(true);

        birthday_layout = view.findViewById(R.id.birthday_layout);
        bio_layout = view.findViewById(R.id.bio_layout);
        gender_layout = view.findViewById(R.id.gender_layout);
        pob_layout = view.findViewById(R.id.pob_layout);
    }

    public void setCastId(String personName)
    {
        this.personName = personName;
    }

    private void setCastUIData(CastInfo castInfo) {
        String profile_pic_url = castInfo.getProfile_pic();
        String name = castInfo.getName();
        String birthday = castInfo.getBirthday();
        String gender = castInfo.getGender();
        String biography = castInfo.getBiography();
        String pob = castInfo.getPob();

        if(!profile_pic_url.equals(""))
            Picasso.with(requireActivity()).load(profile_pic_url).into(profile_pic);
        else
            Picasso.with(requireActivity()).load(R.drawable.ic_baseline_person_24).into(profile_pic);

        if(!name.equals(""))
            name_tv.setText(name);
        else
            name_tv.setText(R.string.not_found_txt);

        if(!birthday.equals(""))
            birthday_tv.setText(birthday);
        else
            disableViews(birthday_layout);

        if(!gender.equals(""))
        {
            if(gender.equals("1"))
                gender_tv.setText(R.string.female);
            else
                gender_tv.setText(R.string.male);
        }
        else
            disableViews(gender_layout);

        if(!biography.equals(""))
            bio_tv.setText(biography);
        else
            disableViews(bio_layout);

        if(!pob.equals(""))
            pob_tv.setText(pob);
        else
            disableViews(pob_layout);
    }


    private void enableViews(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void disableViews(View view) {
        view.setVisibility(View.GONE);
    }

}
