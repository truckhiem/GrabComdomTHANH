package com.android.dfr.grabcondom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.dfr.grabcondom.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

/**
 * Created by MinhThanh on 11/11/16.
 */

public class MapViewShop extends AppCompatActivity {
    private MapView mapView;
    private GoogleMap map;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map_layout);

    }

}
