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
import com.android.dfr.grabcondom.model.HIVItem;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by MinhThanh on 10/31/16.
 */

public class LoveStoryAdapter extends BaseAdapter{
    private ArrayList<HIVItem> listData;
    private Context mContext;

    public LoveStoryAdapter(Context mContext, ArrayList<HIVItem> listData){
        this.mContext = mContext;
        this.listData = listData;
    }


    @Override
    public int getCount() {
        return listData.size();
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
        final ViewHolderLS viewHolderLS;
        View view = convertView;
        if (view == null){
            view = getInfalter().inflate(R.layout.item_love_story, null);
            viewHolderLS = new ViewHolderLS();
            viewHolderLS.imgLove = (ImageView) view.findViewById(R.id.imgListStory);
            viewHolderLS.txtPostTitle = (TextView) view.findViewById(R.id.tv_title_ls);
            viewHolderLS.txtPostExcerpt = (TextView) view.findViewById(R.id.tv_excerpt_ls);
            view.setTag(viewHolderLS);
        }else{
            viewHolderLS = (ViewHolderLS) view.getTag();
        }

        //set data
        viewHolderLS.txtPostTitle.setText(listData.get(position).getPost_title());
        viewHolderLS.txtPostExcerpt.setText(listData.get(position).getPost_excerpt());
        new DownloadImageTask(viewHolderLS.imgLove).execute(listData.get(position).getImage());

        return view;
    }

    private LayoutInflater getInfalter(){
        return LayoutInflater.from(mContext);
    }

    static class ViewHolderLS{
        TextView txtPostTitle;
        ImageView imgLove;
        TextView txtPostExcerpt;
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
