package com.android.dfr.grabcondom.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.api.LoadJSONTask;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.androidquery.AQuery;
import com.google.common.base.Converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by MinhThanh on 11/10/16.
 */

public class DetailProduct extends Activity implements TabHost.OnTabChangeListener{
    //tabhost
    private TabWidget tabWidget;
    private TabHost tabHost;
    private TabHost.TabSpec spec;

    private AQuery aq;
    private Context mContext;

    private LinearLayout llGrallery;
    private ImageView t1ourstoreContentImg;

    //query
    private ProgressDialog progressBar;


    //API
    public static final String URL_SELLER = "http://grabcondom.com/api/rest/mobileapi?action=getdetailvendor&appkey=dfr-yourhe@lthapp&filters=address,logo, title,telephone, email, premium, average_rating&seller_id=";
    //API image
    public static final String URL_IMAGE = "http://grabcondom.com/api/rest/mobileapi?action=getproductgalleries&appkey=dfr-yourhe@lthapp&product_id=";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product_layout);

        //
        llGrallery = (LinearLayout) findViewById(R.id.llGallery);

        progressBar = ProgressDialog.show(DetailProduct.this, "", "Loading...");

        //context
        mContext = this;
        aq = new AQuery(mContext);

        //Tab
        tabHost = (TabHost) findViewById(R.id.tabHost_detail_shop);
        tabHost.setup();

        //tab1
        spec = tabHost.newTabSpec(getResources().getString(R.string.description));
        spec.setContent(R.id.tabs1);
        spec.setIndicator(getResources().getString(R.string.description));
        tabHost.addTab(spec);
        //tab2
        spec = tabHost.newTabSpec(getResources().getString(R.string.image));
        spec.setContent(R.id.tabs2);
        spec.setIndicator(getResources().getString(R.string.image));
        tabHost.addTab(spec);
        //tab3
        spec = tabHost.newTabSpec(getResources().getString(R.string.seller));
        spec.setContent(R.id.tabs3);
        spec.setIndicator(getResources().getString(R.string.seller));
        tabHost.addTab(spec);

        //background color current
        View view = tabHost.getCurrentTabView();
        view.setBackgroundResource(R.color.colorTim);
        //title color tabs
        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(getResources().getColor(R.color.colorWhite));

        tabWidget = tabHost.getTabWidget();
        tabHost.setOnTabChangedListener(this);

        String name = getIntent().getStringExtra(DetailShopOnline.NAME_PRODUCT);
        String image = getIntent().getStringExtra(DetailShopOnline.IMAGE_PRODUCT);
        String desc = getIntent().getStringExtra(DetailShopOnline.DESC_PRODUCT);
        String entity_id_product = getIntent().getStringExtra(DetailShopOnline.ENTITY_ID);

        //convert price
        String price = getIntent().getStringExtra(DetailShopOnline.PRICE_PRODUCT);
        Double d = Double.parseDouble(price);
        DecimalFormat format = new DecimalFormat();
        String price_convert = format.format(d);

        String vendor_id = getIntent().getStringExtra(DetailShopOnline.VENDOR_ID_SHOP);

        //load JSON
        LoadJSONTask_NEW mLoad = new LoadJSONTask_NEW(mContext, new LoadJSONTask_NEW.Listener(){
            @Override
            public void onLoaded(String response) {
                Log.e("JSON", response);
                try {
                    JSONObject js = new JSONObject(response);
                    JSONObject datas = js.getJSONObject("datas");
                    JSONObject data = datas.getJSONObject("data");
                    TextView tv_address = (TextView) findViewById(R.id.tv_address_seller);
                    TextView tv_name = (TextView) findViewById(R.id.tv_name_product_seller);
                    TextView tv_email = (TextView) findViewById(R.id.tv_email_product_seller);
                    TextView tv_phone = (TextView) findViewById(R.id.tv_phone_seller);
                    ImageView im1 = (ImageView) findViewById(R.id.imgView_seller_1);
                    ImageView im2 = (ImageView) findViewById(R.id.imgView_seller_2);
                    ImageView im3 = (ImageView) findViewById(R.id.imgView_seller_3);
                    ImageView im4 = (ImageView) findViewById(R.id.imgView_seller_4);
                    ImageView im5 = (ImageView) findViewById(R.id.imgView_seller_5);

                    //logo vendor
                    ImageView seller_img = (ImageView) findViewById(R.id.img_product_seller);
                    String img_seller = data.getString("logo");
                    new DownloadImageTask(seller_img).execute(getResources().getString(R.string.API)+img_seller);

                    String premium = data.getString("premium");
                    tv_address.setText(data.getString("address"));
                    tv_name.setText(data.getString("title"));
                    if (premium.equals("1")){
                        tv_phone.setText(data.getString("telephone"));
                        tv_email.setText(data.getString("email"));
                    }else{
                        tv_phone.setVisibility(View.GONE);
                        tv_email.setVisibility(View.GONE);
                    }

                    //rating
                    String rating_vendor = data.getString("average_rating");
                    Float rating = null;
                    int result;
                    if (rating_vendor.equals("")){
                        rating = Float.parseFloat("0");
                        result = Math.round(rating);
                    }else{
                        rating = Float.parseFloat(rating_vendor);
                        result = Math.round(rating);
                    }
                    if (result == 0){
                        im1.setImageResource(R.drawable.star_rating_off);
                        im2.setImageResource(R.drawable.star_rating_off);
                        im3.setImageResource(R.drawable.star_rating_off);
                        im4.setImageResource(R.drawable.star_rating_off);
                        im5.setImageResource(R.drawable.star_rating_off);

                    }
                    if (result == 1){
                        im1.setImageResource(R.drawable.star_rating_on);
                        im2.setImageResource(R.drawable.star_rating_off);
                        im3.setImageResource(R.drawable.star_rating_off);
                        im4.setImageResource(R.drawable.star_rating_off);
                        im5.setImageResource(R.drawable.star_rating_off);
                    }
                    if (result == 2){
                        im1.setImageResource(R.drawable.star_rating_on);
                        im2.setImageResource(R.drawable.star_rating_on);
                        im3.setImageResource(R.drawable.star_rating_off);
                        im4.setImageResource(R.drawable.star_rating_off);
                        im5.setImageResource(R.drawable.star_rating_off);
                    }
                    if (result == 3){
                        im1.setImageResource(R.drawable.star_rating_on);
                        im2.setImageResource(R.drawable.star_rating_on);
                        im3.setImageResource(R.drawable.star_rating_on);
                        im4.setImageResource(R.drawable.star_rating_off);
                        im5.setImageResource(R.drawable.star_rating_off);
                    }
                    if (result == 4){
                        im1.setImageResource(R.drawable.star_rating_on);
                        im2.setImageResource(R.drawable.star_rating_on);
                        im3.setImageResource(R.drawable.star_rating_on);
                        im4.setImageResource(R.drawable.star_rating_on);
                        im5.setImageResource(R.drawable.star_rating_off);
                    }
                    if (result == 5){
                        im1.setImageResource(R.drawable.star_rating_on);
                        im2.setImageResource(R.drawable.star_rating_on);
                        im3.setImageResource(R.drawable.star_rating_on);
                        im4.setImageResource(R.drawable.star_rating_on);
                        im5.setImageResource(R.drawable.star_rating_on);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });
        mLoad.execute(URL_SELLER + vendor_id, null, AQuery.METHOD_GET);
        //load JSON Image
        LoadJSONTask_NEW loadImg = new LoadJSONTask_NEW(mContext, new LoadJSONTask_NEW.Listener() {
            @Override
            public void onLoaded(String response) {
                if (progressBar.isShowing()){
                    progressBar.dismiss();
                }
                try {
                    JSONObject js = new JSONObject(response);
                    JSONObject datas = js.getJSONObject("datas");
                    JSONArray data = datas.getJSONArray("data");
//                    ImageView img = (ImageView) findViewById(R.id.img_product_seller);
                    for (int i = 0; i<data.length(); i++){
                        String a = data.getString(i);
                        Log.e("Image", a);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });
        loadImg.execute(URL_IMAGE + entity_id_product, null, AQuery.METHOD_GET);

        TextView tv_name = (TextView) findViewById(R.id.tv_name_detail_product);
        tv_name.setText(name);

        TextView tv_price = (TextView) findViewById(R.id.tv_price_detail_product);
        tv_price.setText(price_convert + " " +getResources().getString(R.string.price_vnd));

        TextView tv_desc = (TextView) findViewById(R.id.tv_desc_product);
        tv_desc.setText(desc);

        ImageView img = (ImageView) findViewById(R.id.img_detail_product);
        new DownloadImageTask(img).execute(image);

        TextView tv_detail = (TextView) findViewById(R.id.detail_seller);
        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
