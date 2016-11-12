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

import com.android.dfr.grabcondom.activity.NewArrival;
import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.activity.ShopOnline;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class HomeScreen_1 extends Fragment{
    private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test", "hello");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_1_fragment, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

        LinearLayout btnArrival = (LinearLayout) view.findViewById(R.id.btnNewArrival);
        btnArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewArrival.class);
                startActivity(intent);
            }
        });

        LinearLayout btnShopOnline = (LinearLayout) view.findViewById(R.id.btnShopOnline);
        btnShopOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopOnline.class);
                startActivity(intent);
            }
        });
    }

}
