package com.android.dfr.grabcondom.model;

/**
 * Created by MinhThanh on 11/2/16.
 */

public class OrderHisItem {

    private String created_at;
    private String order_status;
    private String grand_total;
    private String shop_title;
    private String increment_id;

    public String getCreated_at(){
        return created_at;
    }
    public String getOrder_status(){
        return order_status;
    }
    public String getGrand_total(){
        return grand_total;
    }
    public String getShop_title(){
        return shop_title;
    }
    public String getIncrement_id(){
        return increment_id;
    }
}
