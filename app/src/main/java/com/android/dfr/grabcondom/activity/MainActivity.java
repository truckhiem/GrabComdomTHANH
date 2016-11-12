package com.android.dfr.grabcondom.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.tv.TvInputService;
import android.service.textservice.SpellCheckerService;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.dfr.grabcondom.Facebook;
import com.android.dfr.grabcondom.R;
import com.android.dfr.grabcondom.adapter.HomePagerAdapter;
import com.android.dfr.grabcondom.fragment.TabFragment;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.widget.ShareDialog;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String Token = "tokenKey";

    CallbackManager callbackManager;
    Button share,details;
    ShareDialog shareDialog;
    LoginButton login;
    ProfilePictureView profile;
    Dialog details_dialog;
    TextView details_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){
                mDrawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.nav_email){
                    Intent intent = new Intent(getApplicationContext(), LoginEmail.class);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.information){
                    Intent intent = new Intent(getApplicationContext(), InformationApp.class);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.settings){
                    Intent intent = new Intent(getApplicationContext(), SettingsApp.class);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_register){
                    Intent intent = new Intent(getApplicationContext(), RegisterAccount.class);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.comment_his){
                    Intent intent = new Intent(getApplicationContext(), CommentHistory.class);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.order_his){
                    Intent intent = new Intent(getApplicationContext(), OrderHistory.class);
                    startActivity(intent);
                }
                if (menuItem.getItemId() == R.id.nav_fb){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    startActivity(intent);
                }

                return false;
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        Button btnMenu = (Button) findViewById(R.id.btn_menu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View navHeader = navigationView.getHeaderView(0);
        final TextView txName = (TextView) navHeader.findViewById(R.id.txName);
        final TextView txEmail = (TextView) navHeader.findViewById(R.id.txEmail);

        txEmail.setText(getIntent().getStringExtra(LoginEmail.EMAIL_LOGIN));
        txName.setText(getIntent().getStringExtra(LoginEmail.FIRST_NAME) + getIntent().getStringExtra(LoginEmail.LAST_NAME));

        if (txEmail.getText().toString().equals("") || txName.getText().toString().equals("")){
            txEmail.setText("infor@grabcondom.com");
            txName.setText("Grabcondom.com");
        }

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Name)){
//            txName.
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(Gravity.RIGHT)){
                    drawer.closeDrawer(Gravity.RIGHT);
                }else{
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });
        initViewPager();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewPager(){
        HomePagerAdapter mAdapter = new HomePagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }



}
