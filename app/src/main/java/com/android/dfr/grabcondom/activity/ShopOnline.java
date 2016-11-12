package com.android.dfr.grabcondom.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.Utils.General;
import com.android.dfr.grabcondom.adapter.ShopOnlineAdapter;
import com.android.dfr.grabcondom.api.GPSTracker;
import com.android.dfr.grabcondom.api.LoadJSONTask_NEW;
import com.android.dfr.grabcondom.model.ShopOnlineItem;
import com.androidquery.AQuery;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class ShopOnline extends AppCompatActivity implements LoadJSONTask_NEW.Listener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    TextView tv_location;
    Button btn_viewmap;

    public static final String NAME_SHOP = "NAME_SHOP";
    public static final String IMAGE_SHOP = "IMAGE_SHOP";
    public static final String RATING_SHOP = "RATING_SHOP";
    public static final String VENDOR_ID_SHOP = "NDOR_ID_SHOP";
    public static final String ADDRESS_SHOP = "ADDRESS_SHOP";
    public static final String PHONE_SHOP = "PHONE_SHOP";
    public static final String EMAIL_SHOP = "EMAIL_SHOP";


    private static final int REQUEST_RESPONSE = 1;
    private ArrayAdapter<CharSequence> adapter;

    private ListView mListView;
    //api
    public static final String URL = "http://grabcondom.com/api/rest/mobileapi?action=getnearvendors&appkey=dfr-yourhe@lthapp&longitude=106.709145&latitude=10.810583&limit=100&page=1&filters=title,telephone,address,average_rating,description,delivery,logo, entity_id,group_id, email&offer_product=condom";
    //array
    private ArrayList<ShopOnlineItem> lstData = new ArrayList<>();

    GPSTracker gps;

    private AQuery aq;
    private Context mContext;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_online_layout);

        mContext = this;
        aq = new AQuery(mContext);
        //load JSON
        LoadJSONTask_NEW mLoad = new LoadJSONTask_NEW(mContext, this);
        mLoad.execute(URL, null, AQuery.METHOD_GET);

        progressDialog = ProgressDialog.show(ShopOnline.this, "", "Loading...");

        //listview
        mListView = (ListView) findViewById(R.id.lv_shoponline);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(adapter);

        //create class object
        gps = new GPSTracker(ShopOnline.this);

        //check if GPS enabled
        if (gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat:"+ latitude + "\nLong:" + longitude, Toast.LENGTH_LONG).show();

        }else{
            gps.showSettingsAlert();
        }

        //Spinner
        Resources res = getResources();
        String[] items_product = new String[]{res.getString(R.string.condom), res.getString(R.string.gel_massage), res.getString(R.string.love_toys)};
        String[] items_district = new String[]{res.getString(R.string.tanbinh), res.getString(R.string.govap),
                res.getString(R.string.thuduc),res.getString(R.string.binhchanh), res.getString(R.string.cuchi),
                res.getString(R.string.hoocmon),res.getString(R.string.phunhuan), res.getString(R.string.binhtan),
                res.getString(R.string.tanphu),res.getString(R.string.binhthanh), res.getString(R.string.cangio),
                res.getString(R.string.nhabe),res.getString(R.string.quan1), res.getString(R.string.quan2),
                res.getString(R.string.quan3),res.getString(R.string.quan4), res.getString(R.string.quan5),
                res.getString(R.string.quan6),res.getString(R.string.quan7), res.getString(R.string.quan8),
                res.getString(R.string.quan9),res.getString(R.string.quan10), res.getString(R.string.quan11),
                res.getString(R.string.quan12)};
        String[] items_city = new String[]{res.getString(R.string.hochiminh)};

        final ArrayAdapter<String> adapter_product = new ArrayAdapter<String>(this, R.layout.spinner_item, items_product);
        final ArrayAdapter<String> adapter_district = new ArrayAdapter<String>(this, R.layout.spinner_item, items_district);
        final ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(this, R.layout.spinner_item, items_city);

        //text address
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create custom dialog object
                final Dialog dialog = new Dialog(ShopOnline.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                //Include dialog.xml file
                dialog.setContentView(R.layout.dialog_address_shop);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                //set dialog title
                dialog.show();

                final Spinner dropdown_product = (Spinner) dialog.findViewById(R.id.spinner_product);
                adapter_product.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown_product.setAdapter(adapter_product);

                Spinner dropdown_district = (Spinner) dialog.findViewById(R.id.spinner_district);
                adapter_district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown_district.setAdapter(adapter_district);

                Spinner dropdown_city = (Spinner) dialog.findViewById(R.id.spinner_city);
                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown_city.setAdapter(adapter_city);

                final EditText edt_street = (EditText) dialog.findViewById(R.id.edt_street);

                View rootView = dialog.findViewById(R.id.root_view);
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        General.hideKeyboard(mContext, edt_street);
                    }
                });

                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel_dialog);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                Button btnOK = (Button) dialog.findViewById(R.id.btnOK_dialog);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), dropdown_product.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();


                    }
                });

            }
        });

        btn_viewmap = (Button) findViewById(R.id.btn_viewmap);
        btn_viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopOnline.this, MapViewShop.class);
                startActivity(intent);
            }
        });



    }
    @Override
    public void onLoaded(String response) {
        Log.e("khiem", response);
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject datas = obj.getJSONObject("datas");
            JSONArray data = datas.getJSONArray("data");
            TextView tvNearby = (TextView) findViewById(R.id.tv_nearby);
            Resources res = getResources();
            tvNearby.setText(res.getString(R.string.nearby) + "(" + String.valueOf(data.length()) + ")");
            for (int i = 0; i < data.length(); i++){
                Gson gson = new Gson();
                ShopOnlineItem item = gson.fromJson(data.getString(i), ShopOnlineItem.class);
                lstData.add(item);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        loadListView();

    }

    private void loadListView(){
        ShopOnlineAdapter adapter = new ShopOnlineAdapter(this, lstData);
        mListView.setAdapter(adapter);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int iGroup = Integer.parseInt(lstData.get(position).getGroup_id());
        if (iGroup == 2){
        }
        if (iGroup == 4){
//            Toast.makeText(this, lstData.get(position).getAverage_rating(),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DetailShopOnline.class);
            intent.putExtra(NAME_SHOP, lstData.get(position).getTitle());
            intent.putExtra(VENDOR_ID_SHOP, lstData.get(position).getEntity_id());
            intent.putExtra(ADDRESS_SHOP, lstData.get(position).getNear_address());
            intent.putExtra(IMAGE_SHOP, lstData.get(position).getLogo());
            intent.putExtra(RATING_SHOP, lstData.get(position).getAverage_rating());
            intent.putExtra(EMAIL_SHOP, lstData.get(position).getEmail());
            intent.putExtra(PHONE_SHOP, lstData.get(position).getNear_telephone());
            startActivityForResult(intent, REQUEST_RESPONSE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_RESPONSE) {
                Toast.makeText(this, data.getStringExtra(DetailShopOnline.EXTRA_RESPONSE), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
