package com.android.dfr.grabcondom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.android.dfr.grabcondom.model.CommentHisItem;
import com.androidquery.AQuery;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MinhThanh on 11/3/16.
 */

public class ForgotPass extends Activity implements LoadJSONTask_NEW.Listener{

    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=forgotpassword&appkey=dfr-yourhe@lthapp&customer_email=";
    private AQuery aq;
    private Context mContext;
    Resources res;

    //alert show
    private AlertDialog alertDialog;
    //alert load
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass_layout);
        res = getResources();
        mContext = this;
        aq = new AQuery(mContext);

        alertDialog = new AlertDialog.Builder(this).create();

        final LoadJSONTask_NEW mLoad = new LoadJSONTask_NEW(mContext, this);
        final EditText ed_email_pass = (EditText) findViewById(R.id.ed_email_get_pass);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = ed_email_pass.getText().toString();
                if (a.equals("")){
//                    Toast.makeText(getApplicationContext(), String.format(res.getString(R.string.input_email_pass)), Toast.LENGTH_SHORT);
                    alertDialog.setTitle(getResources().getString(R.string.forgot_pass));
                    alertDialog.setMessage(getResources().getString(R.string.input_email_pass));
                    alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                }else{
                    if (isValidEmail(a)){
                        mLoad.execute(URL + ed_email_pass.getText().toString(), null, AQuery.METHOD_GET);
                        progressDialog = ProgressDialog.show(ForgotPass.this, "", getResources().getString(R.string.loading));
                    }else{
                        alertDialog.setTitle(getResources().getString(R.string.forgot_pass));
                        alertDialog.setMessage(getResources().getString(R.string.invalid_email));
                        alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alertDialog.show();
                    }

                }
            }
        });

    }

    public static boolean isValidEmail(String email)
    {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onLoaded(String response) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject datas = obj.getJSONObject("datas");
            String message = datas.getString("message");
            if (message.equals("Invalid email address.")){
//                Toast.makeText(getApplicationContext(), String.format(res.getString(R.string.invalid_email)), Toast.LENGTH_LONG).show();
                alertDialog.setTitle(getResources().getString(R.string.forgot_pass));
                alertDialog.setMessage(getResources().getString(R.string.invalid_email));
                alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
            }else{
//                Toast.makeText(getApplicationContext(), String.format(res.getString(R.string.check_email_forgot)), Toast.LENGTH_LONG).show();
                alertDialog.setTitle(getResources().getString(R.string.forgot_pass));
                alertDialog.setMessage(getResources().getString(R.string.check_email_forgot));
                alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }
}
