package com.example.movietrailerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.adapters.NowPlayingAdapter;
import com.example.movietrailerapp.adapters.PopularMovieAdapter;
import com.example.movietrailerapp.adapters.TopRatedMovieAdapter;
import com.example.movietrailerapp.adapters.UpcomingMovieAdapter;
import com.example.movietrailerapp.model.MovieEntity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView nowPlayingMovieRv;
    ArrayList<MovieEntity> nowPlayingMovies;
    RequestQueue nowPlayingRequestQueue;

    RecyclerView upcomingMovieRv;
    ArrayList<MovieEntity> upcomingMovies;
    RequestQueue upcomingRequestQueue;

    RecyclerView popularMovieRv;
    ArrayList<MovieEntity> popularMovies;
    RequestQueue popularRequestQueue;

    RecyclerView topRatedMovieRv;
    ArrayList<MovieEntity> topRatedMovies;
    RequestQueue topRatedRequestQueue;

    LinearLayout linearLayout;
    TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateUI();
        nowPlayingMovies = new ArrayList<>();
        upcomingMovies = new ArrayList<>();
        popularMovies = new ArrayList<>();
        topRatedMovies = new ArrayList<>();

        parseNowPlayingJSON();
        parseUpcomingJSON();
        parsePopularJSON();
        parseTopRatedJSON();


    }

    private void instantiateUI() {
        LinearLayoutManager llm1 = new LinearLayoutManager(MainActivity.this);
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);

        nowPlayingMovieRv = findViewById(R.id.now_playing_rv);
        nowPlayingMovieRv.setLayoutManager(llm1);
        nowPlayingMovieRv.setHasFixedSize(true);
        nowPlayingRequestQueue = Volley.newRequestQueue(MainActivity.this);

        LinearLayoutManager llm2 = new LinearLayoutManager(MainActivity.this);
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);

        upcomingMovieRv = findViewById(R.id.upcoming_rv);
        upcomingMovieRv.setLayoutManager(llm2);
        upcomingMovieRv.setHasFixedSize(true);
        upcomingRequestQueue = Volley.newRequestQueue(MainActivity.this);

        LinearLayoutManager llm3 = new LinearLayoutManager(MainActivity.this);
        llm3.setOrientation(LinearLayoutManager.HORIZONTAL);

        popularMovieRv = findViewById(R.id.popular_rv);
        popularMovieRv.setLayoutManager(llm3);
        popularMovieRv.setHasFixedSize(true);
        popularRequestQueue = Volley.newRequestQueue(MainActivity.this);

        LinearLayoutManager llm4 = new LinearLayoutManager(MainActivity.this);
        llm4.setOrientation(LinearLayoutManager.HORIZONTAL);

        topRatedMovieRv = findViewById(R.id.top_rated_rv);
        topRatedMovieRv.setLayoutManager(llm4);
        topRatedMovieRv.setHasFixedSize(true);
        topRatedRequestQueue = Volley.newRequestQueue(MainActivity.this);


        linearLayout = findViewById(R.id.mainActivityContainer);
        titleText = findViewById(R.id.title_text);
    }

    private void parseNowPlayingJSON() {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int movieId=item.getInt("id");
                        String originalTitle=item.getString("original_title");
                        String movieOverview=item.getString("overview");
                        int rating;
                        rating = item.getInt("vote_average");
                        String posterImagePath=basepath+item.getString("poster_path");
                        JSONArray genres=item.getJSONArray("genre_ids");
                        String releaseDate = item.getString("release_date");

                        ArrayList<Integer> genresList=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            genresList.add((Integer) genres.get(j));
                        }
                        String backdropImagePath=basepath+item.getString("backdrop_path");
                        Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        nowPlayingMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,genresList,backdropImagePath, releaseDate));
                    }
                    if(nowPlayingMovies.size() == 0)
                        Log.println(Log.ERROR,"MainActivity","Size of now playing array is 0");

                    NowPlayingAdapter nowPlayingAdapter = new NowPlayingAdapter(nowPlayingMovies,MainActivity.this);
                    nowPlayingMovieRv.setAdapter(nowPlayingAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ERROR,"MainActivity",""+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR,"MainActivity",""+error.getMessage());
            }
        });
        nowPlayingRequestQueue.add(jsonObjectRequest);
    }

    private void parseUpcomingJSON() {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int movieId=item.getInt("id");
                        String originalTitle=item.getString("original_title");
                        String movieOverview=item.getString("overview");
                        int rating;
                        rating = item.getInt("vote_average");
                        String posterImagePath=basepath+item.getString("poster_path");
                        JSONArray genres=item.getJSONArray("genre_ids");
                        String releaseDate = item.getString("release_date");

                        ArrayList<Integer> genresList=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            genresList.add((Integer) genres.get(j));
                        }
                        String backdropImagePath=basepath+item.getString("backdrop_path");
                        Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        upcomingMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,genresList,backdropImagePath, releaseDate));
                    }
                    if(upcomingMovies.size() == 0)
                        Log.println(Log.ERROR,"MainActivity","Size of upcoming array is 0");

                    UpcomingMovieAdapter upcomingMovieAdapter = new UpcomingMovieAdapter(upcomingMovies,MainActivity.this);
                    upcomingMovieRv.setAdapter(upcomingMovieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ERROR,"MainActivity",""+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR,"MainActivity",""+error.getMessage());
            }
        });

        upcomingRequestQueue.add(jsonObjectRequest);
    }

    private void parsePopularJSON() {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int movieId=item.getInt("id");
                        String originalTitle=item.getString("original_title");
                        String movieOverview=item.getString("overview");
                        int rating;
                        rating = item.getInt("vote_average");
                        String posterImagePath=basepath+item.getString("poster_path");
                        JSONArray genres=item.getJSONArray("genre_ids");
                        String releaseDate = item.getString("release_date");

                        ArrayList<Integer> genresList=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            genresList.add((Integer) genres.get(j));
                        }
                        String backdropImagePath=basepath+item.getString("backdrop_path");
                        Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        popularMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,genresList,backdropImagePath, releaseDate));
                    }
                    if(popularMovies.size() == 0)
                        Log.println(Log.ERROR,"MainActivity","Size of popular array is 0");

                    PopularMovieAdapter popularMovieAdapter = new PopularMovieAdapter(popularMovies,MainActivity.this);
                    popularMovieRv.setAdapter(popularMovieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ERROR,"MainActivity",""+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR,"MainActivity",""+error.getMessage());
            }
        });
        popularRequestQueue.add(jsonObjectRequest);
    }

    private void parseTopRatedJSON() {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int movieId=item.getInt("id");
                        String originalTitle=item.getString("original_title");
                        String movieOverview=item.getString("overview");
                        int rating;
                        rating = item.getInt("vote_average");
                        String posterImagePath=basepath+item.getString("poster_path");
                        JSONArray genres=item.getJSONArray("genre_ids");
                        String releaseDate = item.getString("release_date");

                        ArrayList<Integer> genresList=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            genresList.add((Integer) genres.get(j));
                        }
                        String backdropImagePath=basepath+item.getString("backdrop_path");
                        Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        topRatedMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,genresList,backdropImagePath, releaseDate));
                    }
                    if(topRatedMovies.size() == 0)
                        Log.println(Log.ERROR,"MainActivity","Size of topRated array is 0");

                    TopRatedMovieAdapter topRatedMovieAdapter = new TopRatedMovieAdapter(topRatedMovies,MainActivity.this);
                    topRatedMovieRv.setAdapter(topRatedMovieAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.println(Log.ERROR,"MainActivity",""+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR,"MainActivity",""+error.getMessage());
            }
        });

        topRatedRequestQueue.add(jsonObjectRequest);
    }

    public void startSearchActivity(View view) {
        startActivity(new Intent(MainActivity.this,SearchMovie.class));
    }
}