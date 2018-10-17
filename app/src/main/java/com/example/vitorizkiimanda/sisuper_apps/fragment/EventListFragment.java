package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.EventDetail;
import com.example.vitorizkiimanda.sisuper_apps.activity.ProductInput;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment {


    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        View eventItem = view.findViewById(R.id.event_item);
        eventItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(getActivity(), EventDetail.class);
                startActivity(moveIntent);
            }
        });
        return view;
    }

}
