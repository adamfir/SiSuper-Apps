package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vitorizkiimanda.sisuper_apps.R;

public class BusinessListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);


        View actionButton = findViewById(R.id.action_button);

//        onClickListener
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(BusinessListActivity.this, MainActivity.class);
                moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(moveIntent);
            }
        });

        View addUsahaButton = findViewById(R.id.addUsaha_button);

        //to page TambahUsahaActivity
        addUsahaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(BusinessListActivity.this, TambahUsahaActivity.class);
                startActivity(moveIntent);
            }
        });
    }
}
