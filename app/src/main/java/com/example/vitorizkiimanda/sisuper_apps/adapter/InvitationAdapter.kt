package com.example.vitorizkiimanda.sisuper_apps.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vitorizkiimanda.sisuper_apps.R
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints
import com.example.vitorizkiimanda.sisuper_apps.utils.formatDate
import java.util.ArrayList

class InvitationAdapter(private val context: Context, private val invitationList: ArrayList<EventClass>) : RecyclerView.Adapter<InvitationAdapter.ViewHolder>() {
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.adapter_invitation, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = invitationList[position]
        val eventName = currentEvent.eventName
        val eventOrganizer = currentEvent.organized
        var eventImage = currentEvent.image

//        //remove .jpg
//        eventImage = eventImage.substring(0, eventImage.length - 4);

        Glide.with(context)
                .load(EndPoints.ROOT_URL+"/events/eventPicture/"+eventImage)
                .apply(RequestOptions.placeholderOf(R.drawable.logo))
                .into(holder.image)

        Log.d("check Link", "image link : $eventImage")

        holder.eventName.text = eventName
        holder.organizer.text = eventOrganizer
    }

    override fun getItemCount(): Int {
        return invitationList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var image: ImageView
        internal var eventName: TextView
        internal var organizer: TextView

        init {
            image = itemView.findViewById(R.id.invitation_image)
            eventName = itemView.findViewById(R.id.invitation_title)
            organizer = itemView.findViewById(R.id.invitation_organizer)

            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    mListener!!.onItemClick(position)

                }
            }
        }
    }

    companion object {
        private val TAG = "InvitationAdapter"
    }
}
