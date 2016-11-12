package com.android.dfr.grabcondom.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.adapter.CommentHisAdapter;
import com.android.dfr.grabcondom.adapter.DetailProductShopAdapter;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.android.dfr.grabcondom.api.LoadJSONTask_REVIEW;
import com.android.dfr.grabcondom.model.CommentHisItem;
import com.android.dfr.grabcondom.model.DetailProductItem;
import com.androidquery.AQuery;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by MinhThanh on 11/7/16.
 */

public class DetailShopOnline extends AppCompatActivity implements View.OnClickListener, LoadJSONTask_REVIEW.Listener, LoadJSONTask_NEW.Listener, AdapterView.OnItemClickListener, TabHost.OnTabChangeListener{

    //lv
    private ListView mListView;
    private ListView mListViewReview;

    //tabhost
    private TabWidget tabWidget;
    private TabHost tabHost;
    private TabHost.TabSpec spec;

    public static final String NAME_PRODUCT = "NAME_SHOP";
    public static final String IMAGE_PRODUCT = "IMAGE_SHOP";
    public static final String VENDOR_ID_SHOP = "VENDOR_ID_SHOP";
    public static final String ENTITY_ID = "ENTITY_ID";
    public static final String PRICE_PRODUCT = "PRICE_PRODUCT";
    public static final String DESC_PRODUCT = "DESC_PRODUCT";

    //tv
    private TextView tv_update_product;

    //url newarrival
    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=getnewarrival&appkey=dfr-yourhe@lthapp&filters=description,additional_info, name, media_gallery, media_url, price, small_image&seller_id=";
    //url review vendor
    public static final String URL_REVIEW = "http://grabcondom.com/api/rest/mobileapi?action=getlistreview&appkey=dfr-yourhe@lthapp&vendor_id=";

    //array detaiProduct
    private ArrayList<DetailProductItem> lstData = new ArrayList<>();
    //array review
    private ArrayList<CommentHisItem> lstDataReview = new ArrayList<>();


    private AQuery aq;
    private Context mContext;

    //query
    private ProgressDialog progressBar;

    public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_shop_online);

        //
        progressBar = ProgressDialog.show(DetailShopOnline.this, "", "Loading...");

        //textview
        tv_update_product = (TextView) findViewById(R.id.tv_updating_product);

        //context
        mContext = this;
        aq = new AQuery(mContext);

        mListView = (ListView) findViewById(R.id.lvProductShop);
        mListView.setOnItemClickListener(this);

        mListViewReview = (ListView) findViewById(R.id.lv_Review);
//        mListViewReview.setOnItemClickListener(this);

        //id_shop
        String vendor_shop = getIntent().getStringExtra(ShopOnline.VENDOR_ID_SHOP);

        //load JSON
        LoadJSONTask_NEW mLoad = new LoadJSONTask_NEW(mContext, this);
        mLoad.execute(URL+vendor_shop, null, AQuery.METHOD_GET);

        //load review
        LoadJSONTask_REVIEW loadReview = new LoadJSONTask_REVIEW(mContext, this);
        loadReview.execute(URL_REVIEW + vendor_shop, null);


        //Tab
        tabHost = (TabHost) findViewById(R.id.tabHost_detail_shop);
        tabHost.setup();

        //tab1
        spec = tabHost.newTabSpec(getResources().getString(R.string.number_product));
        spec.setContent(R.id.tabs1);
        spec.setIndicator(getResources().getString(R.string.number_product));
        tabHost.addTab(spec);
        //tab2
        spec = tabHost.newTabSpec(getResources().getString(R.string.review));
        spec.setContent(R.id.tabs2);
        spec.setIndicator(getResources().getString(R.string.review));
        tabHost.addTab(spec);
        //tab3
        spec = tabHost.newTabSpec(getResources().getString(R.string.information));
        spec.setContent(R.id.tabs3);
        spec.setIndicator(getResources().getString(R.string.information));
        tabHost.addTab(spec);

        //background color current
        View view = tabHost.getCurrentTabView();
        view.setBackgroundResource(R.color.colorTim);
        //title color tabs
        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(getResources().getColor(R.color.colorWhite));

        tabWidget = tabHost.getTabWidget();
        tabHost.setOnTabChangedListener(this);

        String name_shop = getIntent().getStringExtra(ShopOnline.NAME_SHOP);
        String address_shop = getIntent().getStringExtra(ShopOnline.ADDRESS_SHOP);
        String image_shop = getIntent().getStringExtra(ShopOnline.IMAGE_SHOP);
        String rating_shop = getIntent().getStringExtra(ShopOnline.RATING_SHOP);
        String phone_shop = getIntent().getStringExtra(ShopOnline.PHONE_SHOP);
        String email_shop = getIntent().getStringExtra(ShopOnline.EMAIL_SHOP);


        Float rating = null;
        int result;
        if (rating_shop.equals("")){
            rating = Float.parseFloat("0");
            result = Math.round(rating);
        }else{
            rating = Float.parseFloat(rating_shop);
            result = Math.round(rating);
        }

        ImageView img1 = (ImageView) findViewById(R.id.imgView_detailshop_1);
        ImageView img2 = (ImageView) findViewById(R.id.imgView_detailshop_2);
        ImageView img3 = (ImageView) findViewById(R.id.imgView_detailshop_3);
        ImageView img4 = (ImageView) findViewById(R.id.imgView_detailshop_4);
        ImageView img5 = (ImageView) findViewById(R.id.imgView_detailshop_5);

        ImageView img_1 = (ImageView) findViewById(R.id.imgView_inforshop_1);
        ImageView img_2 = (ImageView) findViewById(R.id.imgView_inforshop_2);
        ImageView img_3 = (ImageView) findViewById(R.id.imgView_inforshop_3);
        ImageView img_4 = (ImageView) findViewById(R.id.imgView_inforshop_4);
        ImageView img_5 = (ImageView) findViewById(R.id.imgView_inforshop_5);

        if (result == 0){
            img1.setImageResource(R.drawable.star_rating_off);
            img2.setImageResource(R.drawable.star_rating_off);
            img3.setImageResource(R.drawable.star_rating_off);
            img4.setImageResource(R.drawable.star_rating_off);
            img5.setImageResource(R.drawable.star_rating_off);

            img_1.setImageResource(R.drawable.star_rating_off);
            img_2.setImageResource(R.drawable.star_rating_off);
            img_3.setImageResource(R.drawable.star_rating_off);
            img_4.setImageResource(R.drawable.star_rating_off);
            img_5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 1){
            img1.setImageResource(R.drawable.star_rating_on);
            img2.setImageResource(R.drawable.star_rating_off);
            img3.setImageResource(R.drawable.star_rating_off);
            img4.setImageResource(R.drawable.star_rating_off);
            img5.setImageResource(R.drawable.star_rating_off);

            img_1.setImageResource(R.drawable.star_rating_on);
            img_2.setImageResource(R.drawable.star_rating_off);
            img_3.setImageResource(R.drawable.star_rating_off);
            img_4.setImageResource(R.drawable.star_rating_off);
            img_5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 2){
            img1.setImageResource(R.drawable.star_rating_on);
            img2.setImageResource(R.drawable.star_rating_on);
            img3.setImageResource(R.drawable.star_rating_off);
            img4.setImageResource(R.drawable.star_rating_off);
            img5.setImageResource(R.drawable.star_rating_off);

            img_1.setImageResource(R.drawable.star_rating_on);
            img_2.setImageResource(R.drawable.star_rating_on);
            img_3.setImageResource(R.drawable.star_rating_off);
            img_4.setImageResource(R.drawable.star_rating_off);
            img_5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 3){
            img1.setImageResource(R.drawable.star_rating_on);
            img2.setImageResource(R.drawable.star_rating_on);
            img3.setImageResource(R.drawable.star_rating_on);
            img4.setImageResource(R.drawable.star_rating_off);
            img5.setImageResource(R.drawable.star_rating_off);

            img_1.setImageResource(R.drawable.star_rating_on);
            img_2.setImageResource(R.drawable.star_rating_on);
            img_3.setImageResource(R.drawable.star_rating_on);
            img_4.setImageResource(R.drawable.star_rating_off);
            img_5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 4){
            img1.setImageResource(R.drawable.star_rating_on);
            img2.setImageResource(R.drawable.star_rating_on);
            img3.setImageResource(R.drawable.star_rating_on);
            img4.setImageResource(R.drawable.star_rating_on);
            img5.setImageResource(R.drawable.star_rating_off);

            img_1.setImageResource(R.drawable.star_rating_on);
            img_2.setImageResource(R.drawable.star_rating_on);
            img_3.setImageResource(R.drawable.star_rating_on);
            img_4.setImageResource(R.drawable.star_rating_on);
            img_5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 5){
            img1.setImageResource(R.drawable.star_rating_on);
            img2.setImageResource(R.drawable.star_rating_on);
            img3.setImageResource(R.drawable.star_rating_on);
            img4.setImageResource(R.drawable.star_rating_on);
            img5.setImageResource(R.drawable.star_rating_on);

            img_1.setImageResource(R.drawable.star_rating_on);
            img_2.setImageResource(R.drawable.star_rating_on);
            img_3.setImageResource(R.drawable.star_rating_on);
            img_4.setImageResource(R.drawable.star_rating_on);
            img_5.setImageResource(R.drawable.star_rating_on);
        }


        TextView tv_name_review = (TextView) findViewById(R.id.tv_nameShop_Review);
        tv_name_review.setTextColor(getResources().getColor(R.color.colorTim));
        tv_name_review.setText(name_shop);

        TextView tv_name_infor = (TextView) findViewById(R.id.tv_nameShop_Infor);
        tv_name_infor.setTextColor(getResources().getColor(R.color.colorTim));
        tv_name_infor.setText(name_shop);

        TextView tv_phone_shop = (TextView) findViewById(R.id.tv_phone_shop_infor);
        if (phone_shop.equals("")){
            tv_phone_shop.setText(getResources().getString(R.string.updating));
        }else{
            tv_phone_shop.setText(phone_shop);
        }

        TextView tv_email_shop = (TextView) findViewById(R.id.tv_email_infor_shop);
        tv_email_shop.setText(email_shop);

        TextView tv_address = (TextView) findViewById(R.id.tv_address_infor_shop);
        tv_address.setText(address_shop);

        ImageView imgProduct = (ImageView) findViewById(R.id.imgDetailShop_Product);
        ImageView imgReview = (ImageView) findViewById(R.id.imgDetailShop_Review);
        ImageView imgInfor = (ImageView) findViewById(R.id.imgDetailShop_infor);

        new DownloadImageTask(imgProduct).execute(getResources().getString(R.string.API) + image_shop);
        new DownloadImageTask(imgReview).execute(getResources().getString(R.string.API) + image_shop);
        new DownloadImageTask(imgInfor).execute(getResources().getString(R.string.API) + image_shop);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setTitle("Detail Shop");
//        toolbar.setNavigationOnClickListener(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn_comment = (Button) findViewById(R.id.btn_Comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create custom dialog object
                final Dialog dialog = new Dialog(DetailShopOnline.this);
                //Include dialog.xml file
                dialog.setContentView(R.layout.dialog_comment_shop);
                //set dialog title
                dialog.setTitle("Minh thanh");
                dialog.show();

                RatingBar ratingBar_quality = (RatingBar) dialog.findViewById(R.id.ratingQuality);
                ratingBar_quality.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        Toast.makeText(getApplicationContext(), String.valueOf(rating), Toast.LENGTH_SHORT).show();
                    }
                });

                RatingBar ratingBar_service = (RatingBar) dialog.findViewById(R.id.ratingService);
                ratingBar_service.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        Toast.makeText(getApplicationContext(), String.valueOf(rating), Toast.LENGTH_SHORT).show();
                    }
                });

                RatingBar ratingBar_price = (RatingBar) dialog.findViewById(R.id.ratingPrice);
                ratingBar_price.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        Toast.makeText(getApplicationContext(), String.valueOf(rating), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESPONSE, "All is well!");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLoaded(String response) {

        try{
            tv_update_product.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            JSONObject obj = new JSONObject(response);
            JSONObject datas = obj.getJSONObject("datas");
            JSONArray data = datas.getJSONArray("data");
            TextView tv_number_product = (TextView) findViewById(R.id.tvProduct_detailShop);
            tv_number_product.setText(getResources().getString(R.string.number_product)+" ("+data.length()+")");
            for (int i =0; i <data.length(); i++){
                Gson gson = new Gson();
                DetailProductItem item = gson.fromJson(data.getString(i), DetailProductItem.class);
                lstData.add(item);
            }
        }catch (JSONException e){
            e.printStackTrace();
            TextView tv_number_product = (TextView) findViewById(R.id.tvProduct_detailShop);
            tv_number_product.setText(getResources().getString(R.string.number_product)+" (0)");

            tv_update_product.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
        if (progressBar.isShowing()){
            progressBar.dismiss();
        }
        loadListView();
    }

    private void loadListView() {
        DetailProductShopAdapter adapter = new DetailProductShopAdapter(this,lstData);
        mListView.setAdapter(adapter);
    }


    @Override
    public void onLoadedReview(String response) {
        Log.e("JSON REVIEW", response);
        try {
            JSONObject json = new JSONObject(response);
            JSONObject datas = json.getJSONObject("datas");
            JSONArray data = datas.getJSONArray("data");
            TextView tv_comment = (TextView) findViewById(R.id.tvComment_ReviewShop);
            tv_comment.setText(getResources().getString(R.string.comment) + " ("+data.length()+")");
            for (int i = 0; i < data.length(); i++){
                Gson gson = new Gson();
                CommentHisItem item = gson.fromJson(data.getString(i), CommentHisItem.class);
                lstDataReview.add(item);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        if (progressBar.isShowing()){
            progressBar.dismiss();
        }
        loadListViewReview();
    }

    private void loadListViewReview() {
        CommentHisAdapter adapter = new CommentHisAdapter(this,lstDataReview);
        mListViewReview.setAdapter(adapter);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra(NAME_PRODUCT, lstData.get(position).getName());
        intent.putExtra(PRICE_PRODUCT, lstData.get(position).getPrice());
        intent.putExtra(IMAGE_PRODUCT, lstData.get(position).getImage());
        intent.putExtra(DESC_PRODUCT, lstData.get(position).getDescription());
        intent.putExtra(VENDOR_ID_SHOP, lstData.get(position).getVendor_id());
        intent.putExtra(ENTITY_ID, lstData.get(position).getEntity_id());
        startActivityForResult(intent, 1);
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
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(activeColor);
        }

        // set the active tab
        tabWidget.getChildAt(tabHost.getCurrentTab()).setBackgroundColor(activeColor);
        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(inactiveColor);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
