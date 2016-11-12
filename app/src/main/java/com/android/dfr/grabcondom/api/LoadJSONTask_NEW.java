package com.android.dfr.grabcondom.api;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by truckhiem on 10/27/16.
 */

public class LoadJSONTask_NEW {

    public interface Listener {
        void onLoaded(String response);
        void onError();
    }

    private  Context mContext;
    private AQuery aq;
    private LoadJSONTask_NEW.Listener mListener;

    public LoadJSONTask_NEW(Context mContext, Listener mListener){
        this.mListener = mListener;
        this.mContext = mContext;
        aq = new AQuery(mContext);
    }

    public void execute(String url, Map<String, Object> params, int method) {
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject html, AjaxStatus status) {
                processResponse(url,html,status);
            }
        };
        cb.header("Content-Type", "application/json; charset=utf-8");
        cb.header("Accept", "application/json");
        cb.params(params);
        cb.method(method);
        aq.ajax(url, JSONObject.class, cb);
    }

    public void processResponse(String url, JSONObject json, AjaxStatus status) {
        if(status.getCode() == 200){
            mListener.onLoaded(json.toString());
        }else{
            mListener.onError();
        }

    }


}
