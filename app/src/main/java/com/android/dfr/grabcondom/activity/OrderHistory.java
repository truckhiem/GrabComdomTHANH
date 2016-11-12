package com.android.dfr.grabcondom.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.adapter.CommentHisAdapter;
import com.android.dfr.grabcondom.adapter.OrderHisAdapter;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.android.dfr.grabcondom.model.CommentHisItem;
import com.android.dfr.grabcondom.model.OrderHisItem;
import com.androidquery.AQuery;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MinhThanh on 11/2/16.
 */

public class OrderHistory extends BaseActivity implements LoadJSONTask_NEW.Listener{

    private ListView mListView;

    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=getlistorderbycustomer&appkey=dfr-yourhe@lthapp&token=0b26150fc70a15c883974080eff6555db61c16c6";

    private ArrayList<OrderHisItem> lstData = new ArrayList<>();

    private AQuery aq;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_his_layout);

        mContext = this;
        aq = new AQuery(mContext);

        mListView = (ListView) findViewById(R.id.lvHisOrder);
        LoadJSONTask_NEW mLoad = new LoadJSONTask_NEW(mContext, this);
        mLoad.execute(URL, null,AQuery.METHOD_GET);
    }
    @Override
    public void onLoaded(String response) {
        Log.e("khiem",response);
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject datas = obj.getJSONObject("datas");
            JSONArray data_arr = datas.getJSONArray("data");
            Log.i("", "onLoaded: " + data_arr.length());
            for (int i = 0; i < data_arr.length(); i++) {
                Gson gson = new Gson();
                OrderHisItem item = gson.fromJson(data_arr.getString(i),OrderHisItem.class);
                lstData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadListView();
    }
    //
    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    private void loadListView() {
        OrderHisAdapter adapter = new OrderHisAdapter(this,lstData);
        mListView.setAdapter(adapter);
    }

}
