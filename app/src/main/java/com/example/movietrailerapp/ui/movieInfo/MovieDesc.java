package com.example.movietrailerapp.ui.movieInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movietrailerapp.R;
import com.example.movietrailerapp.adapters.MovieCastAdapter;
import com.example.movietrailerapp.adapters.TrailerAdapter;
import com.example.movietrailerapp.model.MovieCast;
import com.example.movietrailerapp.model.TrailerEntity;
import com.example.movietrailerapp.ui.movieInfo.castListInfo.CastListRepository;
import com.example.movietrailerapp.ui.movieInfo.castListInfo.CastListViewModel;
import com.example.movietrailerapp.ui.movieInfo.castListInfo.CastListViewModelFactory;
import com.example.movietrailerapp.ui.movieInfo.trailerInfo.TrailerDescRepository;
import com.example.movietrailerapp.ui.movieInfo.trailerInfo.TrailerDescViewModel;
import com.example.movietrailerapp.ui.movieInfo.trailerInfo.TrailerDescViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDesc extends AppCompatActivity {

    ImageView movie_back_iv,movie_poster_iv;
    TextView movie_name_tv,movie_overview_tv,movie_rating_tv;
    RecyclerView movie_cast_rv, movie_trailer_rv;
    TrailerDescViewModel trailerDescViewModel;
    CastListViewModel castListViewModel;
    ProgressBar cast_pb,trailers_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_desc);
        instantiateUI();
        trailerDescViewModel = new ViewModelProvider(MovieDesc.this,new TrailerDescViewModelFactory(new TrailerDescRepository(MovieDesc.this))).get(TrailerDescViewModel.class);
        castListViewModel = new ViewModelProvider(MovieDesc.this,new CastListViewModelFactory(new CastListRepository(MovieDesc.this))).get(CastListViewModel.class);

        Intent intent = getIntent();
        Picasso.with(this).load(intent.getStringExtra("backdrop_url")).fit().into(movie_back_iv);
        Picasso.with(this).load(intent.getStringExtra("poster_url")).into(movie_poster_iv);
        String movie_title = intent.getStringExtra("movie_title");
        assert movie_title != null;
        if(movie_title.length() > 15)
            movie_name_tv.setTextSize(15);
        else
            movie_name_tv.setTextSize(20);
        movie_name_tv.setText(movie_title);
        movie_rating_tv.setText(String.format("%s", intent.getIntExtra("rating", 0)));
        movie_overview_tv.setText(intent.getStringExtra("overview_txt"));
        getAllCasts(intent.getStringExtra("movie_id"));
        getAllTrailers(intent.getStringExtra("movie_id"));

    }

    private void getAllTrailers(String movie_id) {
        MutableLiveData<ArrayList<TrailerEntity>> trailersData = trailerDescViewModel.getTrailers(movie_id);
        trailersData.observe(MovieDesc.this, new Observer<ArrayList<TrailerEntity>>() {
            @Override
            public void onChanged(ArrayList<TrailerEntity> trailerEntities) {
                disableViews(trailers_pb);
                enableViews(movie_trailer_rv);
                TrailerAdapter trailerAdapter = new TrailerAdapter(MovieDesc.this,trailerEntities);
                movie_trailer_rv.setAdapter(trailerAdapter);
            }
        });
    }

    private void getAllCasts(String movie_id) {
        MutableLiveData<ArrayList<MovieCast>> castData = castListViewModel.getCastData(movie_id);
        castData.observe(MovieDesc.this, new Observer<ArrayList<MovieCast>>() {
            @Override
            public void onChanged(ArrayList<MovieCast> movieCasts) {
                disableViews(cast_pb);
                enableViews(movie_cast_rv);
                MovieCastAdapter castAdapter = new MovieCastAdapter(MovieDesc.this,movieCasts,getSupportFragmentManager());
                movie_cast_rv.setAdapter(castAdapter);
            }
        });
    }

    private void instantiateUI() {
        movie_back_iv = findViewById(R.id.desc_movie_back_img);
        movie_poster_iv = findViewById(R.id.desc_movie_poster);
        movie_poster_iv.bringToFront();
        movie_name_tv = findViewById(R.id.desc_movie_title);
        movie_overview_tv = findViewById(R.id.desc_movie_overview);
        movie_rating_tv = findViewById(R.id.desc_movie_ratings);
        movie_cast_rv = findViewById(R.id.desc_cast_rv);
        movie_trailer_rv = findViewById(R.id.desc_trailers_rv);
        cast_pb = findViewById(R.id.progress_bar_cast_list);
        trailers_pb = findViewById(R.id.progress_bar_trailers);

        LinearLayoutManager llm1 = new LinearLayoutManager(MovieDesc.this);
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        movie_cast_rv.setLayoutManager(llm1);
        movie_cast_rv.setHasFixedSize(true);

        LinearLayoutManager llm2 = new LinearLayoutManager(MovieDesc.this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        movie_trailer_rv.setLayoutManager(llm2);
        movie_trailer_rv.setHasFixedSize(true);

    }

    private void enableViews(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void disableViews(View view) {
        view.setVisibility(View.GONE);
    }

}