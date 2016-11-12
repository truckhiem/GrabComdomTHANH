package com.android.dfr.grabcondom.api;

import android.content.Context;

import com.android.dfr.grabcondom.activity.DetailShopOnline;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by MinhThanh on 11/9/16.
 */

public class LoadJSONTask_REVIEW {

    public interface Listener {
        void onLoadedReview(String response);
        void onError();
    }

    private Context mContext;
    private AQuery aq;
    private LoadJSONTask_REVIEW.Listener mListener;

    public LoadJSONTask_REVIEW(Context mContext, LoadJSONTask_REVIEW.Listener mListener){
        this.mListener = mListener;
        this.mContext = mContext;
        aq = new AQuery(mContext);
    }

    public void execute(String url, Map<String, Object> params) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                processResponse(url,html,status);
            }
        };
        cb.header("Content-Type", "application/json; charset=utf-8");
        cb.header("Accept", "application/json");
        cb.params(params);
//        cb.method(AQuery.METHOD_POST);
        aq.ajax(url, JSONObject.class, cb);
    }


    public void processResponse(String url, JSONObject json, AjaxStatus status) {
        mListener.onLoadedReview(json.toString());
    }

}
