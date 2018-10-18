package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.BusinessListActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BussinessProfileFragment extends Fragment {


    public BussinessProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bussiness_profile, container, false);
        View Logout = view.findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(getActivity(), LoginActivity.class);
                moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(moveIntent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
