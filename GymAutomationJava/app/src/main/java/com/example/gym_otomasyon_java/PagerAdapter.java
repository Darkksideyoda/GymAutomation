package com.example.gym_otomasyon_java;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends  FragmentStateAdapter{

    private int numoftab;
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity,int numoftab) {
        super(fragmentActivity);
        this.numoftab = numoftab;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return  new profil_frag();
            case 1:
                return  new qr_frag();

            case 2:
                return new ayarlar_frag();
            default:
                return null;


        }

    }

    @Override
    public int getItemCount() {
        return numoftab;
    }
}