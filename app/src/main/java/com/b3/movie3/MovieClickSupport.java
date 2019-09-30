package com.b3.movie3;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MovieClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener onItemClickListener;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                onItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private MovieClickSupport(RecyclerView view) {
        mRecyclerView = view;
        mRecyclerView.setTag(R.id.movie_click_support,this);
        RecyclerView.OnChildAttachStateChangeListener attachListener = new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                if (onItemClickListener != null) {
                    view.setOnClickListener(onClickListener);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
            }
        };
        mRecyclerView.addOnChildAttachStateChangeListener(attachListener);
    }

    static MovieClickSupport addTo(RecyclerView view) {
        MovieClickSupport support = (MovieClickSupport) view.getTag(R.id.movie_click_support);
        if (support == null) {
            support = new MovieClickSupport(view);
        }
        return support;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
}
