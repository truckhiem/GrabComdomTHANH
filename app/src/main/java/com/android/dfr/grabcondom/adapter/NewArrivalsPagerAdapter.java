package com.android.dfr.grabcondom.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.fragment.HomeScreen_1;
import com.android.dfr.grabcondom.fragment.HomeScreen_2;
import com.android.dfr.grabcondom.fragment.HomeScreen_3;
import com.android.dfr.grabcondom.fragment.NewArrivalsPagerFragment;
import com.android.dfr.grabcondom.model.NewArrivalModel;

import java.util.ArrayList;

/**
 * Created by MinhThanh on 11/12/16.
 */

public class NewArrivalsPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<NewArrivalModel> lstData;

    public NewArrivalsPagerAdapter(FragmentManager fm, ArrayList<NewArrivalModel> lstData) {
        super(fm);
        this.lstData = lstData;
    }

    @Override
    public Fragment getItem(int position) {
        NewArrivalsPagerFragment item = new NewArrivalsPagerFragment();
        item.setData(lstData, position);
        return item;
    }

    @Override
    public int getCount() {
        if(lstData.size() % 4 == 0 ){
            return lstData.size() / 4;
        }
        return lstData.size() / 4 + 1;
    }

}


