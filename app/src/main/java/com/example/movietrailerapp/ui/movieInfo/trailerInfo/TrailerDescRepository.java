package com.example.movietrailerapp.ui.movieInfo.trailerInfo;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.model.MovieCast;
import com.example.movietrailerapp.model.TrailerEntity;
import com.example.movietrailerapp.utils.CastServerCallback;
import com.example.movietrailerapp.utils.TrailerServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrailerDescRepository {


    private TrailerServerCallback trailerServerCallback;
    private RequestQueue trailerQueue;

    public TrailerDescRepository(Context context) {

        trailerQueue = Volley.newRequestQueue(context);
    }

    public void getMovieTrailers(String movie_id,TrailerServerCallback trailerServerCallback)
    {
        parseTrailersJson(movie_id);
        this.trailerServerCallback = trailerServerCallback;
    }

    private void parseTrailersJson(String movie_id)
    {
        String url="https://api.themoviedb.org/3/movie/"+movie_id+"/videos?api_key=84c85237fccfe25218451c4279b3de1a";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("results");
                    ArrayList<TrailerEntity> trailerEntityArrayList = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        String key=jsonObject.getString("key");
                        String type=jsonObject.getString("type");
                        trailerEntityArrayList.add(new TrailerEntity(name,key,type));
                    }
                    trailerServerCallback.onSuccess(trailerEntityArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error: ",error.getMessage()+"");

            }
        });
        trailerQueue.add(jsonObjectRequest);
    }
}
