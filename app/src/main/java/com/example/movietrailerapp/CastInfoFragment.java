package com.example.movietrailerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastInfoFragment extends BottomSheetDialogFragment {
    private CircleImageView profile_pic;
    private TextView name_tv,birthday_tv,gender_tv,bio_tv,pob_tv;
    private RecyclerView cast_movie_rv;
    private Integer castId;

    public static CastInfoFragment newInstance() {
        return new CastInfoFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cast_bottom_sheet,container,false);
        initializeUI(view);
        setCastData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initializeUI(View view) {
        profile_pic = view.findViewById(R.id.profile_pic_cast);
        name_tv = view.findViewById(R.id.name_cast);
        birthday_tv = view.findViewById(R.id.birthday_cast);
        gender_tv = view.findViewById(R.id.gender_cast);
        bio_tv = view.findViewById(R.id.bio_cast);
        pob_tv = view.findViewById(R.id.pob_cast);
        cast_movie_rv = view.findViewById(R.id.movies_rv_cast);
    }

    public void setCastId(int castID)
    {
        castId = castID;
    }
    private void setCastData()
    {

    }
}
