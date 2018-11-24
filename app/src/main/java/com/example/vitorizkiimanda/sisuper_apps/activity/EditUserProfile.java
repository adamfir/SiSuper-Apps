package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import java.util.HashMap;

public class EditUserProfile extends AppCompatActivity {
    SessionManagement session;
    String Username;
    String Email;
    String Phone;
    String Address;
    String Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        //session
        session = new SessionManagement(getApplicationContext());

        //parse data
        EditText Usernames = (EditText) findViewById(R.id.name_user_edit);
        EditText Emails = (EditText) findViewById(R.id.email_user_edit);
        EditText Phones = (EditText) findViewById(R.id.phone_user_edit);
        EditText Addresses = (EditText) findViewById(R.id.address_user_edit);

        //get data
        getData();

        //settext
        Usernames.setText(Username);
        Emails.setText(Email);
        Phones.setText(Phone);
        Addresses.setText(Address);

    }

    public void getData(){
        HashMap result = session.getUserDetails();
        Username = (String) result.get("username");
        Email = (String) result.get("email");
        Address = (String) result.get("address");
        Phone = (String) result.get("phone");
        Image = (String) result.get("image");


        System.out.println("hasil " + Username);
    }
}
