package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.adapter.BusinessListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductInput extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SessionManagement session;

    EditText productNames;
    EditText productPrices;

    String token;
    String ID;
    String unitProduct;

    Boolean cancel = false;
    View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_input);

        session = new SessionManagement(this);

        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.input_product_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.unitProduct, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //init
        productNames = findViewById(R.id.name_input_product);
        productPrices = findViewById(R.id.price_input_product);
        mProgressView = findViewById(R.id.product_input_progress);

        final Button inputProduct = findViewById(R.id.add_input_product);
        inputProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                inputProduct();
            }
        });

    }

    public void inputProduct() {
        View focusView = null;
        cancel = false;

        final String productName = productNames.getText().toString();
        final String productPrice = productPrices.getText().toString();

        if (TextUtils.isEmpty(productName)) {
            productNames.setError(getString(R.string.error_field_required));
            focusView = productNames;
            cancel = true;
        }

        if (TextUtils.isEmpty(productPrice)) {
            productPrices.setError(getString(R.string.error_field_required));
            focusView = productPrices;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Log.d("nama", productName);
            Log.d("price", productPrice);
            Log.d("unit", unitProduct);

            HashMap result = session.getUserDetails();
            HashMap business = session.getBusiness();

            token = (String) result.get("token");
            ID = (String) business.get("business");
            final String url = EndPoints.ROOT_URL+"/products/addProduct";

            StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                System.out.println(result);

                                Toast.makeText(getApplication(), "Input Produk Berhasil", Toast.LENGTH_LONG).show();
                                showProgress(false);
                                finish();

                            } catch (JSONException e) {
                                Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
                                showProgress(false);
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
                    params.put("businessId", ID);
                    params.put("productName", productName);
                    params.put("productPrice", productPrice);
                    params.put("productUnit", unitProduct);

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

            RequestQueue requestQueue = Volley.newRequestQueue(ProductInput.this);
            requestQueue.add(postRequest);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        unitProduct = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

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
}
