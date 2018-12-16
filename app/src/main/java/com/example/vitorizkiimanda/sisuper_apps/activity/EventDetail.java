package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Boolean isAgenda;
    private String origin;
    private Button agendaButton;
    private SQLiteOpenHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        bundle = getIntent().getExtras();
        model = bundle.getParcelable("model");

        origin = "notAgenda";

        System.out.println(model.getDescription());

        eventName = findViewById(R.id.name_event_detail);
        eventOrganizer = findViewById(R.id.organized_event_detail);
        eventDate = findViewById(R.id.date_event_detail);
        eventLocation = findViewById(R.id.location_event_detail);
        eventDescription = findViewById(R.id.description_event_detail);
        agendaButton = findViewById(R.id.add_agenda_button);

        eventName.setText(model.getEventName());
        eventOrganizer.setText(model.getOrganized());
        eventDate.setText(model.getDate());
        eventLocation.setText(model.getEventPlace());
        eventDescription.setText(model.getDescription());

        checkAgenda(model.getIdEvent());
    }

    public void checkAgenda(String id){
        if(origin.equals("agenda")){
            isAgenda = true;
        }
        else {
//            database.use {
//                Log.d("checkDB", "id :" + id)
//                val result = select(FavouriteTeam.TABLE_FAVORITE_TEAM)
//                        .whereArgs("(TEAM_ID = {id})",
//                                "id" to id)
//                val favorite = result.parseList(classParser<FavouriteTeam>())
//                if (!favorite.isEmpty()) isFavorite = true
//            }

            isAgenda = false;
        }
        setAgenda();
    }

    public void setAgenda(){
        if (isAgenda){
            agendaButton.setText(R.string.hapus_agenda);
        }
        else {
            agendaButton.setText(R.string.tambahkan_ke_agenda);
        }
    }

    public void agendaHandler(View target) {
        // Do stuff
        Log.d("test","awawaw");
    }


}
