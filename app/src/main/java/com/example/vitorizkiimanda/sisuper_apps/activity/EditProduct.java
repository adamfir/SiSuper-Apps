package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.vitorizkiimanda.sisuper_apps.data.ProductClass;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SessionManagement session;
    String unitProduct;

    EditText productNames;
    EditText productPrices;

    String token;
    String ID;

    Boolean cancel = false;
    Bundle bundle;
    private ProductClass model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        //session
        session = new SessionManagement(this);

        bundle = getIntent().getExtras();
        model = bundle.getParcelable("model");

        Spinner spinner = (Spinner) findViewById(R.id.product_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.unitProduct, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        String[] array = getApplication().getResources().getStringArray(R.array.unitProduct);
        System.out.println(array[0]);
        System.out.println(array.length);
        for(int i = 0; i < array.length; i++){

            if(array[i].equals(model.getProductUnit())){
                spinner.setSelection(i);
            }
        }

        //init
        productNames = findViewById(R.id.product_name);
        productPrices = findViewById(R.id.product_price);
        productNames.setText(model.getProductName());
        productPrices.setText(model.getProductPrice());


        final Button editProduct = findViewById(R.id.edit_edit_produk);
        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editProduct();

            }
        });

        final Button deleteProduct = findViewById(R.id.delete_edit_produk);
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        unitProduct = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void editProduct(){
        View focusView = null;
        cancel = false;

        final String productName = productNames.getText().toString();
        final String productPrice = productPrices.getText().toString();

        if(TextUtils.isEmpty(productName)){
            productNames.setError(getString(R.string.error_field_required));
            focusView = productNames;
            cancel = true;
        }

        if(TextUtils.isEmpty(productPrice)){
            productPrices.setError(getString(R.string.error_field_required));
            focusView = productPrices;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else {
            Log.d("nama", productName);
            Log.d("price", productPrice);
            Log.d("unit", unitProduct);

            HashMap result = session.getUserDetails();
            HashMap business = session.getBusiness();

            token = (String) result.get("token");
            ID = (String) business.get("business");
            final String url = EndPoints.ROOT_URL+"/products/editProduct";

            StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject result = new JSONObject(response);
                                System.out.println(result);

                                Toast.makeText(getApplication(), "Edit Produk Berhasil", Toast.LENGTH_LONG).show();
//                                showProgress(false);
                                finish();

                            } catch (JSONException e) {
                                Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
//                                showProgress(false);
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
                    params.put("productId", model.getProductId());
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

            RequestQueue requestQueue = Volley.newRequestQueue(EditProduct.this);
            requestQueue.add(postRequest);
        }
    }

    public void deleteProduct(){

        HashMap result = session.getUserDetails();
        HashMap business = session.getBusiness();

        token = (String) result.get("token");
        ID = (String) business.get("business");
        final String url = EndPoints.ROOT_URL+"/products/deleteProduct";
        System.out.println(token);
        System.out.println(model.getProductId());

        StringRequest postRequest  =  new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            System.out.println(result);

                            Toast.makeText(getApplication(), "Hapus Produk Berhasil", Toast.LENGTH_LONG).show();
//                                showProgress(false);
                            finish();

                        } catch (JSONException e) {
                            Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
//                                showProgress(false);
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
                params.put("productId", model.getProductId());

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

        RequestQueue requestQueue = Volley.newRequestQueue(EditProduct.this);
        requestQueue.add(postRequest);
    }

    public void dialogDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Apakah anda yakin ingin menghapus produk ini?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteProduct();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
