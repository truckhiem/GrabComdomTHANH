package com.android.dfr.grabcondom.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.GridView;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.adapter.NewArrivalAdapter;
import com.android.dfr.grabcondom.adapter.NewArrivalsPagerAdapter;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.android.dfr.grabcondom.model.NewArrivalModel;
import com.androidquery.AQuery;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class NewArrival extends BaseActivity implements LoadJSONTask_NEW.Listener{
    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=getnewarrival&appkey=dfr-yourhe@lthapp&filters=description,additional_info, name, media_gallery, media_url, price, small_image";

    private NewArrivalAdapter mAdapter;
    private ArrayList<String> listTitle;
    private ArrayList<Integer> listImg;
    private ArrayList<NewArrivalModel> lstDataPage = new ArrayList<>();
    private GridView gridView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_arrival_layout);
        prepareList();

        mAdapter = new NewArrivalAdapter(this, listTitle, listImg);
//        initViewPager();

        LoadJSONTask_NEW api = new LoadJSONTask_NEW(this, this);
        api.execute(URL, null, AQuery.METHOD_GET);
    }

    private void initViewPager(){
        mViewPager = (ViewPager) findViewById(R.id.pager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        NewArrivalsPagerAdapter mAdapter = new NewArrivalsPagerAdapter(getSupportFragmentManager(), lstDataPage);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageMargin(20);
        indicator.setViewPager(mViewPager);
    }

    public void prepareList(){
        listTitle = new ArrayList<String>();
        listTitle.add("india");
        listTitle.add("Brazil");
        listTitle.add("Canada");
        listTitle.add("China");
        listTitle.add("France");
        listTitle.add("Germany");
        listTitle.add("Iran");
        listTitle.add("Italy");
        listTitle.add("Japan");
        listTitle.add("Korea");
        listTitle.add("Mexico");
        listTitle.add("Netherlands");
        listTitle.add("Portugal");
        listTitle.add("Russia");
        listTitle.add("Saudi Arabia");
        listTitle.add("Spain");
        listTitle.add("Turkey");
        listTitle.add("United Kingdom");
        listTitle.add("United States");

        listImg = new ArrayList<Integer>();
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);
        listImg.add(R.drawable.arrival);

    }

    @Override
    public void onLoaded(String response) {

        try {
            JSONObject obj = new JSONObject(response);
            JSONObject objDatas = obj.getJSONObject("datas");
            JSONArray arr = objDatas.getJSONArray("data");

            for (int i = 0; i < arr.length(); i++) {
                Gson mGson = new Gson();
                NewArrivalModel model = mGson.fromJson(arr.getString(i), NewArrivalModel.class);
                lstDataPage.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initViewPager();
    }

    @Override
    public void onError() {

    }
}
