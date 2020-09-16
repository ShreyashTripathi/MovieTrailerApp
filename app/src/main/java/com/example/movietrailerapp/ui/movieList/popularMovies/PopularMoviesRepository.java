package com.example.movietrailerapp.ui.movieList.popularMovies;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.model.MovieEntity;
import com.example.movietrailerapp.utils.MoviesServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopularMoviesRepository {
    Context context;
    RequestQueue requestQueue;
    MoviesServerCallback serverCallback;

    public PopularMoviesRepository(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }
    public void getPopularMovieEntities(String type_, MoviesServerCallback serverCallback_) {
        parseMovieJSON(type_);
        this.serverCallback = serverCallback_;
    }
    private void parseMovieJSON(final String type) {
        Log.d("MovieRepo",""+type);
        final String basePath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/"+type+"?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    ArrayList<MovieEntity> movieEntityArrayList = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int movieId=item.getInt("id");
                        String originalTitle=item.getString("original_title");
                        String movieOverview=item.getString("overview");
                        int rating;
                        rating = item.getInt("vote_average");
                        String posterImagePath=basePath+item.getString("poster_path");
                        JSONArray genres=item.getJSONArray("genre_ids");
                        String releaseDate = item.getString("release_date");

                        ArrayList<Integer> genresList=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            genresList.add((Integer) genres.get(j));
                        }
                        String backdropImagePath=basePath+item.getString("backdrop_path");
                        movieEntityArrayList.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,genresList,backdropImagePath, releaseDate));
                    }
                    if(movieEntityArrayList.size() < 1)
                        Log.d("MovieRepo","ArraySize is zero");
                    else {
                        Log.d("MovieRepo", "ArraySize is: " + movieEntityArrayList.size());
                        serverCallback.OnSuccess(movieEntityArrayList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("MovieRepo",""+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MovieRepo",""+error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
