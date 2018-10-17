package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.Onboarding;
import com.example.vitorizkiimanda.sisuper_apps.activity.ProductInput;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {


    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        View addProductButton = view.findViewById(R.id.button_add_product);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(getActivity(), ProductInput.class);
                startActivity(moveIntent);
            }
        });
        return view;
    }



}
