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

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int i) {
        holder.title.setText(movieList.get(i).getTitle());
        holder.release.setText(movieList.get(i).getReleaseDate());
        holder.rating.setText(Double.toString(movieList.get(i).getVoteAverage()));
        holder.description.setText(movieList.get(i).getOverview());

        Glide.with(context)
                .load(movieList.get(i).getPosterPath())
                .into(holder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, release, rating;
        ImageView imgMovie;

        MovieViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.movie_title);
            release = view.findViewById(R.id.movie_release);
            rating = view.findViewById(R.id.movie_rating);
            description = view.findViewById(R.id.movie_desc);
            imgMovie = view.findViewById(R.id.movie_photo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int q = getAdapterPosition();
                    if (q != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailMovie.class);
                        intent.putExtra("poster_path", movieList.get(q).getPosterPath());
                        intent.putExtra("release_date", movieList.get(q).getReleaseDate());
                        intent.putExtra("vote_average", Double.toString(movieList.get(q).getVoteAverage()));
                        intent.putExtra("original_language", movieList.get(q).getOriginalLanguage());
                        intent.putExtra("title", movieList.get(q).getTitle());
                        intent.putExtra("overview", movieList.get(q).getOverview());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
