package com.example.movietrailerapp.ui.search;

import android.content.Context;
import android.util.Log;
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
import com.example.movietrailerapp.utils.MoviesServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchMovieRepository {
    private RequestQueue requestQueue;
    private MoviesServerCallback moviesServerCallback;

    public SearchMovieRepository(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }
    public void getSearchedMovie(String query,MoviesServerCallback moviesServerCallback)
    {
        this.moviesServerCallback = moviesServerCallback;
        parseSearchMovieJson(query);
    }
    private void parseSearchMovieJson(String query) {
        if(query.length()==0)
            return;
        String url = "https://api.themoviedb.org/3/search/movie?api_key=84c85237fccfe25218451c4279b3de1a&language=en-US&query="+query+"&page=1&include_adult=true";
        final String basePath = "https://image.tmdb.org/t/p/w300";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //movieEntities.clear();
                    JSONArray jsonArray = response.getJSONArray("results");
                    ArrayList<MovieEntity> movieEntities = new ArrayList<>();
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
                        Log.println(Log.ERROR, "SearchMovieRepo", "Size of movie search is zero");
                    }
                    else {
                        moviesServerCallback.OnSuccess(movieEntities);
                        Log.d("SearchMovieRepo","Search Array size: " + movieEntities.size());
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

}
