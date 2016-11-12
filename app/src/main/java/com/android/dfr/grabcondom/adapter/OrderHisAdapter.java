package com.android.dfr.grabcondom.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.model.OrderHisItem;

import java.util.ArrayList;

/**
 * Created by MinhThanh on 11/2/16.
 */

public class OrderHisAdapter extends BaseAdapter {


    private ArrayList<OrderHisItem> lstData;
    private Context mContext;

    public OrderHisAdapter(Context mContext, ArrayList<OrderHisItem> lstData){
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
        final OrderHisAdapter.ViewHolder viewHolder;
        View view = convertView;
        if(view==null){
            view = getInfalter().inflate(R.layout.item_his_order, null);
            viewHolder = new OrderHisAdapter.ViewHolder();
            viewHolder.txtCodeOrder = (TextView) view.findViewById(R.id.tvCodeOrders);
            viewHolder.txtOrderDate = (TextView) view.findViewById(R.id.tvDetailOrderDate);
            viewHolder.txtTotal = (TextView) view.findViewById(R.id.tvDetailTotalMoney);
            viewHolder.txtShop = (TextView) view.findViewById(R.id.tvDetailShop);
            viewHolder.txtStatus = (TextView) view.findViewById(R.id.tvDetailStatus);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (OrderHisAdapter.ViewHolder) view.getTag();
        }

        // set data
        viewHolder.txtCodeOrder.setText(Html.fromHtml("<u>"+"#"+ lstData.get(position).getIncrement_id()+"</u>"));
        viewHolder.txtOrderDate.setText(lstData.get(position).getCreated_at());
        viewHolder.txtTotal.setText(lstData.get(position).getGrand_total());
        viewHolder.txtShop.setText(lstData.get(position).getShop_title());
        viewHolder.txtStatus.setText(lstData.get(position).getOrder_status());

        return view;
    }

    private LayoutInflater getInfalter() {
        return LayoutInflater.from(mContext);
    }

    static class ViewHolder {
        TextView txtCodeOrder;
        TextView txtOrderDate;
        TextView txtTotal;
        TextView txtStatus;
        TextView txtShop;

    }
}
