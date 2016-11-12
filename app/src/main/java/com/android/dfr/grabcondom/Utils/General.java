package com.android.dfr.grabcondom.Utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by truckhiem on 11/12/16.
 */

public class General {

    public static void hideKeyboard(Context mContext, EditText edtView){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtView.getWindowToken(), 0);
    }
}
