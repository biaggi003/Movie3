package com.b3.movie3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TvViewHolder> {
    private Context context;
    private List<TVShow> tvShowList;

    public TVShowAdapter(Context context, List<TVShow> tvShowList) {
        this.context = context;
        this.tvShowList = tvShowList;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new TvViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int i) {
        holder.title.setText(tvShowList.get(i).getName());
        holder.release.setText(tvShowList.get(i).getFirstAirDate());
        holder.rating.setText(Double.toString(tvShowList.get(i).getVoteAverage()));
        holder.description.setText(tvShowList.get(i).getOverview());

        Glide.with(context)
                .load(tvShowList.get(i).getPosterPath())
                .placeholder(R.color.colorPrimary)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, release, rating;
        ImageView photo;

        TvViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.movie_title);
            release = view.findViewById(R.id.movie_release);
            rating = view.findViewById(R.id.movie_rating);
            description = view.findViewById(R.id.movie_desc);
            photo = view.findViewById(R.id.movie_photo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    if (i != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailTVShow.class);
                        intent.putExtra("name", tvShowList.get(i).getName());
                        intent.putExtra("poster_path", tvShowList.get(i).getPosterPath());
                        intent.putExtra("first_air_date", tvShowList.get(i).getFirstAirDate());
                        intent.putExtra("vote_average", Double.toString(tvShowList.get(i).getVoteAverage()));
                        intent.putExtra("original_language", tvShowList.get(i).getOriginalLanguage());
                        intent.putExtra("overview", tvShowList.get(i).getOverview());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
