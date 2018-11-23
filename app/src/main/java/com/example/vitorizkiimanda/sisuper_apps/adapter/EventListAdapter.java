package com.example.vitorizkiimanda.sisuper_apps.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{
    private static final String TAG = "EventListAdapter";
    private Context context;
    private ArrayList<EventClass> eventList;
    private OnItemClickListener mListener;

    public EventListAdapter(Context context, ArrayList<EventClass> eventList){
        this.context = context;
        this.eventList = eventList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_list_evets, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventClass currentEvent = eventList.get(position);
        String eventName = currentEvent.getEventName();
        String eventDate = currentEvent.getDate();
        String eventLocation = currentEvent.getEventPlace();

        holder.eventName.setText(eventName);
        holder.eventPlace.setText(eventLocation);
        holder.date.setText(eventDate);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView date;
        TextView eventName;
        TextView eventPlace;
        RelativeLayout parentEventListLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_event_list);
            date = itemView.findViewById(R.id.date_event_list);
            eventName = itemView.findViewById(R.id.name_event_list);
            eventPlace = itemView.findViewById(R.id.place_event_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        mListener.onItemClick(position);

                    }
                }
            });
        }
    }
}
