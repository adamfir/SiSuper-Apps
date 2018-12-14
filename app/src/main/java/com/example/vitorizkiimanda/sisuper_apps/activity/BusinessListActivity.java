package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.example.vitorizkiimanda.sisuper_apps.adapter.BusinessListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.adapter.EventListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;
import com.example.vitorizkiimanda.sisuper_apps.fragment.EventListFragment;
import com.example.vitorizkiimanda.sisuper_apps.fragment.ProductListFragment;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusinessListActivity extends AppCompatActivity implements BusinessListAdapter.OnItemClickListener {
    SessionManagement session;
    String token;
    String ID;
    private ArrayList<BusinessClass> businessList;

    BusinessListAdapter businessListAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);

        this.businessList = new ArrayList<>();

        //session
        //session manager
        session = new SessionManagement(this);

        recyclerView = findViewById(R.id.business_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(mLayoutManager);



        View actionButton = findViewById(R.id.action_button);

//        onClickListener
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(BusinessListActivity.this, MainActivity.class);
                moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(moveIntent);
            }
        });


        //to page TambahUsahaActivity
        View addUsahaButton = findViewById(R.id.addUsaha_button);
        addUsahaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(BusinessListActivity.this, TambahUsahaActivity.class);
                startActivity(moveIntent);
            }
        });
        getData();

    }

    public void getData(){
        HashMap result = session.getUserDetails();
        token = (String) result.get("token");
        ID = (String) result.get("id");
        System.out.println(token + " " + ID);
        final String url = EndPoints.ROOT_URL+"/business/getBusinessByUserId";

        StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            JSONArray events = result.getJSONArray("result");

                            for(int i = 0; i<events.length(); i++){
                                JSONObject event = events.getJSONObject(i);
                                System.out.println(event);

                                BusinessClass businessClass = new BusinessClass();
                                businessClass.setID(event.getString("_id"));
                                businessClass.setNamaUsaha(event.getString("name"));
                                businessClass.setCategory(event.getString("category"));
                                businessClass.setLamaUsaha(event.getString("established_date"));
                                businessClass.setOmzetUsaha(event.getString("revenue"));
                                businessClass.setDeskripsiUsaha(event.getString("description"));
                                businessClass.setAlamatUsaha(event.getString("address"));
                                businessClass.setEmailUsaha(event.getString("email"));
                                businessClass.setTeleponUsaha(event.getString("phone"));

                                businessList.add(businessClass);

                            }
                            businessListAdapter = new BusinessListAdapter(getApplication(), businessList);
                            recyclerView.setAdapter(businessListAdapter);
                            businessListAdapter.setOnItemClickListener(BusinessListActivity.this);

                            System.out.println(result);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId", ID);

                return params;
            }

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BusinessListActivity.this);
        requestQueue.add(postRequest);
        }

    @Override
    public void onItemClick(int position) {

    }

}

