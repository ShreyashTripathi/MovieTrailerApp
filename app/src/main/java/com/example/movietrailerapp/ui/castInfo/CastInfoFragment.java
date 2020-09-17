package com.example.movietrailerapp.ui.castInfo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastInfoFragment extends BottomSheetDialogFragment {
    private CircleImageView profile_pic;
    private TextView name_tv,birthday_tv,gender_tv,bio_tv,pob_tv;
    private RecyclerView cast_movie_rv;
    private String personName;
    private RequestQueue castDataQueue,creditsDataQueue;
    private ArrayList<MovieEntity> movieEntities;
    private LinearLayout birthday_layout,gender_layout,pob_layout,bio_layout,known_for_layout;
    private CastInfoViewModel castInfoViewModel;
    private CastMovieListViewModel castMovieListViewModel;
    private String person_id;

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
        castDataQueue = Volley.newRequestQueue(requireActivity());
        movieEntities = new ArrayList<>();
        creditsDataQueue = Volley.newRequestQueue(requireActivity());

        LinearLayoutManager llm1 = new LinearLayoutManager(requireActivity());
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        cast_movie_rv.setLayoutManager(llm1);
        cast_movie_rv.setHasFixedSize(true);

        birthday_layout = view.findViewById(R.id.birthday_layout);
        bio_layout = view.findViewById(R.id.bio_layout);
        gender_layout = view.findViewById(R.id.gender_layout);
        pob_layout = view.findViewById(R.id.pob_layout);
        known_for_layout = view.findViewById(R.id.movie_rv_layout);
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
            disableView(birthday_layout);

        if(!gender.equals(""))
        {
            if(gender.equals("1"))
                gender_tv.setText(R.string.female);
            else
                gender_tv.setText(R.string.male);
        }
        else
            disableView(gender_layout);

        if(!biography.equals(""))
            bio_tv.setText(biography);
        else
            disableView(bio_layout);

        if(!pob.equals(""))
            pob_tv.setText(pob);
        else
            disableView(pob_layout);
    }

    private void disableView(LinearLayout layout) {
        layout.setVisibility(View.GONE);
    }


}
