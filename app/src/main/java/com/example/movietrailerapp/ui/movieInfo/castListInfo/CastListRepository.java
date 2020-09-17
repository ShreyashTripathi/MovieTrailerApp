package com.example.movietrailerapp.ui.movieInfo.castListInfo;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.model.MovieCast;
import com.example.movietrailerapp.utils.CastServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CastListRepository {
    private CastServerCallback castServerCallback;
    private RequestQueue castQueue;

    public CastListRepository(Context context) {
        castQueue = Volley.newRequestQueue(context);
    }
    public void getMovieCasts(String movie_id,CastServerCallback castServerCallback)
    {
        parseCastsJson(movie_id);
        this.castServerCallback = castServerCallback;
    }
    private void parseCastsJson(String movie_id) {
        String url="https://api.themoviedb.org/3/movie/"+movie_id+"/credits?api_key=84c85237fccfe25218451c4279b3de1a";
        Log.d("url_generated",url);
        final String basePath = "https://image.tmdb.org/t/p/w300";
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("cast");
                    ArrayList<MovieCast> castArrayList = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        int id=jsonObject.getInt("cast_id");
                        String creditId=jsonObject.getString("credit_id");
                        String role=jsonObject.getString("character");
                        String imagePath=basePath+jsonObject.getString("profile_path");
                        castArrayList.add(new MovieCast(id,name,role,imagePath, creditId));
                    }
                    if(castArrayList.size() == 0)
                        Log.println(Log.ERROR,"MovieDescRepo","Cast list is empty");
                    else
                        castServerCallback.onSuccess(castArrayList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TMDB_Error",error.getMessage()+"");
            }
        });
        castQueue.add(jsonObjectRequest);
    }
}
