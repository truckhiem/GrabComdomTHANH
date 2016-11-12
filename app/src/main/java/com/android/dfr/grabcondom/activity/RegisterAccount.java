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
import com.androidquery.AQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MinhThanh on 11/3/16.
 */

public class RegisterAccount extends Activity implements LoadJSONTask.Listener{

    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=signupbyemail&appkey=dfr-yourhe@lthapp&";

    private String URL_Full;

    private AQuery aq;
    private Context mContext;

    private ProgressDialog progressDialog;

    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_account_layout);

        alertDialog = new AlertDialog.Builder(this).create();

        mContext = this;
        aq = new AQuery(mContext);

        final EditText ed_email_register = (EditText) findViewById(R.id.ed_email_register);
        final EditText ed_pass_register = (EditText) findViewById(R.id.ed_pass_register);
        final EditText ed_confirm_register = (EditText) findViewById(R.id.ed_pass_1_register);
        final EditText ed_fullname_register = (EditText) findViewById(R.id.ed_full_name_register);

        final TextView tv_forgot = (TextView) findViewById(R.id.tv_forgot_register);
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPass.class);
                startActivity(intent);
            }
        });

        final Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //full infor
                if (!ed_email_register.getText().toString().equals("") || !ed_pass_register.getText().toString().equals("")
                        || !ed_confirm_register.getText().toString().equals("") || !ed_fullname_register.getText().toString().equals("")){
                    if (ed_pass_register.getText().toString().equals(ed_confirm_register.getText().toString())){
                        if (ed_pass_register.getText().toString().length() < 6){
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.mk_6kitu), Toast.LENGTH_SHORT).show();
                            alertDialog.setTitle(getResources().getString(R.string.sign_up));
                            alertDialog.setMessage(getResources().getString(R.string.mk_6kitu));
                            alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alertDialog.show();
                        }else{
                            if (isValidEmail(ed_email_register.getText().toString())){
                                progressDialog = ProgressDialog.show(RegisterAccount.this, "", getResources().getString(R.string.loading));
                                URL_Full = URL + "email=" + ed_email_register.getText().toString() + "&firstname="
                                        + ed_fullname_register.getText().toString() + "&lastname=" + ed_fullname_register.getText().toString() +
                                        "&password="+ed_pass_register.getText().toString() + "&confirmation=" + ed_confirm_register.getText().toString();
                                Log.e("URL_FULL", URL_Full);
                                postJson();
                            }else{
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.email_error), Toast.LENGTH_SHORT).show();
                                alertDialog.setTitle(getResources().getString(R.string.sign_up));
                                alertDialog.setMessage(getResources().getString(R.string.email_error));
                                alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                alertDialog.show();
                            }
                        }
                    }else{
//                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.mk_khongkhop), Toast.LENGTH_SHORT).show();
                        alertDialog.setTitle(getResources().getString(R.string.sign_up));
                        alertDialog.setMessage(getResources().getString(R.string.mk_khongkhop));
                        alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alertDialog.show();
                    }
                }else{
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.nhapdaydu_tt), Toast.LENGTH_SHORT).show();
                    alertDialog.setTitle(getResources().getString(R.string.sign_up));
                    alertDialog.setMessage(getResources().getString(R.string.nhapdaydu_tt));
                    alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                }
            }
        });

    }



    public void postJson(){
        LoadJSONTask mLoad = new LoadJSONTask(mContext, this);
        mLoad.execute(URL_Full, null);
    }

    @Override
    public void onLoaded(String response) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        //hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        Log.e("JSON post result", response);
        try{
            JSONObject obj = new JSONObject(response);
            JSONObject datas = obj.getJSONObject("datas");
            JSONObject data = datas.getJSONObject("data");
            if (data.length() == 0){
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }else {
                String token_register = data.getString("token");
                alertDialog.setTitle(getResources().getString(R.string.sign_up));
                alertDialog.setMessage(getResources().getString(R.string.dk_thanhcong));
                alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();

            }
            String massage = datas.getString("message");
            if (massage.equals("There is already an account with this email address")){
                alertDialog.setTitle(getResources().getString(R.string.sign_up));
                alertDialog.setMessage(getResources().getString(R.string.tk_tontai));
                alertDialog.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onError() {

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
