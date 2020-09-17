package com.example.movietrailerapp.ui.castInfo.castBasicInfo;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movietrailerapp.R;
import com.example.movietrailerapp.model.CastInfo;
import com.example.movietrailerapp.model.MovieCast;
import com.example.movietrailerapp.utils.CastInfoServerCallback;
import com.example.movietrailerapp.utils.CastServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CastInfoRepository {

    private Context context;
    private CastInfoServerCallback serverCallback;
    private RequestQueue castDataQueue;

    public CastInfoRepository(Context context) {
        this.context = context;
        castDataQueue = Volley.newRequestQueue(context);
    }

    public void getCastInfo(String personName, CastInfoServerCallback serverCallback)
    {
        setCastData(personName);
        this.serverCallback = serverCallback;
    }
    private void setCastData(final String personName)
    {
        //{"page":1,"total_results":1,"total_pages":1,"results":[{"popularity":12.707,"known_for_department":"Acting","gender":1,"id":90633,"profile_path":"\/6VrWFKBlPbGFNYq5BvXVWOkHNXj.jpg","adult":false,"known_for":[{"release_date":"2017-05-30","id":297762,"vote_count":15157,"video":false,"media_type":"movie","vote_average":7.3,"title":"Wonder Woman","genre_ids":[28,12,14],"original_title":"Wonder Woman","original_language":"en","adult":false,"backdrop_path":"\/6iUNJZymJBMXXriQyFZfLAKnjO6.jpg","overview":"An Amazon princess comes to the world of Man in the grips of the First World War to confront the forces of evil and bring an end to human conflict.","poster_path":"\/gfJGlDaHuWimErCr5Ql0I8x9QSy.jpg"},{"release_date":"2016-03-23","id":209112,"vote_count":13808,"video":false,"media_type":"movie","vote_average":5.9,"title":"Batman v Superman: Dawn of Justice","genre_ids":[28,12,14],"original_title":"Batman v Superman: Dawn of Justice","original_language":"en","adult":false,"backdrop_path":"\/doiUtOHzcxXFl0GVQ2n8Ay6Pirx.jpg","overview":"Fearing the actions of a god-like Super Hero left unchecked, Gotham City’s own formidable, forceful vigilante takes on Metropolis’s most revered, modern-day savior, while the world wrestles with what sort of hero it really needs. And with Batman and Superman at war with one another, a new threat quickly arises, putting mankind in greater danger than it’s ever known before.","poster_path":"\/5UsK3grJvtQrtzEgqNlDljJW96w.jpg"},{"poster_path":"\/eifGNCSDuxJeS1loAXil5bIGgvC.jpg","id":141052,"vote_count":9564,"video":false,"media_type":"movie","adult":false,"backdrop_path":"\/jorgjEk6a0bed6jdR5wu4S6ZvRm.jpg","genre_ids":[28,12,14,878],"original_title":"Justice League","original_language":"en","title":"Justice League","vote_average":6.2,"overview":"Fuelled by his restored faith in humanity and inspired by Superman's selfless act, Bruce Wayne and Diana Prince assemble a team of metahumans consisting of Barry Allen, Arthur Curry and Victor Stone to face the catastrophic threat of Steppenwolf and the Parademons who are on the hunt for three Mother Boxes on Earth.","release_date":"2017-11-15"}],"name":"Gal Gadot"}]}

        String query = getEncodedString(personName);
        String url = "https://api.themoviedb.org/3/search/person?api_key="+context.getResources().getString(R.string.tmdb_api_key)+"&query="+query;
        final String basePath = "https://image.tmdb.org/t/p/w300";
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
                        _profile_pic = basePath+jsonObject.getString("profile_path");

                    if(jsonObject.has("id"))
                        _person_id = jsonObject.getInt("id")+"";

                    //setMovieCastData(_profile_pic,_name,_birthday,_gender,_biography,_pob);
                    //setMovieRv(_person_id);
                    serverCallback.onSuccess(new CastInfo(_name,_birthday,_gender,_biography,_pob,_profile_pic,_person_id));

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
}
