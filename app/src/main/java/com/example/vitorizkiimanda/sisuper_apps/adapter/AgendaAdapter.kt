package com.example.vitorizkiimanda.sisuper_apps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vitorizkiimanda.sisuper_apps.R
import com.example.vitorizkiimanda.sisuper_apps.activity.EventDetail
import com.example.vitorizkiimanda.sisuper_apps.database.AgendaEvent
import com.example.vitorizkiimanda.sisuper_apps.utils.formatDate
import kotlinx.android.synthetic.main.adapter_list_evets.view.*
import org.jetbrains.anko.startActivity

class AgendaAdapter (private val events: List<AgendaEvent>)
    : RecyclerView.Adapter<AgendaAdapterViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AgendaAdapterViewHolder {
        val viewHolder = AgendaAdapterViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.adapter_list_evets, p0, false))

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            p0.context?.startActivity<EventDetail>("id" to events[position].eventId, "origin" to "agenda")
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return events.size
    }
    override fun onBindViewHolder(p0: AgendaAdapterViewHolder, p1: Int) {
        p0.bindItem(events[p1])
    }

}

class AgendaAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val eventImage = view.image_event_list
    private val evenDate = view.date_event_list
    private val eventName = view.name_event_list
    private val eventPlace = view.place_event_list

    fun bindItem(events: AgendaEvent) {
        Glide.with(itemView.context)
                .load(events.eventImage)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(eventImage)
        evenDate?.text = formatDate(events.eventDate)
        eventName?.text = events.eventName
        eventPlace?.text = events.eventPlace
    }
}
