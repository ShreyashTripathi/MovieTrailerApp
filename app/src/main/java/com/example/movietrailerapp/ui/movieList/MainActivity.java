package com.example.movietrailerapp.ui.movieList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movietrailerapp.R;
import com.example.movietrailerapp.ui.movieList.nowPlayingMovies.NowPlayingMoviesRepository;
import com.example.movietrailerapp.ui.movieList.nowPlayingMovies.NowPlayingMoviesViewModel;
import com.example.movietrailerapp.ui.movieList.nowPlayingMovies.NowPlayingViewModelFactory;
import com.example.movietrailerapp.ui.movieList.popularMovies.PopularMoviesModelFactory;
import com.example.movietrailerapp.ui.movieList.popularMovies.PopularMoviesRepository;
import com.example.movietrailerapp.ui.movieList.popularMovies.PopularMoviesViewModel;
import com.example.movietrailerapp.ui.movieList.topRatedMovies.MovieListViewModel;
import com.example.movietrailerapp.ui.movieList.topRatedMovies.MovieRepository;
import com.example.movietrailerapp.ui.movieList.topRatedMovies.MyViewModelFactory;
import com.example.movietrailerapp.ui.movieList.upcomingMovies.UpcomingMovieViewModel;
import com.example.movietrailerapp.ui.movieList.upcomingMovies.UpcomingMoviesModelFactory;
import com.example.movietrailerapp.ui.movieList.upcomingMovies.UpcomingMoviesRepository;
import com.example.movietrailerapp.ui.search.SearchMovie;
import com.example.movietrailerapp.adapters.MoviesAdapter;
import com.example.movietrailerapp.model.MovieEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView nowPlayingMovieRv;

    RecyclerView upcomingMovieRv;

    RecyclerView popularMovieRv;

    RecyclerView topRatedMovieRv;

    LinearLayout linearLayout;
    TextView titleText;
    MovieListViewModel viewModel;
    UpcomingMovieViewModel upcomingMovieViewModel;
    NowPlayingMoviesViewModel nowPlayingMoviesViewModel;
    PopularMoviesViewModel popularMoviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateUI();
        viewModel = new ViewModelProvider(MainActivity.this,new MyViewModelFactory(new MovieRepository(MainActivity.this))).get(MovieListViewModel.class);
        upcomingMovieViewModel = new ViewModelProvider(MainActivity.this,new UpcomingMoviesModelFactory(new UpcomingMoviesRepository(MainActivity.this))).get(UpcomingMovieViewModel.class);
        nowPlayingMoviesViewModel = new ViewModelProvider(MainActivity.this,new NowPlayingViewModelFactory(new NowPlayingMoviesRepository(MainActivity.this))).get(NowPlayingMoviesViewModel.class);
        popularMoviesViewModel = new ViewModelProvider(MainActivity.this,new PopularMoviesModelFactory(new PopularMoviesRepository(MainActivity.this))).get(PopularMoviesViewModel.class);

        getNowPlayingMovies();
        getUpcomingMovies();
        getPopularMovies();
        getTopRatedMovies();

    }

    private void getTopRatedMovies() {
        LiveData<ArrayList<MovieEntity>> movieList = viewModel.getMovieList("top_rated");
        movieList.observe(MainActivity.this, new Observer<ArrayList<MovieEntity>>() {
            @Override
            public void onChanged(ArrayList<MovieEntity> movieEntities) {
                Log.d("MainActivity","ArraySize is: " + movieEntities.size());
                MoviesAdapter moviesAdapter = new MoviesAdapter(movieEntities,MainActivity.this);
                topRatedMovieRv.setAdapter(moviesAdapter);
            }
        });
    }

    private void getPopularMovies() {
        LiveData<ArrayList<MovieEntity>> movieList = popularMoviesViewModel.getMovieList("popular");
        movieList.observe(MainActivity.this, new Observer<ArrayList<MovieEntity>>() {
            @Override
            public void onChanged(ArrayList<MovieEntity> movieEntities) {
                Log.d("MainActivity","ArraySize is: " + movieEntities.size());
                MoviesAdapter moviesAdapter = new MoviesAdapter(movieEntities,MainActivity.this);
                popularMovieRv.setAdapter(moviesAdapter);
            }
        });
    }

    private void getUpcomingMovies() {
        LiveData<ArrayList<MovieEntity>> movieList = upcomingMovieViewModel.getMovieList("upcoming");
        movieList.observe(MainActivity.this, new Observer<ArrayList<MovieEntity>>() {
            @Override
            public void onChanged(ArrayList<MovieEntity> movieEntities) {
                Log.d("MainActivity","ArraySize is: " + movieEntities.size());
                MoviesAdapter moviesAdapter = new MoviesAdapter(movieEntities,MainActivity.this);
                upcomingMovieRv.setAdapter(moviesAdapter);
            }
        });
    }

    private void getNowPlayingMovies() {
        LiveData<ArrayList<MovieEntity>> movieList = nowPlayingMoviesViewModel.getMovieList("now_playing");
        movieList.observe(MainActivity.this, new Observer<ArrayList<MovieEntity>>() {
            @Override
            public void onChanged(ArrayList<MovieEntity> movieEntities) {
                Log.d("MainActivity","ArraySize is: " + movieEntities.size());
                MoviesAdapter moviesAdapter = new MoviesAdapter(movieEntities,MainActivity.this);
                nowPlayingMovieRv.setAdapter(moviesAdapter);
            }
        });
    }

    private void instantiateUI() {
        LinearLayoutManager llm1 = new LinearLayoutManager(MainActivity.this);
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);

        nowPlayingMovieRv = findViewById(R.id.now_playing_rv);
        nowPlayingMovieRv.setLayoutManager(llm1);
        nowPlayingMovieRv.setHasFixedSize(true);

        LinearLayoutManager llm2 = new LinearLayoutManager(MainActivity.this);
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);

        upcomingMovieRv = findViewById(R.id.upcoming_rv);
        upcomingMovieRv.setLayoutManager(llm2);
        upcomingMovieRv.setHasFixedSize(true);


        LinearLayoutManager llm3 = new LinearLayoutManager(MainActivity.this);
        llm3.setOrientation(LinearLayoutManager.HORIZONTAL);

        popularMovieRv = findViewById(R.id.popular_rv);
        popularMovieRv.setLayoutManager(llm3);
        popularMovieRv.setHasFixedSize(true);


        LinearLayoutManager llm4 = new LinearLayoutManager(MainActivity.this);
        llm4.setOrientation(LinearLayoutManager.HORIZONTAL);

        topRatedMovieRv = findViewById(R.id.top_rated_rv);
        topRatedMovieRv.setLayoutManager(llm4);
        topRatedMovieRv.setHasFixedSize(true);

        linearLayout = findViewById(R.id.mainActivityContainer);
        titleText = findViewById(R.id.title_text);
    }

    public void startSearchActivity(View view) {
        startActivity(new Intent(MainActivity.this, SearchMovie.class));
    }
}