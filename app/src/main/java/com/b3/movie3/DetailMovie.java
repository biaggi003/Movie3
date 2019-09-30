package com.b3.movie3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class DetailMovie extends AppCompatActivity {
    TextView title, desc, release, lang, score;
    ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img = findViewById(R.id.img_mv);
        title = findViewById(R.id.mv_detail_title);
        desc = findViewById(R.id.mv_detail_description);
        release = findViewById(R.id.mv_detail_release);
        lang = findViewById(R.id.mv_detail_language);
        score = findViewById(R.id.mv_detail_voteAverage);

        Intent detailMovieIntent = getIntent();
        if (detailMovieIntent.hasExtra("title")) {
            String mImg = Objects.requireNonNull(getIntent().getExtras()).getString("poster_path");
            String mTitle = getIntent().getExtras().getString("title");
            getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
            String mDesc = getIntent().getExtras().getString("overview");
            String mRelease = getIntent().getExtras().getString("release_date");
            String mLang = getIntent().getExtras().getString("original_language");
            String mScore = getIntent().getExtras().getString("vote_average");

            Glide.with(this)
                    .load(mImg)
                    .into(img);
            title.setText(mTitle);
            desc.setText(mDesc);
            release.setText(mRelease);
            lang.setText(mLang);
            score.setText(mScore);

        } else {
            Toast.makeText(this, "API DATA: NOT FOUND", Toast.LENGTH_SHORT).show();
        }
    }
}
