package com.android.dfr.grabcondom.api;

import android.content.Context;
import android.os.AsyncTask;

import com.android.dfr.grabcondom.ResponseHIV;
import com.android.dfr.grabcondom.model.HIVItem;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class LoadJSONTask{

    public interface Listener {
        void onLoaded(String response);
        void onError();
    }

    private Context mContext;
    private AQuery aq;
    private LoadJSONTask.Listener mListener;

    public LoadJSONTask(Context mContext, LoadJSONTask.Listener mListener){
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
        cb.method(AQuery.METHOD_POST);
        aq.ajax(url, JSONObject.class, cb);
    }


    public void processResponse(String url, JSONObject json, AjaxStatus status) {
        mListener.onLoaded(json.toString());
    }

}
