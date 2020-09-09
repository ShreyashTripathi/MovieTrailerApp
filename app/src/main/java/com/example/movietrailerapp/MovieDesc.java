package com.example.movietrailerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.adapters.MovieCastAdapter;
import com.example.movietrailerapp.adapters.TrailerAdapter;
import com.example.movietrailerapp.model.MovieCast;
import com.example.movietrailerapp.model.TrailerEntity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDesc extends AppCompatActivity {

    ImageView movie_back_iv,movie_poster_iv;
    TextView movie_name_tv,movie_overview_tv,movie_rating_tv;
    RecyclerView movie_cast_rv, movie_trailer_rv;
    ArrayList<MovieCast> castArrayList;
    ArrayList<TrailerEntity> trailerEntityArrayList;
    RequestQueue castQueue,trailerQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_desc);
        instantiateUI();
        Intent intent = getIntent();
        Picasso.with(this).load(intent.getStringExtra("backdrop_url")).fit().into(movie_back_iv);
        Picasso.with(this).load(intent.getStringExtra("poster_url")).into(movie_poster_iv);
        String movie_title = intent.getStringExtra("movie_title");
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

    private void instantiateUI() {
        movie_back_iv = findViewById(R.id.desc_movie_back_img);
        movie_poster_iv = findViewById(R.id.desc_movie_poster);
        movie_poster_iv.bringToFront();
        movie_name_tv = findViewById(R.id.desc_movie_title);
        movie_overview_tv = findViewById(R.id.desc_movie_overview);
        movie_rating_tv = findViewById(R.id.desc_movie_ratings);
        movie_cast_rv = findViewById(R.id.desc_cast_rv);
        movie_trailer_rv = findViewById(R.id.desc_trailers_rv);
        castArrayList = new ArrayList<>();
        trailerEntityArrayList = new ArrayList<>();
        castQueue = Volley.newRequestQueue(MovieDesc.this);
        trailerQueue = Volley.newRequestQueue(MovieDesc.this);

        LinearLayoutManager llm1 = new LinearLayoutManager(MovieDesc.this);
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        movie_cast_rv.setLayoutManager(llm1);
        movie_cast_rv.setHasFixedSize(true);

        LinearLayoutManager llm2 = new LinearLayoutManager(MovieDesc.this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        movie_trailer_rv.setLayoutManager(llm2);
        movie_trailer_rv.setHasFixedSize(true);

    }

    private void getAllCasts(String movie_id) {
        String url="https://api.themoviedb.org/3/movie/"+movie_id+"/credits?api_key=6bab6920aae24c6f79377a7786622ab6";
        Log.d("url_generated",url);
        final String basepath = "https://image.tmdb.org/t/p/w300";
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("cast");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        int id=jsonObject.getInt("cast_id");
                        String role=jsonObject.getString("character");
                        String imagepath=basepath+jsonObject.getString("profile_path");
                        castArrayList.add(new MovieCast(id,name,role,imagepath));
                    }
                    if(castArrayList.size() == 0)
                        Log.println(Log.ERROR,"MovieDesc","Cast list is empty");

                    MovieCastAdapter movieCastAdapter = new MovieCastAdapter(MovieDesc.this,castArrayList,getSupportFragmentManager());
                    movie_cast_rv.setAdapter(movieCastAdapter);

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

    private void getAllTrailers(String movie_id)
    {
        String url="https://api.themoviedb.org/3/movie/"+movie_id+"/videos?api_key=6bab6920aae24c6f79377a7786622ab6";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        String key=jsonObject.getString("key");
                        String type=jsonObject.getString("type");
                        trailerEntityArrayList.add(new TrailerEntity(name,key,type));
                    }
                    TrailerAdapter trailerAdapter = new TrailerAdapter(MovieDesc.this,trailerEntityArrayList);
                    movie_trailer_rv.setAdapter(trailerAdapter);

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