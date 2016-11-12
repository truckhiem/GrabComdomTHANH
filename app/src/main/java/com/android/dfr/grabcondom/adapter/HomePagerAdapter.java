package com.android.dfr.grabcondom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.dfr.grabcondom.fragment.HomeScreen_1;
import com.android.dfr.grabcondom.fragment.HomeScreen_2;
import com.android.dfr.grabcondom.fragment.HomeScreen_3;

/**
 * Created by MinhThanh on 11/12/16.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new HomeScreen_1();
            case 1 : return new HomeScreen_2();
            case 2 : return new HomeScreen_3();
            default: return new HomeScreen_1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
