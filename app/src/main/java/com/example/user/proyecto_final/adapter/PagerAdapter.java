package com.example.user.proyecto_final.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.proyecto_final.fragment.ContactoFragment;
import com.example.user.proyecto_final.fragment.NoticiaFragment;
import com.example.user.proyecto_final.fragment.TelefonoFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NoticiaFragment();
            case 1:
                return new TelefonoFragment();
            case 2:
                return new ContactoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}