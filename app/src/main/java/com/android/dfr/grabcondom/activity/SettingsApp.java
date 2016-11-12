package com.android.dfr.grabcondom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dfr.grabcondom.R;

/**
 * Created by MinhThanh on 11/2/16.
 */

public class SettingsApp extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        final TextView tv = (TextView) findViewById(R.id.tvLanguage);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
