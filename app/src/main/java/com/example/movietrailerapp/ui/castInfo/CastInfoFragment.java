package com.example.movietrailerapp.ui.castInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.R;
import com.example.movietrailerapp.adapters.PopularMovieAdapter;
import com.example.movietrailerapp.model.MovieEntity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastInfoFragment extends BottomSheetDialogFragment {
    private CircleImageView profile_pic;
    private TextView name_tv,birthday_tv,gender_tv,bio_tv,pob_tv;
    private RecyclerView cast_movie_rv;
    private String personName;
    private RequestQueue castDataQueue,creditsDataQueue;
    private ArrayList<MovieEntity> movieEntities;
    private LinearLayout birthday_layout,gender_layout,pob_layout,bio_layout,known_for_layout;

    public static CastInfoFragment newInstance() {
        return new CastInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.cast_bottom_sheet,container,false);
        initializeUI(view);
        setCastData();
        return view;
    }

    private void initializeUI(View view) {
        profile_pic = view.findViewById(R.id.profile_pic_cast);
        name_tv = view.findViewById(R.id.name_cast);
        birthday_tv = view.findViewById(R.id.birthday_cast);
        gender_tv = view.findViewById(R.id.gender_cast);
        bio_tv = view.findViewById(R.id.bio_cast);
        pob_tv = view.findViewById(R.id.pob_cast);
        cast_movie_rv = view.findViewById(R.id.movies_rv_cast);
        castDataQueue = Volley.newRequestQueue(requireActivity());
        movieEntities = new ArrayList<>();
        creditsDataQueue = Volley.newRequestQueue(requireActivity());

        LinearLayoutManager llm1 = new LinearLayoutManager(requireActivity());
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        cast_movie_rv.setLayoutManager(llm1);
        cast_movie_rv.setHasFixedSize(true);

        birthday_layout = view.findViewById(R.id.birthday_layout);
        bio_layout = view.findViewById(R.id.bio_layout);
        gender_layout = view.findViewById(R.id.gender_layout);
        pob_layout = view.findViewById(R.id.pob_layout);
        known_for_layout = view.findViewById(R.id.movie_rv_layout);
    }

    public void setCastId(String personName)
    {
        this.personName = personName;
    }

    private String getEncodedString(String castName) {
        String newName = "";
        for(int i=0;i<castName.length();i++)
        {
            if(castName.charAt(i) == ' ')
                newName = newName.concat("+");
            else
                newName = newName.concat(castName.charAt(i) + "");
        }
        return newName;
    }

    private void setCastData()
    {
        Toast.makeText(requireActivity(), ""+personName, Toast.LENGTH_SHORT).show();

        //{"page":1,"total_results":1,"total_pages":1,"results":[{"popularity":12.707,"known_for_department":"Acting","gender":1,"id":90633,"profile_path":"\/6VrWFKBlPbGFNYq5BvXVWOkHNXj.jpg","adult":false,"known_for":[{"release_date":"2017-05-30","id":297762,"vote_count":15157,"video":false,"media_type":"movie","vote_average":7.3,"title":"Wonder Woman","genre_ids":[28,12,14],"original_title":"Wonder Woman","original_language":"en","adult":false,"backdrop_path":"\/6iUNJZymJBMXXriQyFZfLAKnjO6.jpg","overview":"An Amazon princess comes to the world of Man in the grips of the First World War to confront the forces of evil and bring an end to human conflict.","poster_path":"\/gfJGlDaHuWimErCr5Ql0I8x9QSy.jpg"},{"release_date":"2016-03-23","id":209112,"vote_count":13808,"video":false,"media_type":"movie","vote_average":5.9,"title":"Batman v Superman: Dawn of Justice","genre_ids":[28,12,14],"original_title":"Batman v Superman: Dawn of Justice","original_language":"en","adult":false,"backdrop_path":"\/doiUtOHzcxXFl0GVQ2n8Ay6Pirx.jpg","overview":"Fearing the actions of a god-like Super Hero left unchecked, Gotham City’s own formidable, forceful vigilante takes on Metropolis’s most revered, modern-day savior, while the world wrestles with what sort of hero it really needs. And with Batman and Superman at war with one another, a new threat quickly arises, putting mankind in greater danger than it’s ever known before.","poster_path":"\/5UsK3grJvtQrtzEgqNlDljJW96w.jpg"},{"poster_path":"\/eifGNCSDuxJeS1loAXil5bIGgvC.jpg","id":141052,"vote_count":9564,"video":false,"media_type":"movie","adult":false,"backdrop_path":"\/jorgjEk6a0bed6jdR5wu4S6ZvRm.jpg","genre_ids":[28,12,14,878],"original_title":"Justice League","original_language":"en","title":"Justice League","vote_average":6.2,"overview":"Fuelled by his restored faith in humanity and inspired by Superman's selfless act, Bruce Wayne and Diana Prince assemble a team of metahumans consisting of Barry Allen, Arthur Curry and Victor Stone to face the catastrophic threat of Steppenwolf and the Parademons who are on the hunt for three Mother Boxes on Earth.","release_date":"2017-11-15"}],"name":"Gal Gadot"}]}

        String query = getEncodedString(personName);
        String url = "https://api.themoviedb.org/3/search/person?api_key=84c85237fccfe25218451c4279b3de1a&query="+query;
        final String basepath = "https://image.tmdb.org/t/p/w300";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String _name = "",_birthday = "",_gender = "",_biography = "",_pob = "",_profile_pic = "",_person_id = "";

                    JSONArray jsonArray = response.getJSONArray("results");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if(jsonObject.has("name"))
                            _name = jsonObject.getString("name");

                        if(jsonObject.has("birthday"))
                            _birthday = jsonObject.getString("birthday");

                        if(jsonObject.has("gender"))
                            _gender = jsonObject.getInt("gender")+"";

                        if(jsonObject.has("biography"))
                            _biography = jsonObject.getString("biography");

                        if(jsonObject.has("place_of_birth"))
                            _pob = jsonObject.getString("place_of_birth");

                        if(jsonObject.has("profile_path"))
                            _profile_pic = basepath+jsonObject.getString("profile_path");

                        if(jsonObject.has("id"))
                            _person_id = jsonObject.getInt("id")+"";

                        setCastUIData(_profile_pic,_name,_birthday,_gender,_biography,_pob);
                        setMovieRv(_person_id);
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
        castDataQueue.add(jsonObjectRequest);
    }

    private void setMovieRv(String person_id) {
        String url = "https://api.themoviedb.org/3/person/"+person_id+"/movie_credits?api_key=84c85237fccfe25218451c4279b3de1a&language=en-US";
        final String basepath = "https://image.tmdb.org/t/p/w300";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("cast");
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
                            posterImagePath = basepath + jsonObject.getString("poster_path");
                        JSONArray genres = jsonObject.getJSONArray("genre_ids");
                        if(jsonObject.has("release_date"))
                            releaseDate = jsonObject.getString("release_date");

                        ArrayList<Integer> genresList=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            genresList.add((Integer) genres.get(j));
                        }

                        if(jsonObject.has("backdrop_path"))
                            backdropImagePath = basepath + jsonObject.getString("backdrop_path");

                        movieEntities.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,genresList,backdropImagePath, releaseDate));
                    }
                    if(movieEntities.size() == 0) {
                        Log.println(Log.ERROR, "CastInfoFragment", "Size of movieCast array is 0");
                        disableView(known_for_layout);
                    }else {
                        PopularMovieAdapter popularMovieAdapter = new PopularMovieAdapter(movieEntities, requireActivity());
                        cast_movie_rv.setAdapter(popularMovieAdapter);
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

    private void setCastUIData(String profile_pic_url, String name, String birthday, String gender, String biography, String pob) {
        if(!profile_pic_url.equals(""))
            Picasso.with(requireActivity()).load(profile_pic_url).into(profile_pic);
        else
            Picasso.with(requireActivity()).load(R.drawable.ic_baseline_person_24).into(profile_pic);

        if(!name.equals(""))
            name_tv.setText(name);
        else
            name_tv.setText(R.string.not_found_txt);

        if(!birthday.equals(""))
            birthday_tv.setText(birthday);
        else
            disableView(birthday_layout);

        if(!gender.equals(""))
        {
            if(gender.equals("1"))
                gender_tv.setText(R.string.female);
            else
                gender_tv.setText(R.string.male);
        }
        else
            disableView(gender_layout);

        if(!biography.equals(""))
            bio_tv.setText(biography);
        else
            disableView(bio_layout);

        if(!pob.equals(""))
            pob_tv.setText(pob);
        else
            disableView(pob_layout);
    }

    private void disableView(LinearLayout layout) {
        layout.setVisibility(View.GONE);
    }
}
