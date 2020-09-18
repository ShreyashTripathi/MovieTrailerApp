package com.example.movietrailerapp.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.R;
import com.example.movietrailerapp.adapters.SearchMovieAdapter;
import com.example.movietrailerapp.model.MovieEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchMovie extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView result_rv;
    private SearchMovieViewModel viewModel;
    private ProgressBar search_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        instantiateUI();
        viewModel = new ViewModelProvider(SearchMovie.this,new SearchMovieViewModelFactory(new SearchMovieRepository(SearchMovie.this))).get(SearchMovieViewModel.class);
        searchView.setActivated(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*if(query.length() == 0)
                {
                    Toast.makeText(SearchMovie.this, "Empty movie name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    enableViews(search_pb);
                    searchMovieByName(query);
                }
                return true;*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() >= 1) {
                    enableViews(search_pb);
                    searchMovieByName(newText);
                }
                else {
                    disableViews(result_rv);
                }
                return true;
            }
        });
    }

    private void searchMovieByName(String query) {
        MutableLiveData<ArrayList<MovieEntity>> movieList =  viewModel.getSearchedMovie(query);
        movieList.observe(SearchMovie.this, new Observer<ArrayList<MovieEntity>>() {
            @Override
            public void onChanged(ArrayList<MovieEntity> movieEntities) {
                disableViews(search_pb);
                enableViews(result_rv);
                SearchMovieAdapter adapter = new SearchMovieAdapter(movieEntities, SearchMovie.this);
                result_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void instantiateUI() {
        searchView = findViewById(R.id.search_movie_sv);
        result_rv = findViewById(R.id.search_movie_rv);
        search_pb = findViewById(R.id.progress_search_movie);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        result_rv.setLayoutManager(llm);

    }

    private void enableViews(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void disableViews(View view) {
        view.setVisibility(View.GONE);
    }

}