package com.android.dfr.grabcondom.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.model.NewArrivalModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by truckhiem on 11/12/16.
 */

public class NewArrivalGridAdapter extends BaseAdapter {
    private final ArrayList<NewArrivalModel> lstData;
    private final int windowWidth;
    private Context mContext;

    public NewArrivalGridAdapter(Context c, ArrayList<NewArrivalModel> lstData) {
        mContext = c;
        this.lstData = lstData;

        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        windowWidth = size.x;
    }

    public int getCount() {
        return lstData.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = convertView;
        if (convertView == null) {
            view = getInflater().inflate(R.layout.item_grid_new_arrivals, null);
            viewHolder = new ViewHolder();
            viewHolder.imgView = (ImageView) view.findViewById(R.id.image);
            viewHolder.txtPrice = (TextView) view.findViewById(R.id.txt_price);
            view.setTag(viewHolder);

//            viewHolder.imgView.setLayoutParams(new RelativeLayout.LayoutParams((int) this.screenWidth / 2,(int) this.screenWidth / 2));
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Picasso.with(mContext)
                .load(lstData.get(position).getImage())
                .resize(windowWidth / 3,windowWidth / 3)
                .into(viewHolder.imgView);
        viewHolder.txtPrice.setText(lstData.get(position).getPrice());
        return view;
    }

    private LayoutInflater getInflater(){
        return LayoutInflater.from(mContext);
    }

    static class ViewHolder{
        ImageView imgView;
        TextView txtPrice;

    }
}
