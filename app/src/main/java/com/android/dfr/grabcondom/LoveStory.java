package com.android.dfr.grabcondom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dfr.grabcondom.activity.BaseActivity;
import com.android.dfr.grabcondom.activity.ContactUs;
import com.android.dfr.grabcondom.activity.DetailShopOnline;
import com.android.dfr.grabcondom.adapter.LoveStoryAdapter;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.android.dfr.grabcondom.model.HIVItem;
import com.androidquery.AQuery;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MinhThanh on 10/27/16.
 */

public class LoveStory extends BaseActivity implements LoadJSONTask_NEW.Listener,
//        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener , TabHost.OnTabChangeListener{

    private Toolbar toolbar;

    private TabHost host;
    private TabWidget tabWidget;

    private ListView mListView;
    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=getlistlovestory&appkey=dfr-yourhe@lthapp&filters=post_title, post_excerpt, post_content, image, created_at, condom_best_blog&page=1";
    private ArrayList<HIVItem> listData = new ArrayList<>();

    private AQuery aQuery;
    private Context mContext;

    private View viewStory;

    //query
    private ProgressDialog progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.love_story_layout);

        //view story detail
        viewStory = findViewById(R.id.detail_lovestory);
        viewStory.setVisibility(View.GONE);

        //alert load
        progressBar = ProgressDialog.show(LoveStory.this, "", getResources().getString(R.string.loading));

        mContext = this;
        aQuery = new AQuery(mContext);

        mListView = (ListView) findViewById(R.id.lv_liststory);
        mListView.setOnItemClickListener(this);

        LoadJSONTask_NEW mLoadStory = new LoadJSONTask_NEW(mContext, this);
        mLoadStory.execute(URL, null, AQuery.METHOD_GET);

        host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab1
        TabHost.TabSpec spec = host.newTabSpec("Story");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Story");
        host.addTab(spec);

        //Tab2
        spec = host.newTabSpec("List Story");
        spec.setContent(R.id.tab2);
        spec.setIndicator("List Story");
        host.addTab(spec);

        //background color current
        View view = host.getCurrentTabView();
        view.setBackgroundResource(R.color.colorTim);
        //title color tabs
        TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(getResources().getColor(R.color.colorWhite));

        tabWidget = host.getTabWidget();
        host.setOnTabChangedListener(this);

    }

    @Override
    public void onLoaded(String response){
        Log.e("Json ", response);
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject datas = obj.getJSONObject("datas");
            JSONArray top_love_story = obj.getJSONArray("top_love_story");
            TextView tvName = (TextView) findViewById(R.id.tvNameStory);
            TextView tvDetail = (TextView) findViewById(R.id.tvDetailStory);
            ImageView imgStory = (ImageView) findViewById(R.id.imgStory);
            for (int i = 0; i < top_love_story.length(); i ++){
                JSONObject js = top_love_story.getJSONObject(i);
                String postTitle = js.getString("post_title");
                tvName.setText(postTitle);
                String postContent = js.getString("post_content");
                tvDetail.setText(postContent);
                String img = js.getString("image");
                new DownloadImageFromInternet(imgStory).execute(img);
            }

            JSONArray data = datas.getJSONArray("data");
            for (int i = 0; i < data.length(); i++){
                Gson gson = new Gson();
                HIVItem item = gson.fromJson(data.getString(i), HIVItem.class);
                listData.add(item);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        loadLV_LS();
    }

    @Override
    public void onError(){
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    private void loadLV_LS(){
        if (progressBar.isShowing()){
            progressBar.dismiss();
        }
        LoveStoryAdapter adapter = new LoveStoryAdapter(this, listData);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        viewStory.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        TextView tv_name_detail = (TextView) findViewById(R.id.tvNameStory_detail);
        tv_name_detail.setText(listData.get(i).getPost_title());
        TextView tv_desc = (TextView) findViewById(R.id.tvDetailStory_detail);
        tv_desc.setText(listData.get(i).getPost_content());
        ImageView img = (ImageView) findViewById(R.id.imgStory_detail);
        new DownloadImageFromInternet(img).execute(listData.get(i).getImage());
    }

    @Override
    public void onTabChanged(String tabId) {
        colorTabs();
    }

    private void colorTabs() {
        int inactiveColor = getResources().getColor(R.color.colorWhite);
        int activeColor = getResources().getColor(R.color.colorTim);
        // set the inactive tabs
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            tabWidget.getChildAt(i).setBackgroundColor(inactiveColor);
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(activeColor);
        }

        // set the active tab
        tabWidget.getChildAt(host.getCurrentTab()).setBackgroundColor(activeColor);
        TextView tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(inactiveColor);
    }

    //Download Image
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
