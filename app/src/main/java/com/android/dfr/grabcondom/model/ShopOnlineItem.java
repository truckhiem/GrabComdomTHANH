package com.android.dfr.grabcondom.model;

/**
 * Created by MinhThanh on 11/7/16.
 */

public class ShopOnlineItem {

    private String entity_id;
    private String near_address;
    private String near_telephone;
    private String delivery;
    private String premium;
    private String email;
    private String title;
    private String logo;
    private String average_rating;
    private String offer_product;
    private String group_id;

    public String getEntity_id(){
        return entity_id;
    }

    public String getNear_address(){
        return near_address;
    }

    public String getNear_telephone(){
        return near_telephone;
    }

    public String getDelivery(){
        return delivery;
    }

    public String getPremium(){
        return premium;
    }

    public String getEmail(){
        return email;
    }

    public String getTitle(){
        return title;
    }

    public String getLogo(){
        return logo;
    }

    private void setLogo(String logo){
        this.logo = logo;
    }

    public String getAverage_rating(){
        return average_rating;
    }

    public String getOffer_product(){
        return offer_product;
    }

    public String getGroup_id(){return group_id;}
}
