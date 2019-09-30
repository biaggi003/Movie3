package com.b3.movie3;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listString = new ArrayList<>();

    PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listString.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listString.get(position);
    }

    void AddFragment(Fragment fragment) {
        listFragment.add(fragment);
        listString.add("");
    }

}
