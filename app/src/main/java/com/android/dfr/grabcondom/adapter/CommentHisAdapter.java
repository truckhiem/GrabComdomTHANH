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
import com.android.dfr.grabcondom.model.CommentHisItem;
import com.android.dfr.grabcondom.model.HIVItem;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by MinhThanh on 11/2/16.
 */

public class CommentHisAdapter extends BaseAdapter{


    private ArrayList<CommentHisItem> lstData;
    private Context mContext;

    public CommentHisAdapter(Context mContext, ArrayList<CommentHisItem> lstData){
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
        final CommentHisAdapter.ViewHolder viewHolder;
        View view = convertView;
        if(view==null){
            view = getInfalter().inflate(R.layout.item_comment_his, null);
            viewHolder = new CommentHisAdapter.ViewHolder();
            viewHolder.txtNickName = (TextView) view.findViewById(R.id.tv_nick_name);
            viewHolder.txtDetail = (TextView) view.findViewById(R.id.tv_comment_user);
            viewHolder.txtDate = (TextView) view.findViewById(R.id.tv_date);
            viewHolder.img1 = (ImageView) view.findViewById(R.id.star_1);
            viewHolder.img2 = (ImageView) view.findViewById(R.id.star_2);
            viewHolder.img3 = (ImageView) view.findViewById(R.id.star_3);
            viewHolder.img4 = (ImageView) view.findViewById(R.id.star_4);
            viewHolder.img5 = (ImageView) view.findViewById(R.id.star_5);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (CommentHisAdapter.ViewHolder) view.getTag();
        }

        // set data
        viewHolder.txtNickName.setText(lstData.get(position).getNickName());
        viewHolder.txtDetail.setText(lstData.get(position).getDetail());
        viewHolder.txtDate.setText(lstData.get(position).getCreated_at());
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
        return view;
    }

    private LayoutInflater getInfalter() {
        return LayoutInflater.from(mContext);
    }

    static class ViewHolder {
        TextView txtNickName;
        TextView txtDate;
        TextView txtDetail;
        ImageView img1, img2, img3, img4, img5;

    }
//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
}
