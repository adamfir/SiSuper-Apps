package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import java.util.Arrays;
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
    private View mProgressView;

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

        mProgressView = findViewById(R.id.business_progress);

        showProgress(true);
        BusinessListActivity.getBusinessTask getBusinessTask = new BusinessListActivity.getBusinessTask();
        getBusinessTask.execute();

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

                            //marker
                            BusinessClass businessClasses  = new BusinessClass();
                            businessClasses.setID("null");
                            businessList.add(businessClasses);

                            for(int i = 0; i<events.length(); i++){
                                JSONObject event = events.getJSONObject(i);

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
                                businessClass.setWebsiteUsaha(event.getString("site"));
                                businessClass.setFacebokUsaha(event.getString("facebook"));
                                businessClass.setTwitterUsaha(event.getString("twitter"));
                                businessClass.setLineUsaha(event.getString("line"));
                                businessClass.setInstagramUsaha(event.getString("instagram"));
                                businessClass.setLogoUsaha(event.getString("logo"));

                                businessList.add(businessClass);
                            }



                            businessListAdapter = new BusinessListAdapter(getApplication(), businessList);
                            recyclerView.setAdapter(businessListAdapter);
                            businessListAdapter.setOnItemClickListener(BusinessListActivity.this);

                            //setDecoration
                            float offsetPx = getResources().getDimension(R.dimen.padding);
                            BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
                            recyclerView.addItemDecoration(bottomOffsetDecoration);

                            Toast.makeText(getApplication(), "Retrieve Data Usaha Berhasil", Toast.LENGTH_LONG).show();
                            showProgress(false);

                        } catch (JSONException e) {
                            Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
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
        }
    }
    public class getBusinessTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            getData();

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
        Intent moveIntent = new Intent(BusinessListActivity.this, MainActivity.class);
        moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BusinessClass clickedItem = businessList.get(position);
        session.businessSession(clickedItem.getID(), clickedItem.getNamaUsaha(), clickedItem.getEmailUsaha());
        moveIntent.putExtra("model", clickedItem);
        startActivity(moveIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        startActivity(a);
    }

    static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset;

        public BottomOffsetDecoration(int bottomOffset) {
            mBottomOffset = bottomOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, 0, mBottomOffset);
            } else {
                outRect.set(0, 0, 0, 0);
            }

        }
    }

}



