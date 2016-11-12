package com.android.dfr.grabcondom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.adapter.NewArrivalGridAdapter;
import com.android.dfr.grabcondom.custom.ExpandableHeightGridView;
import com.android.dfr.grabcondom.model.NewArrivalModel;

import java.util.ArrayList;

/**
 * Created by truckhiem on 11/12/16.
 */

public class NewArrivalsPagerFragment extends Fragment {
    private final int NUM_PICS_PER_PAGE = 4;

    private ArrayList<NewArrivalModel> lstDataGrid = new ArrayList<>();
    private int position;

    public NewArrivalsPagerFragment(){}

    public void setData(ArrayList<NewArrivalModel> lstDataGrid, int position){
        int from = position * NUM_PICS_PER_PAGE;
        int to = from + NUM_PICS_PER_PAGE;
        for (int i = from; i < to; i++) {
            this.lstDataGrid.add(lstDataGrid.get(i));
        }
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_page_new_arrivals, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpandableHeightGridView mGridView = (ExpandableHeightGridView) view.findViewById(R.id.gridview_arrivals);
        mGridView.setExpanded(true);
        NewArrivalGridAdapter adapter = new NewArrivalGridAdapter(getContext(),lstDataGrid);
        mGridView.setAdapter(adapter);
    }
}

