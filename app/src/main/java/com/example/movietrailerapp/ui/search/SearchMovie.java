package com.example.movietrailerapp.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        instantiateUI();
        searchView.setActivated(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() == 0)
                {
                    Toast.makeText(SearchMovie.this, "Empty movie name!", Toast.LENGTH_SHORT).show();
                }
                else {
                    parseSearchMovieJson(query);

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*newText = newText.toLowerCase();
                parseSearchMovieJson(newText);
                */
                return false;
            }
        });
    }

    private void parseSearchMovieJson(String query) {
        if(query.length()==0)
            return;
        //Toast.makeText(SearchMovie.this, "Movie: " + query, Toast.LENGTH_SHORT).show();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+getResources().getString(R.string.tmdb_api_key)+"&language=en-US&query="+query+"&page=1&include_adult=true";
        final String basePath = "https://image.tmdb.org/t/p/w300";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    movieEntities.clear();
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int movieId = item.getInt("id");
                        String originalTitle = item.getString("original_title");
                        String movieOverview = item.getString("overview");
                        int rating;
                        rating = item.getInt("vote_average");
                        String posterImagePath = basePath + item.getString("poster_path");
                        JSONArray genres = item.getJSONArray("genre_ids");
                        String releaseDate = item.getString("release_date");

                        ArrayList<Integer> genresList = new ArrayList<>();
                        for (int j = 0; j < genres.length(); j++) {
                            genresList.add((Integer) genres.get(j));
                        }
                        String backdropImagePath = basePath + item.getString("backdrop_path");
                        movieEntities.add(new MovieEntity(movieId, originalTitle, movieOverview, rating, posterImagePath, genresList, backdropImagePath, releaseDate));
                    }
                    if (movieEntities.size() == 0) {
                        Log.println(Log.ERROR, "SearchMovie", "Size of movie search is zero");
                        Toast.makeText(SearchMovie.this, "No movie found!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SearchMovieAdapter adapter = new SearchMovieAdapter(movieEntities, SearchMovie.this);
                        result_rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR,"SearchMovie",""+error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
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