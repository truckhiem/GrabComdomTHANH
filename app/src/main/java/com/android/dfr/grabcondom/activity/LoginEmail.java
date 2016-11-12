package com.android.dfr.grabcondom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.api.LoadJSONTask;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.androidquery.AQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MinhThanh on 11/3/16.
 */

public class LoginEmail extends Activity implements LoadJSONTask_NEW.Listener{

    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=logincustomer&appkey=dfr-yourhe@lthapp&";

    private AQuery aq;
    private Context mContext;
    private EditText ed_email;
    private EditText ed_pass;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;


    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String TOKEN_LOGIN = "TOKEN_LOGIN";
    public static final String EMAIL_LOGIN = "EMAIL_LOGIN";
    private static final int REQUEST_RESPONSE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_email_layout);

        //alert
        alertDialog = new AlertDialog.Builder(this).create();


        mContext = this;
        aq = new AQuery(mContext);

        //edit
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_pass = (EditText) findViewById(R.id.ed_passLogin);

        final TextView tvRegister = (TextView) findViewById(R.id.tvRegister_login);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterAccount.class);
                startActivity(intent);
            }
        });

        final TextView tvForgot = (TextView) findViewById(R.id.tvForgot_login);
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPass.class);
                startActivity(intent);
            }
        });

        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail(ed_email.getText().toString())){
                    if (!ed_pass.getText().toString().equals("")){
                        postJson();
                        progressDialog = ProgressDialog.show(LoginEmail.this, "", getResources().getString(R.string.loading));
                    }
                }else{
                    alertDialog.setTitle(getResources().getString(R.string.login_t));
                    alertDialog.setMessage(getResources().getString(R.string.nhapdaydu_tt_dn));
                    alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                }

            }
        });

        final Button btnLoginFB = (Button) findViewById(R.id.btnLoginFB);
        btnLoginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), btnLoginFB.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLoaded(String response) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        //hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        Log.e("DEV",response);
        try {
            JSONObject js = new JSONObject(response);
            JSONObject datas = js.getJSONObject("datas");
            String message = datas.getString("message");
            if (message.equals("Invalid login or password.")){
                alertDialog.setTitle(getResources().getString(R.string.login_t));
                alertDialog.setMessage(getResources().getString(R.string.dangnhap_loi));
                alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
            }
            JSONObject data = datas.getJSONObject("data");
            String token = data.getString("token");
            JSONObject customer = data.getJSONObject("customer");
            String firstName = customer.getString("firstname");
            String lastName = customer.getString("lastname");
            String email = customer.getString("email");

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(FIRST_NAME, firstName);
            intent.putExtra(LAST_NAME, lastName);
            intent.putExtra(EMAIL_LOGIN, email);
            intent.putExtra(TOKEN_LOGIN, token);
            startActivityForResult(intent, REQUEST_RESPONSE);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onError() {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    public void postJson(){
        LoadJSONTask_NEW mLoad = new LoadJSONTask_NEW(mContext, this);
        mLoad.execute(URL+"email=" + ed_email.getText().toString() + "&password=" + ed_pass.getText().toString(), null, AQuery.METHOD_POST);
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

}
