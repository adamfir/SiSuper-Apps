package com.example.vitorizkiimanda.sisuper_apps.activity;

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

import com.bumptech.glide.Glide;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.fragment.AgendaFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.BussinessProfileFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.EventListFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.ProductListFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.UserProfileFragment;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import java.util.HashMap;

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

        } else if (id == R.id.nav_send) {

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
        AgendaFragment agendaFragment = new AgendaFragment();
        mFragmentTransaction.replace(R.id.frame_container, agendaFragment, AgendaFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
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
        Log.d("get data profile", "masook");

        HashMap result = session.getUserDetails();
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

        //check data
        if(Id == null || Username == null || Email == null || Address == null || Phone == null || Image == null || Certificate == null){
            sendNotification(this, "Profil",
                    "Lengkapi profil Anda", 1001);
        }

    }

}
