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

import com.android.dfr.grabcondom.model.HIVItem;
import com.android.dfr.grabcondom.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by truckhiem on 10/27/16.
 */

public class HIVTesAdapter extends BaseAdapter {
    private ArrayList<HIVItem> lstData;
    private Context mContext;

    public HIVTesAdapter(Context mContext, ArrayList<HIVItem> lstData){
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
        final ViewHolder viewHolder;
        View view = convertView;
        if(view==null){
            view = getInfalter().inflate(R.layout.item_hivtest, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_hiv);
            viewHolder.txtPostTitle = (TextView) view.findViewById(R.id.name_hiv);
            viewHolder.txtPostContent = (TextView) view.findViewById(R.id.detail_hiv);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        // set data
        viewHolder.txtPostTitle.setText(lstData.get(position).getPost_title());
//        viewHolder.imageView.setImageResource(R.drawable.testhiv);
        new DownloadImageTask(viewHolder.imageView).execute(lstData.get(position).getImage());
        viewHolder.txtPostContent.setText(lstData.get(position).getPost_content());

        return view;
    }

    private LayoutInflater getInfalter() {
        return LayoutInflater.from(mContext);
    }

    static class ViewHolder {
        TextView txtPostTitle;
        ImageView imageView;
        TextView txtPostContent;
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
