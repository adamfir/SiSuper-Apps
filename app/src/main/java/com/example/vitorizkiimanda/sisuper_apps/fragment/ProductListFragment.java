package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.BusinessListActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.EditProduct;
import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.MainActivity;
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
import java.util.Objects;

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

        mProgressView = view.findViewById(R.id.product_progress);


//        View addProductButton = view.findViewById(R.id.button_add_product);
//        addProductButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moveIntent = new Intent(getActivity(), ProductInput.class);
//                startActivity(moveIntent);
//            }
//        });

//        View editProductItem = view.findViewById(R.id.product_item);
//        editProductItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moveIntent = new Intent(getActivity(), EditProduct.class);
//                startActivity(moveIntent);
//            }
//        });

        Objects.requireNonNull(getActivity()).setTitle("Produk-ku");
        return view;
    }

    public void getProductList(){
        productList.clear();

        showProgress(true);
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

                            for(int i = results.length() - 1; i>=0; i--){
                                JSONObject produk = results.getJSONObject(i);

                                ProductClass productClass = new ProductClass();
                                productClass.setProductId(produk.getString("_id"));
                                productClass.setProductName(produk.getString("name"));
                                productClass.setProductPrice(produk.getString("price"));
                                productClass.setProductUnit(produk.getString("unit"));

                                productList.add(productClass);
                            }

                            productListAdapter = new ProductListAdapter(getActivity(), productList);
                            recyclerView.setAdapter(productListAdapter);
                            productListAdapter.setOnItemClickListener(ProductListFragment.this);

                            //setDecoration
                            float offsetPx = getResources().getDimension(R.dimen.padding);
                            ProductListFragment.BottomOffsetDecoration bottomOffsetDecoration = new ProductListFragment.BottomOffsetDecoration((int) offsetPx);
                            recyclerView.addItemDecoration(bottomOffsetDecoration);


                            Toast.makeText(mContext, "Retrieve Produk Berhasil", Toast.LENGTH_LONG).show();
                            showProgress(false);
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


    @Override
    public void onItemClick(int position) {
        Intent moveIntent = new Intent(mContext, EditProduct.class);
        ProductClass clickedItem = productList.get(position);
        moveIntent.putExtra("model", clickedItem);
        startActivity(moveIntent);


    }

    @Override
    public void onResume() {
        super.onResume();
        getProductList();
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
