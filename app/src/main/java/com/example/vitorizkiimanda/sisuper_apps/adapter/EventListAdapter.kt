package com.example.vitorizkiimanda.sisuper_apps.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.example.vitorizkiimanda.sisuper_apps.R
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass
import com.example.vitorizkiimanda.sisuper_apps.utils.formatDate

import java.util.ArrayList

class EventListAdapter(private val context: Context, private val eventList: ArrayList<EventClass>) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.adapter_list_evets, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = eventList[position]
        val eventName = currentEvent.eventName
        val eventDate = currentEvent.date
        val eventLocation = currentEvent.eventPlace
        val eventImage = currentEvent.image

        Glide.with(context)
                .load(eventImage)
                .into(holder.image)

        Log.d("check Link", "image link : $eventImage")

        holder.eventName.text = eventName
        holder.eventPlace.text = eventLocation
        holder.date.text = formatDate(eventDate)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var image: ImageView
        internal var date: TextView
        internal var eventName: TextView
        internal var eventPlace: TextView
        internal var parentEventListLayout: RelativeLayout? = null

        init {
            image = itemView.findViewById(R.id.image_event_list)
            date = itemView.findViewById(R.id.date_event_list)
            eventName = itemView.findViewById(R.id.name_event_list)
            eventPlace = itemView.findViewById(R.id.place_event_list)

            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    mListener!!.onItemClick(position)

                }
            }
        }
    }

    companion object {
        private val TAG = "EventListAdapter"
    }
}
