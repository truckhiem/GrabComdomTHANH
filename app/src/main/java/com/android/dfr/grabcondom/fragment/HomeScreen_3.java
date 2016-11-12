package com.android.dfr.grabcondom.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.dfr.grabcondom.Facebook;
import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.activity.ContactUs;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class HomeScreen_3 extends Fragment {

    private static Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_3_fragment, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

        LinearLayout btnContactUs = (LinearLayout) view.findViewById(R.id.btnContact);
        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUs.class);
                startActivity(intent);
            }
        });

        LinearLayout btnFb = (LinearLayout) view.findViewById(R.id.btnFB);
        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Facebook.class);
                startActivity(intent);
            }
        });
    }

}
