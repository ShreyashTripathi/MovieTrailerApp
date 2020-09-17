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
import android.widget.LinearLayout;
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

    SearchView searchView;
    RecyclerView result_rv;
    LinearLayout linearLayout;
    ArrayList<MovieEntity> movieEntities;
    RequestQueue requestQueue;
    SearchMovieViewModel viewModel;

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
                if(query.length() == 0)
                {
                    Toast.makeText(SearchMovie.this, "Empty movie name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //result_rv.setAdapter(null);
                    searchMovieByName(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchMovieByName(String query) {
        MutableLiveData<ArrayList<MovieEntity>> movieList =  viewModel.getSearchedMovie(query);
        movieList.observe(SearchMovie.this, new Observer<ArrayList<MovieEntity>>() {
            @Override
            public void onChanged(ArrayList<MovieEntity> movieEntities) {
                SearchMovieAdapter adapter = new SearchMovieAdapter(movieEntities, SearchMovie.this);
                result_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void instantiateUI() {
        searchView = findViewById(R.id.search_movie_sv);
        result_rv = findViewById(R.id.search_movie_rv);
        linearLayout = findViewById(R.id.search_movie_container);
        movieEntities = new ArrayList<>();


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        result_rv.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(this);
    }
}