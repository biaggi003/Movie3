package com.b3.movie3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Objects.requireNonNull;

public class MovieFragment extends Fragment {
    private MovieAdapter movieadapter;
    private RecyclerView recyclerview;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_fragment, container, false);
        super.onViewCreated(view, savedInstanceState);

        pb = view.findViewById(R.id.progressBar);

        MovieViewModel movieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MovieViewModel.class);
        movieViewModel.init();
        movieViewModel.getMovieRepo().observe(this, movieResponse -> {
            List<Movie> movie = movieResponse.getResults();
            movies.addAll(movie);
            movieadapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
        });
        setRecyclerView();

        super.onCreateView(inflater, container, savedInstanceState);

        recyclerview = view.findViewById(R.id.list_recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        movies = new ArrayList<>();
        movieadapter = new MovieAdapter(getContext(), movies);
        recyclerview.setItemAnimator(new DefaultItemAnimator()); recyclerview.setAdapter(movieadapter);
        movieadapter.notifyDataSetChanged();
        loadJSON();

        swipeContainer = view.findViewById(R.id.list_fragment);
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark);
        swipeContainer.setOnRefreshListener(() -> {
            movies = new ArrayList<>(); movieadapter = new MovieAdapter(getContext(), movies);
            recyclerview.setItemAnimator(new DefaultItemAnimator()); recyclerview.setAdapter(movieadapter);
            movieadapter.notifyDataSetChanged();
            loadJSON();
        });
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    private void setRecyclerView() {
        if (movieadapter == null) {
            pb.setVisibility(View.VISIBLE);
            movieadapter = new MovieAdapter(getContext(), movies);
            recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerview.setAdapter(movieadapter);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setNestedScrollingEnabled(true);
        } else {
            movieadapter.notifyDataSetChanged();
        }
    }

        private void loadJSON() {
        try {
            Service apiService = com.b3.movie3.Base.getBase().create(Service.class);
            Call<MovieResponse> call = apiService.getMovieList(BuildConfig.API_TOKEN, BuildConfig.ORI_LANGUAGE);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    List<Movie> movies = requireNonNull(response.body()).getResults();
                    recyclerview.setAdapter(new MovieAdapter(getContext(), movies));
                    recyclerview.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    Log.d("Error", requireNonNull(t.getMessage()));
                    Toast.makeText(getContext(), R.string.fetch_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", requireNonNull(e.getMessage()));
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}