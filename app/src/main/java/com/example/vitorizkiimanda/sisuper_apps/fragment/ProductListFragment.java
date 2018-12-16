package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.EditProduct;
import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.Onboarding;
import com.example.vitorizkiimanda.sisuper_apps.activity.ProductInput;
import com.example.vitorizkiimanda.sisuper_apps.adapter.EventListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.adapter.ProductListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.data.ProductClass;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
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
public class ProductListFragment extends Fragment implements ProductListAdapter.OnItemClickListener {
    Context mContext;
    SessionManagement session;
    private ArrayList<ProductClass> productList;

    ProductListAdapter productListAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mProgressView;

    String token;
    String businessID;


    public ProductListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        this.productList = new ArrayList<>();
        //session
        session = new SessionManagement(mContext);

        //list
        recyclerView = view.findViewById(R.id.product_list);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);


        View addProductButton = view.findViewById(R.id.button_add_product);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(getActivity(), ProductInput.class);
                startActivity(moveIntent);
            }
        });

//        View editProductItem = view.findViewById(R.id.product_item);
//        editProductItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moveIntent = new Intent(getActivity(), EditProduct.class);
//                startActivity(moveIntent);
//            }
//        });

        getProductList();

        return view;
    }

    public void getProductList(){
        HashMap userProfile = session.getUserDetails();
        HashMap businessProfile = session.getBusiness();

        token = (String) userProfile.get("token");
        businessID = (String) businessProfile.get("business");
        final String url = EndPoints.ROOT_URL+"/products/getProductByBussinessId";

        StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response);
                            JSONArray results = result.getJSONArray("result");

                            //marker
                            ProductClass productClasses = new ProductClass();
                            productClasses.setProductName("null");
                            productList.add(productClasses);

                            for(int i = 0; i<results.length(); i++){
                                JSONObject produk = results.getJSONObject(i);

                                ProductClass productClass = new ProductClass();
                                productClass.setProductName(produk.getString("name"));
                                productClass.setProductPrice(produk.getString("price"));
                                productClass.setProductUnit(produk.getString("unit"));

                                productList.add(productClass);
                            }

                            productListAdapter = new ProductListAdapter(getActivity(), productList);
                            recyclerView.setAdapter(productListAdapter);
                            productListAdapter.setOnItemClickListener(ProductListFragment.this);


                            Toast.makeText(mContext, "Retrieve Produk Berhasil", Toast.LENGTH_LONG).show();
//                            showProgress(false);
//                            finish();

                        } catch (JSONException e) {
                            Toast.makeText(mContext, "Internal Server Error", Toast.LENGTH_LONG).show();
//                            showProgress(false);
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
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("businessId", businessID);

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

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(postRequest);
    }


    @Override
    public void onItemClick(int position) {

    }
}
