package com.android.dfr.grabcondom.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.model.HIVItem;
import com.android.dfr.grabcondom.model.ShopOnlineItem;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by MinhThanh on 10/31/16.
 */

public class ShopOnlineAdapter extends BaseAdapter {
    private ArrayList<ShopOnlineItem> lstData;
    private Context mContext;

    public ShopOnlineAdapter(Context mContext, ArrayList<ShopOnlineItem> lstData){
        this.mContext = mContext;
        this.lstData = lstData;
    }
    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = convertView;
        if (view == null){
            view = getInflater().inflate(R.layout.item_shop_online, null);
            viewHolder = new ViewHolder();
            viewHolder.imgView = (ImageView) view.findViewById(R.id.imgShopOnline);
            viewHolder.tvNameShop = (TextView) view.findViewById(R.id.tvNameShopOnline);
            viewHolder.tvAddress = (TextView) view.findViewById(R.id.tv_address_shoponline);
            viewHolder.tvDelivery = (TextView) view.findViewById(R.id.tv_delivery_shoponline);
            viewHolder.img1 = (ImageView) view.findViewById(R.id.imgView_shop_1);
            viewHolder.img2 = (ImageView) view.findViewById(R.id.imgView_shop_2);
            viewHolder.img3 = (ImageView) view.findViewById(R.id.imgView_shop_3);
            viewHolder.img4 = (ImageView) view.findViewById(R.id.imgView_shop_4);
            viewHolder.img5 = (ImageView) view.findViewById(R.id.imgView_shop_5);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        ImageView imgDetail = (ImageView) view.findViewById(R.id.ic_detailShopOnline);
        String group = lstData.get(position).getGroup_id();
        int group_id = Integer.parseInt(group);
        if (group_id == 2){
            imgDetail.setVisibility(View.GONE);
        }
        if (group_id == 4){
            imgDetail.setVisibility(View.VISIBLE);
        }

        //set data
        viewHolder.tvNameShop.setText(lstData.get(position).getTitle());
        viewHolder.tvAddress.setText(lstData.get(position).getNear_address());
        String deli = lstData.get(position).getDelivery();
        if (deli.equals("0")){
            viewHolder.tvDelivery.setText(mContext.getResources().getString(R.string.delivery)+ mContext.getResources().getString(R.string.no));
//            viewHolder.tvDelivery.setText("NO");
        }else{
            viewHolder.tvDelivery.setText(mContext.getResources().getString(R.string.delivery)+ mContext.getResources().getString(R.string.yes));
        }

        String strRating = lstData.get(position).getAverage_rating();
        Float rating = null;
        int result;
        if (strRating.equals("")){
            rating = Float.parseFloat("0");
            result = Math.round(rating);
        }else{
            rating = Float.parseFloat(strRating);
            result = Math.round(rating);
        }
        if (result == 0){
            viewHolder.img1.setImageResource(R.drawable.star_rating_off);
            viewHolder.img2.setImageResource(R.drawable.star_rating_off);
            viewHolder.img3.setImageResource(R.drawable.star_rating_off);
            viewHolder.img4.setImageResource(R.drawable.star_rating_off);
            viewHolder.img5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 1){
            viewHolder.img1.setImageResource(R.drawable.star_rating_on);
            viewHolder.img2.setImageResource(R.drawable.star_rating_off);
            viewHolder.img3.setImageResource(R.drawable.star_rating_off);
            viewHolder.img4.setImageResource(R.drawable.star_rating_off);
            viewHolder.img5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 2){
            viewHolder.img1.setImageResource(R.drawable.star_rating_on);
            viewHolder.img2.setImageResource(R.drawable.star_rating_on);
            viewHolder.img3.setImageResource(R.drawable.star_rating_off);
            viewHolder.img4.setImageResource(R.drawable.star_rating_off);
            viewHolder.img5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 3){
            viewHolder.img1.setImageResource(R.drawable.star_rating_on);
            viewHolder.img2.setImageResource(R.drawable.star_rating_on);
            viewHolder.img3.setImageResource(R.drawable.star_rating_on);
            viewHolder.img4.setImageResource(R.drawable.star_rating_off);
            viewHolder.img5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 4){
            viewHolder.img1.setImageResource(R.drawable.star_rating_on);
            viewHolder.img2.setImageResource(R.drawable.star_rating_on);
            viewHolder.img3.setImageResource(R.drawable.star_rating_on);
            viewHolder.img4.setImageResource(R.drawable.star_rating_on);
            viewHolder.img5.setImageResource(R.drawable.star_rating_off);
        }
        if (result == 5){
            viewHolder.img1.setImageResource(R.drawable.star_rating_on);
            viewHolder.img2.setImageResource(R.drawable.star_rating_on);
            viewHolder.img3.setImageResource(R.drawable.star_rating_on);
            viewHolder.img4.setImageResource(R.drawable.star_rating_on);
            viewHolder.img5.setImageResource(R.drawable.star_rating_on);
        }
        String strImg =  lstData.get(position).getLogo();
        if (strImg.equals("")){
            if (group_id == 2){
                viewHolder.imgView.setImageResource(R.drawable.shoponline);
            }
            if (group_id == 4){
                viewHolder.imgView.setImageResource(R.drawable.nhathuocdefault);
            }
        }else{
            new DownloadImageTask(viewHolder.imgView).execute("http://grabcondom.com/media/"+strImg);
        }
        return view;
    }

    private LayoutInflater getInflater(){
        return LayoutInflater.from(mContext);
    }

    static class ViewHolder{
        TextView tvNameShop;
        TextView tvDelivery;
        ImageView imgView;
        TextView tvAddress;
        ImageView img1, img2, img3, img4, img5;

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
