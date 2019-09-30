package com.b3.movie3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        TabLayout tablayout = findViewById(R.id.tabLayout);
        ViewPager viewpager = findViewById(R.id.viewPager);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new MovieFragment());
        adapter.AddFragment(new TVShowFragment());

        viewpager.setAdapter(adapter); tablayout.setupWithViewPager(viewpager);
        Objects.requireNonNull(tablayout.getTabAt(0)).setText(R.string.movie);
        Objects.requireNonNull(tablayout.getTabAt(1)).setText(R.string.tvshow);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.language_settings){
            showLanguageSettingsDialog(); }
        return super.onOptionsItemSelected(item);
    }

    private void showLanguageSettingsDialog() {
        final String[] langItems = {"English", "Indonesia"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.select_language).setSingleChoiceItems(langItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    setLocale("en"); recreate(); }
                else if(i == 1){
                    setLocale("id");recreate(); }
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang); Locale.setDefault(locale);
        Configuration config = new Configuration(); config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("App_Lang", lang); editor.apply();
    }

    public void loadLocale() {
        SharedPreferences sharedpreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sharedpreferences.getString("App_Lang",""); setLocale(language);
    }
}