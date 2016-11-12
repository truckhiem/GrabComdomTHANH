package com.android.dfr.grabcondom.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dfr.grabcondom.adapter.HIVTesAdapter;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.model.HIVItem;
import com.androidquery.AQuery;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HIVTest extends BaseActivity implements LoadJSONTask_NEW.Listener, AdapterView.OnItemClickListener {

    private ListView mListView;

    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=getlisthivtest&appkey=dfr-yourhe@lthapp&filters=post_title, image, post_content, sexual_cost";

    private ArrayList<HIVItem> lstData = new ArrayList<>();

    private static final String KEY_VER = "post_title";
    private static final String KEY_NAME = "image";
    private static final String KEY_API = "post_content";

    private AQuery aq;
    private Context mContext;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hiv_test_layout);

        progressDialog = ProgressDialog.show(HIVTest.this, "", getResources().getString(R.string.loading));

        mContext = this;
        aq = new AQuery(mContext);

        mListView = (ListView) findViewById(R.id.lv_hiv_test);
        mListView.setOnItemClickListener(this);
        LoadJSONTask_NEW mLoad = new LoadJSONTask_NEW(mContext, this);
        mLoad.execute(URL, null, AQuery.METHOD_GET);

        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        Resources res = getResources();
        String[] items = new String[]{
                res.getString(R.string.all)
                , res.getString(R.string.free_consultation)
                , res.getString(R.string.consultation_fee)
                , res.getString(R.string.path)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);

    }

    @Override
    public void onLoaded(String response) {
        Log.e("khiem",response);
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject datas = obj.getJSONObject("datas");
            JSONObject data = datas.getJSONObject("data");
            JSONArray data_arr = data.getJSONArray("data");
            Log.i("", "onLoaded: " + data_arr.length());
            for (int i = 0; i < data_arr.length(); i++) {
                Gson gson = new Gson();
                HIVItem item = gson.fromJson(data_arr.getString(i),HIVItem.class);
                lstData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        loadListView();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, lstData.get(i).getPost_title(),Toast.LENGTH_LONG).show();
    }

    private void loadListView() {
        HIVTesAdapter adapter = new HIVTesAdapter(this,lstData);
        mListView.setAdapter(adapter);
    }
}