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
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.EventDetail;
import com.example.vitorizkiimanda.sisuper_apps.adapter.EventListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.adapter.InvitationAdapter;
import com.example.vitorizkiimanda.sisuper_apps.adapter.ProductListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;
import com.example.vitorizkiimanda.sisuper_apps.data.ProductClass;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationFragment extends Fragment implements InvitationAdapter.OnItemClickListener {


    Context mContext;
    SessionManagement session;
    private ArrayList<EventClass> invitationList;

    InvitationAdapter invitationAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mProgressView;

    String id;
    String token;

    private View mRcView;

    public InvitationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_invitation, container, false);
        recyclerView = view.findViewById(R.id.invitation_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);

        mRcView = view.findViewById(R.id.invitation_list);
        mProgressView = view.findViewById(R.id.invitations_progress);
        //session manager
        session = new SessionManagement(mContext);
        this.invitationList = new ArrayList<>();
        showProgress(true);
        InvitationFragment.getEventsTask getEventsTask = new InvitationFragment.getEventsTask();
        getEventsTask.execute();

        Objects.requireNonNull(getActivity()).setTitle("Kegiatan");
        return view;
    }

    public void getInvitationList(){

        showProgress(true);
        invitationList.clear();

//        showProgress(true);
        HashMap userProfile = session.getUserDetails();

        token = (String) userProfile.get("token");
        id = (String) userProfile.get("id");
        final String url = EndPoints.ROOT_URL+"/invitations/getUserById/"+id;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray invitation = response.getJSONArray("invitation");
                            Log.d("event", "data events :" +invitation);

                            for (int i = 0; i<invitation.length(); i++){
                                JSONObject data = invitation.getJSONObject(i).getJSONObject("event");
                                Log.d("data event", String.valueOf(data));

                                System.out.println(data);
                                EventClass eventClass = new EventClass();

                                eventClass.setEventName(data.getString("name"));
                                eventClass.setEventPlace(data.getString("location"));
                                eventClass.setDate(data.getString("date"));
                                eventClass.setOrganized(data.getString("organized_by"));
                                eventClass.setDescription(data.getString("description"));
                                eventClass.setIdEvent(data.getString("_id"));

                                //image URL
                                String tempImageURL = data.getString("picture");
                                tempImageURL = tempImageURL.substring(0, tempImageURL.length() - 4);

                                eventClass.setImage(tempImageURL);

                                invitationList.add(eventClass);
                            }
                            invitationAdapter = new InvitationAdapter(mContext, invitationList);
                            recyclerView.setAdapter(invitationAdapter);
                            invitationAdapter.setOnItemClickListener(InvitationFragment.this);
                            showProgress(false);


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



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRcView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRcView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRcView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRcView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public class getEventsTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            getInvitationList();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            //showProgress(false);

        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, EventDetail.class);
        EventClass clickedItem = invitationList.get(position);
        System.out.println("haha"+clickedItem);
        intent.putExtra("model", clickedItem);
        intent.putExtra("origin", "notAgenda");
        Log.d("clicked event", String.valueOf(clickedItem.getIdEvent()));
        startActivity(intent);
    }


}
