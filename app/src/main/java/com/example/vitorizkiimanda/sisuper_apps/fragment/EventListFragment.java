package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.EventDetail;
import com.example.vitorizkiimanda.sisuper_apps.activity.ProductInput;
import com.example.vitorizkiimanda.sisuper_apps.adapter.EventListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment {
    SessionManagement session;
    Context mContext;
    String token;
    private ArrayList<EventClass> eventList;
    private EventListAdapter eventListAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;




    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
//        View eventItem = view.findViewById(R.id.event_item);
//        eventItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moveIntent = new Intent(getActivity(), EventDetail.class);
//                startActivity(moveIntent);
//            }
//        });

        recyclerView = view.findViewById(R.id.event_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);

        //session manager
        session = new SessionManagement(mContext);
        this.eventList = new ArrayList<>();

        getEventsTask getEventsTask = new getEventsTask();
        getEventsTask.execute();




        return view;
    }

    public void getData(){
        HashMap result = session.getUserDetails();
        token = (String) result.get("token");
        String URI = "http://sisuper.codepanda.web.id/events";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URI,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray events = response.getJSONArray("event");

                            for (int i = 0; i<events.length(); i++){
                                JSONObject data = events.getJSONObject(i);

                                System.out.println(data);
                                EventClass eventClass = new EventClass();

                                eventClass.setEventName(data.getString("name"));
                                eventClass.setEventPlace(data.getString("location"));
                                eventClass.setDate(data.getString("date"));

                                eventList.add(eventClass);
                            }
                            eventListAdapter = new EventListAdapter(mContext, eventList);
                            recyclerView.setAdapter(eventListAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Internal Server Error", Toast.LENGTH_LONG).show();

                    }
                }
        )
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


    public class getEventsTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            getData();
            return null;
        }
    }

}
