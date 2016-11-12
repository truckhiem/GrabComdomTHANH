package com.android.dfr.grabcondom.model;

/**
 * Created by MinhThanh on 11/8/16.
 */

public class DetailProductItem {

    private String image;
    private String price;
    private String name;
    private String description;
    private String vendor_id;
    private String entity_id;

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getVendor_id(){
        return vendor_id;
    }

    public String getEntity_id(){
        return entity_id;
    }
}
