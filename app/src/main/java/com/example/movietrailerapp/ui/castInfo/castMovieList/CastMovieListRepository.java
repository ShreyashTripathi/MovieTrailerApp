package com.example.movietrailerapp.ui.castInfo.castMovieList;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.R;
import com.example.movietrailerapp.model.MovieEntity;
import com.example.movietrailerapp.utils.MoviesServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CastMovieListRepository {
    private Context context;
    private RequestQueue creditsDataQueue;
    private MoviesServerCallback serverCallback;

    public CastMovieListRepository(Context context) {
        this.context = context;
        creditsDataQueue = Volley.newRequestQueue(context);
    }

    public void getCastMovieList(String person_id,MoviesServerCallback serverCallback)
    {
        setCastMovie(person_id);
        this.serverCallback = serverCallback;
    }
    private void setCastMovie(String person_id) {
        String url = "https://api.themoviedb.org/3/person/"+person_id+"/movie_credits?api_key="+context.getResources().getString(R.string.tmdb_api_key)+"&language=en-US";
        final String basePath = "https://image.tmdb.org/t/p/w300";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("cast");
                    ArrayList<MovieEntity> movieEntities = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int movieId = 0,rating = 0;
                        String originalTitle = "",movieOverview="",posterImagePath="",releaseDate="",backdropImagePath="";
                        if(jsonObject.has("id"))
                            movieId=jsonObject.getInt("id");
                        if(jsonObject.has("title"))
                            originalTitle=jsonObject.getString("title");
                        if(jsonObject.has("overview"))
                            movieOverview=jsonObject.getString("overview");
                        if(jsonObject.has("vote_average"))
                            rating = jsonObject.getInt("vote_average");
                        if(jsonObject.has("poster_path"))
                            posterImagePath = basePath + jsonObject.getString("poster_path");
                        JSONArray genres = jsonObject.getJSONArray("genre_ids");
                        if(jsonObject.has("release_date"))
                            releaseDate = jsonObject.getString("release_date");

                        ArrayList<Integer> genresList=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            genresList.add((Integer) genres.get(j));
                        }

                        if(jsonObject.has("backdrop_path"))
                            backdropImagePath = basePath + jsonObject.getString("backdrop_path");

                        movieEntities.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,genresList,backdropImagePath, releaseDate));
                    }
                    if(movieEntities.size() == 0) {
                        Log.println(Log.ERROR, "CastInfoFragment", "Size of movieCast array is 0");
                    }else {
                        serverCallback.OnSuccess(movieEntities);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ERROR,"CastInfoFrag",e.getMessage()+"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR,"CastInfoFrag",error.getMessage()+"");
            }
        });
        creditsDataQueue.add(jsonObjectRequest);
    }

}
