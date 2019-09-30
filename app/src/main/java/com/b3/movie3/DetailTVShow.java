package com.b3.movie3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class DetailTVShow extends AppCompatActivity {
    TextView title, desc, release, lang, score;
    ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img = findViewById(R.id.img_tv);
        title = findViewById(R.id.tv_detail_title);
        desc = findViewById(R.id.tv_detail_overview);
        release = findViewById(R.id.tv_detail_firstRelease);
        lang = findViewById(R.id.tv_detail_language);
        score = findViewById(R.id.tv_detail_userscore);

        Intent detailTvShowIntent = getIntent();
        if (detailTvShowIntent.hasExtra("name")) {
            String mImg = Objects.requireNonNull(getIntent().getExtras()).getString("poster_path");
            String mTitle = getIntent().getExtras().getString("name");
            getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));
            String mDesc = getIntent().getExtras().getString("overview");
            String mRelease = getIntent().getExtras().getString("first_air_date");
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
