package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;
import com.example.vitorizkiimanda.sisuper_apps.fragment.AgendaFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.AgendaKotlinFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.BussinessProfileFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.EventListFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.ProductListFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.UserProfileFragment;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.vitorizkiimanda.sisuper_apps.R.id.business_email;
import static com.example.vitorizkiimanda.sisuper_apps.R.id.right_side;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SessionManagement session;
    String Username;
    String Email;
    String Phone;
    String Address;
    String Image;
    String Id;
    String Certificate;

    String Token;
    String ID;
    Integer numberCerf = 0;

    private Bundle bundle;
    private BusinessClass model;

    TextView Name;
    TextView Emails;

    View headers;
    JSONArray certificate;

    HashMap businessData;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        bundle = getIntent().getExtras();
        model = bundle.getParcelable("model");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        doProdukku();


        // Session Manager
        session = new SessionManagement(this);
        getDataUser();
        getDataBusiness();

        headers = navigationView.getHeaderView(0);
        Name = (TextView) headers.findViewById(R.id.business_name);
        Emails = (TextView) headers.findViewById(R.id.business_email);
        Name.setText(businessData.get("businessName").toString());
        Emails.setText(businessData.get("businessEmail").toString());


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_produkku) {
            doProdukku();
        } else if (id == R.id.nav_agenda) {
            doAgenda();

        } else if (id == R.id.nav_kegiatan) {
            doKegiatan();
        } else if (id == R.id.nav_undangan) {

        } else if (id == R.id.nav_profil_usaha) {
            doProfilUsaha();

        } else if (id == R.id.nav_profil_akun) {
            doUserProfle();

        }
        else if(id == R.id.nav_ganti_usaha){
            Intent moveIntent = new Intent(MainActivity.this, BusinessListActivity.class);
            moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(moveIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void doProdukku(){
        //fragment
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        ProductListFragment productListFragment = new ProductListFragment();
        mFragmentTransaction.replace(R.id.frame_container, productListFragment, ProductListFragment.class.getSimpleName());
        mFragmentTransaction.commit();
        //fragment ^^^
    }

    public void doKegiatan(){
        //fragment
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        EventListFragment eventListFragment = new EventListFragment();
        mFragmentTransaction.replace(R.id.frame_container, eventListFragment, EventListFragment.class.getSimpleName());
        mFragmentTransaction.commit();
        //fragment ^^^
    }

    public void doProfilUsaha(){
        //fragment
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        BussinessProfileFragment bussinessProfileFragment = new BussinessProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        bussinessProfileFragment.setArguments(bundle);
        mFragmentTransaction.replace(R.id.frame_container, bussinessProfileFragment, BussinessProfileFragment.class.getSimpleName());
        mFragmentTransaction.commit();
        //fragment
    }

    public void doUserProfle(){
        //fragment
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        mFragmentTransaction.replace(R.id.frame_container, userProfileFragment, UserProfileFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    public void doAgenda(){
        //fragment
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        AgendaKotlinFragment agendaKotlinFragment = new AgendaKotlinFragment();
        mFragmentTransaction.replace(R.id.frame_container, agendaKotlinFragment, AgendaKotlinFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
//        Intent intent = new Intent(context, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
//                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "11001", "NOTIFICATION_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId("11001");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());

    }


    public void getDataUser(){
        HashMap result = session.getUserDetails();
//        Log.d("get data profile", "masook"+result);
        Id = (String) result.get("id");
            Log.d("get data profile", "Id"+Id);
        Username = (String) result.get("username");
            Log.d("get data profile", "Username"+Username);
        Email = (String) result.get("email");
            Log.d("get data profile", "Email"+Email);
        Address = (String) result.get("address");
            Log.d("get data profile", "Address"+Address);
        Phone = (String) result.get("phone");
            Log.d("get data profile", "Phone"+Phone);
        Image = (String) result.get("image");
            Log.d("get data profile", "Image"+Image);
        Certificate = null;


        getCertificate();
        System.out.println(numberCerf);


    }

    public void getCertificate(){
        HashMap result = session.getBusiness();
        HashMap getToken = session.getUserDetails();
        Token = (String) getToken.get("token");
        ID = (String) result.get("business");

        final String url = EndPoints.ROOT_URL+"/certificates/getCertificateUser";
        StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject results = new JSONObject(response);
                            certificate = results.getJSONArray("result");
                            numberCerf = certificate.length();

                            //check data
                            if(Phone == null) sendNotification(getApplication(), "Nomor Telepon", "Lengkapi Nomor Telepon Anda", 1001);
                            if(Address == null) sendNotification(getApplication(), "Alamat", "Lengkapi Alamat Anda", 1002);
                            if(numberCerf == 0) sendNotification(getApplication(), "Sertifikat", "Lengkapi Sertifikat Anda", 1003);
                            if(numberCerf == 0) sendNotification(getApplication(), "Undangan", "Anda mendapat undangan!\nCek Undangan sekarang juga", 1004);

//                            Log.d("getCertificate", result.toString());

                        } catch (JSONException e) {
                            Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId", ID);

                return params;
            }

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer " + Token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);

    }

    public void getDataBusiness(){
        businessData = session.getBusiness();
    }

}
