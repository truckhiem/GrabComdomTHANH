package com.android.dfr.grabcondom.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.dfr.grabcondom.LoveStory;
import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.activity.HIVTest;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class HomeScreen_2 extends Fragment{

    private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_2_fragment, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

        LinearLayout btnHIV = (LinearLayout) view.findViewById(R.id.btnHIVTest);
        btnHIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HIVTest.class);
                startActivity(intent);
            }
        });

        LinearLayout btnLove_Story = (LinearLayout) view.findViewById(R.id.btnLoveStory);
        btnLove_Story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoveStory.class);
                startActivity(intent);
            }
        });
    }

}
