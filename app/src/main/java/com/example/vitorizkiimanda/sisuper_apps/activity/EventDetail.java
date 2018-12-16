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
        eventDescription.setText(model.getDescription()+"as;lkfjas;lkjf l;kasdflk ;asjdflk sal;kf jsal;kfj ;lksaklf jsalkjf lk;sad;fl jasl;kf jl;ask jfjas dl;fjs;lk lksd f;lkjasldk jl;sdakj ;lskdj fksajdl;fk jaslkd jflk;saj dfl;k sda;lkfj ;lskadfj ;laskdjf ;lsjd ;lkjsdlkfj sldfj ;sldjf;l jsa;ld kj;saldkj fl;sadkj f;slakj d;ls kdjslkdf j;sla lksaj d;lsd lkajs d;lksjda ;lksj lksdaj f;lksdj ;lsdkj f;aslkd jfas;ldkf jas; lkjsfda ;lks dj;salkd j;salk fjs;ld jkfas;kldjfsd;lk ja;sdlk jsd;lfj as;ldfj a;lsdjf ;asldjf ;sdljkf lksajdf;lkjsa;dlfk j;lsdkf j;lksadjf ;lksjad; flsdlk; fls;dj f;ls jd;sdfsdsd f sdf sf sad f sa fsd f ds f sa f asd f asd f sdf  sdf sd f s df dsfs df s df sa df asdf as dfsdf sd fsd f sdfsadf sa fd dasf sa df sd f sd f sdf safs adf asf sad f sfdajsdkljsdalk;f jlksdj flksdl;kfjs;ld kj;lsj l;ksdlfk jalskf lk;asd;lfk as;lkfj asd");
    }
}
