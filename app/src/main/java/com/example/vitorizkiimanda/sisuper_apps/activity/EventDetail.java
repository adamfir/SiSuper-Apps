package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;

import org.w3c.dom.Text;

public class EventDetail extends AppCompatActivity {
    private Bundle bundle;
    private EventClass model;
    private TextView eventName;
    private TextView eventOrganizer;
    private TextView eventDate;
    private TextView eventLocation;
    private TextView eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        bundle = getIntent().getExtras();
        model = bundle.getParcelable("model");
        System.out.println(model.getDescription());

        eventName = findViewById(R.id.name_event_detail);
        eventOrganizer = findViewById(R.id.organized_event_detail);
        eventDate = findViewById(R.id.date_event_detail);
        eventLocation = findViewById(R.id.location_event_detail);
        eventDescription = findViewById(R.id.description_event_detail);

        eventName.setText(model.getEventName());
        eventOrganizer.setText(model.getOrganized());
        eventDate.setText(model.getDate());
        eventLocation.setText(model.getEventPlace());
        eventDescription.setText(model.getDescription());
    }
}
