package com.android.dfr.grabcondom.model;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class HIVItem {

    private String post_title;
    private String image;
    private String post_content;
    private String post_excerpt;


    public String getPost_title(){
        return post_title;
    }

    public String getImage(){
        return image;
    }

    private void setImage(String image){
        this.image = image;
    }

    public String getPost_content(){
        return post_content;
    }

    public String getPost_excerpt(){return post_excerpt;}

}
