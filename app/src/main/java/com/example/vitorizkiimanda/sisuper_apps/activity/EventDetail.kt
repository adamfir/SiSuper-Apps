package com.example.vitorizkiimanda.sisuper_apps.activity

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.vitorizkiimanda.sisuper_apps.R
import com.example.vitorizkiimanda.sisuper_apps.R.id.img_event_detail
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass
import com.example.vitorizkiimanda.sisuper_apps.database.AgendaEvent
import com.example.vitorizkiimanda.sisuper_apps.database.database
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints
import com.example.vitorizkiimanda.sisuper_apps.utils.formatDate
import kotlinx.coroutines.selects.select
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

import org.w3c.dom.Text

class EventDetail : AppCompatActivity() {
    private lateinit var bundle: Bundle
    private lateinit var model: EventClass
    private lateinit var modelAgenda: AgendaEvent
    private lateinit var eventName: TextView
    private lateinit var eventOrganizer: TextView
    private lateinit var eventDate: TextView
    private lateinit var eventLocation: TextView
    private lateinit var eventDescription: TextView
    private lateinit var imgEventDetail : ImageView
    private var isAgenda: Boolean = false
    private lateinit var origin: String
    private lateinit var agendaButton: Button
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        //binding
        eventName = findViewById(R.id.name_event_detail)
        eventOrganizer = findViewById(R.id.organized_event_detail)
        eventDate = findViewById(R.id.date_event_detail)
        eventLocation = findViewById(R.id.location_event_detail)
        eventDescription = findViewById(R.id.description_event_detail)
        agendaButton = findViewById(R.id.add_agenda_button)
        imgEventDetail = findViewById(R.id.img_event_detail)

        origin = intent.getStringExtra("origin")
        if(origin == "agenda"){
            id = intent.getStringExtra("id")
            //get Match Data drom SQLite
            database.use {
                database.use {
                    val result  = select("TABLE_AGENDA_EVENT")
                            .whereArgs("(EVENT_ID = {eventId})",
                                    "eventId" to id)

                    val dataList = result.parseList(classParser<AgendaEvent>())
                    val data = dataList[0]

                    modelAgenda = data



                    eventName.text = modelAgenda.eventName
                    eventOrganizer.text = modelAgenda.eventOrganizer
                    eventDate.text = formatDate(modelAgenda.eventDate)
                    eventLocation.text = modelAgenda.eventPlace
                    eventDescription.text = modelAgenda.eventDescription
                }
            }

        }
        else{
            val data: EventClass = intent.getParcelableExtra<EventClass>("model")
            model = data
            id = model.idEvent



            eventName.text = model.eventName
            eventOrganizer.text = model.organized
            eventDate.text = model.date
            eventLocation.text = model.eventPlace
            eventDescription.text = model.description
        }

        loadImage()
        checkAgenda()
    }

    private fun loadImage(){

        var imageLink =""
        if(origin == "agenda"){
//            //remove .jpg
//            imageLink = modelAgenda.eventImage!!.substring(0, modelAgenda.eventImage!!.length - 4)

            imageLink = modelAgenda.eventImage!!
        }
        else{
//            //remove .jpg
//            imageLink = model.image.substring(0, model.image.length - 4)

            imageLink = model.image
        }

        Glide.with(this)
                .load(EndPoints.ROOT_URL+"/events/eventPicture/$imageLink")
                .apply(RequestOptions.placeholderOf(R.drawable.logo))
                .into(imgEventDetail)
    }

    private fun checkAgenda() {
        if (origin == "agenda") {
            isAgenda = true
        } else {
            database.use {
                Log.d("checkDB", id)
                val result = select(AgendaEvent.TABLE_AGENDA_EVENT)
                        .whereArgs("(EVENT_ID = {id})",
                                "id" to id)
                val favorite = result.parseList(classParser<AgendaEvent>())
                if (!favorite.isEmpty()) isAgenda = true
            }
        }
        setAgenda()
    }

    private fun setAgenda() {
        if (isAgenda) {
            agendaButton.setText(R.string.hapus_agenda)
        } else {
            agendaButton.setText(R.string.tambahkan_ke_agenda)
        }
    }

    fun agendaHandler(target: View) {

        if(!isAgenda){
            try {
                database.use {
                    insert(AgendaEvent.TABLE_AGENDA_EVENT,
                            AgendaEvent.EVENT_ID to model.idEvent,
                            AgendaEvent.EVENT_NAME to model.eventName,
                            AgendaEvent.EVENT_IMAGE to model.image,
                            AgendaEvent.EVENT_DATE to model.date,
                            AgendaEvent.EVENT_PLACE to model.eventPlace,
                            AgendaEvent.EVENT_ORGANIZER to model.organized,
                            AgendaEvent.EVENT_DESCRIPTION to model.description)
                }
                toast("Agenda Berhasil Di Tambahkan")
            } catch (e: SQLiteConstraintException) {
                toast(e.localizedMessage)
            }
        }
        else{
            try {
                database.use {
                    database.use {
                        delete(AgendaEvent.TABLE_AGENDA_EVENT, "(EVENT_ID = {id})",
                                "id" to id)
                    }
                }
                toast("Agenda Berhasil Di Hapus")
            } catch (e: SQLiteConstraintException){
                toast(e.localizedMessage)
            }
        }
        isAgenda = !isAgenda
        setAgenda()
    }


}
