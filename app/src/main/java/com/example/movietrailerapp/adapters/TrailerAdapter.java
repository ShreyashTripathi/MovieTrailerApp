package com.example.movietrailerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietrailerapp.R;
import com.example.movietrailerapp.model.TrailerEntity;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    Context context;
    ArrayList<TrailerEntity> trailerEntityArrayList;

    public TrailerAdapter(Context context, ArrayList<TrailerEntity> trailerEntityArrayList) {
        this.context = context;
        this.trailerEntityArrayList = trailerEntityArrayList;
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailer_card,parent,false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerViewHolder holder, int position) {
        final TrailerEntity trailerEntity = trailerEntityArrayList.get(position);
        holder.trailer_name_tv.setText(trailerEntity.getName());
        holder.trailer_button_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v="+trailerEntity.getKey()));
                intent.putExtra("force_fullscreen",true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerEntityArrayList.size();
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder {
        TextView trailer_name_tv;
        ImageView trailer_button_iv;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailer_name_tv = itemView.findViewById(R.id.card_trailer_name);
            trailer_button_iv = itemView.findViewById(R.id.card_trailer_img_button);
        }

    }
}
