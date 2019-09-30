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
import androidx.lifecycle.Observer;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private TVShowAdapter adapter;
    private List<TVShow> tvShowList;
    private SwipeRefreshLayout swipeContainer;
    private ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);

        pb = v.findViewById(R.id.progressBar);

        TVShowViewModel tvShowViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TVShowViewModel.class);
        tvShowViewModel.init();
        tvShowViewModel.getTVRepository().observe(this, tvResponse -> {
            List<TVShow> tvShows = tvResponse.getResults();
            tvShowList.addAll(tvShows);
            adapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
            setRecyclerView();
        });

        recyclerView = v.findViewById(R.id.list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowList = new ArrayList<>();
        adapter = new TVShowAdapter(getContext(), tvShowList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadJSON();

        swipeContainer = v.findViewById(R.id.list_fragment);
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark);
        swipeContainer.setOnRefreshListener(() -> {
            tvShowList = new ArrayList<>();
            adapter = new TVShowAdapter(getContext(), tvShowList);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            loadJSON();
        });
        super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    private void setRecyclerView() {
        if (adapter == null) {
            pb.setVisibility(View.VISIBLE);
            adapter = new TVShowAdapter(getContext(), tvShowList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void loadJSON() {
        try {
            Service apiService =
                    com.b3.movie3.Base.getBase().create(Service.class);
            Call<TVResponse> call = apiService.getTVList(BuildConfig.API_TOKEN, BuildConfig.ORI_LANGUAGE);
            call.enqueue(new Callback<TVResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVResponse> call, @NonNull Response<TVResponse> response) {
                    List<TVShow> tvShows = Objects.requireNonNull(response.body()).getResults();
                    recyclerView.setAdapter(new TVShowAdapter(getContext(), tvShows));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<TVResponse> call, @NonNull Throwable t) {
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(getContext(), R.string.fetch_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
