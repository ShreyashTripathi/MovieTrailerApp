package com.example.movietrailerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietrailerapp.ui.castInfo.CastInfoFragment;
import com.example.movietrailerapp.R;
import com.example.movietrailerapp.model.MovieCast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.CastViewHolder> {
    Context context;
    ArrayList<MovieCast> movieCastArrayList;
    FragmentManager fm;

    public MovieCastAdapter(Context context, ArrayList<MovieCast> movieCastArrayList, FragmentManager supportFragmentManager) {
        this.context = context;
        this.movieCastArrayList = movieCastArrayList;
        this.fm = supportFragmentManager;
    }

    @NonNull
    @Override
    public MovieCastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.movie_cast_card,parent,false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MovieCastAdapter.CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastAdapter.CastViewHolder holder, int position) {
        final MovieCast cast = movieCastArrayList.get(position);
        if(cast.getCastImageUrl()!=null)
            Picasso.with(context).load(cast.getCastImageUrl()).into(holder.cast_img);

        if(cast.getCastName().length() > 15)
            holder.cast_real_name.setTextSize(10.0f);
        else
            holder.cast_real_name.setTextSize(15.0f);

        if(cast.getCharacterRole().length() > 15)
            holder.cast_reel_name.setTextSize(10.0f);
        else
            holder.cast_real_name.setTextSize(15.0f);

        holder.cast_real_name.setText(cast.getCastName());
        holder.cast_reel_name.setText(cast.getCharacterRole());
        holder.cast_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CastInfoFragment fragment = CastInfoFragment.newInstance();
                fragment.setCastId(cast.getCastName());
                fragment.show(fm,"MovieCastSheet");
                //Toast.makeText(context, "Cast : " + cast.getCreditId() + " : " + cast.getCastName(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public int getItemCount() {
        return movieCastArrayList.size();
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView cast_img;
        TextView cast_real_name,cast_reel_name;
        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            cast_img = itemView.findViewById(R.id.card_movie_cast_img);
            cast_real_name = itemView.findViewById(R.id.card_movie_cast_real_name);
            cast_reel_name = itemView.findViewById(R.id.card_movie_cast_reel_name);
        }
    }
}
