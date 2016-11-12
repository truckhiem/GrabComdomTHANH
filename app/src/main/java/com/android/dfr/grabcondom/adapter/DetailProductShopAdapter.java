package com.android.dfr.grabcondom.adapter;

import android.content.Context;
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
import com.android.dfr.grabcondom.model.DetailProductItem;
import com.android.dfr.grabcondom.model.HIVItem;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by MinhThanh on 11/8/16.
 */

public class DetailProductShopAdapter extends BaseAdapter{
    private ArrayList<DetailProductItem> lstData;
    private Context mContext;

    public DetailProductShopAdapter(Context mContext, ArrayList<DetailProductItem> lstData){
        this.mContext = mContext;
        this.lstData = lstData;
    }
    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final DetailProductShopAdapter.ViewHolder viewHolder;
        View view = convertView;
        if(view==null){
            view = getInfalter().inflate(R.layout.item_product_shop, null);
            viewHolder = new DetailProductShopAdapter.ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imgProduct);
            viewHolder.txtName = (TextView) view.findViewById(R.id.tv_name_product);
            viewHolder.txtPrice = (TextView) view.findViewById(R.id.tvPrice_product);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (DetailProductShopAdapter.ViewHolder) view.getTag();
        }

        // set data
        viewHolder.txtName.setText(lstData.get(position).getName());
//        viewHolder.imageView.setImageResource(R.drawable.testhiv);
        new DetailProductShopAdapter.DownloadImageTask(viewHolder.imageView).execute(lstData.get(position).getImage());

        Double d = Double.parseDouble(lstData.get(position).getPrice());
        DecimalFormat format = new DecimalFormat();
        String price_convert = format.format(d);
        viewHolder.txtPrice.setText(price_convert + " VND");

        return view;
    }

    private LayoutInflater getInfalter() {
        return LayoutInflater.from(mContext);
    }

    static class ViewHolder {
        TextView txtName;
        ImageView imageView;
        TextView txtPrice;
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
