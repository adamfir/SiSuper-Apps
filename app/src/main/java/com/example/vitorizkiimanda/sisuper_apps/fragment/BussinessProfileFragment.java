package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.BusinessListActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.EditProfileActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BussinessProfileFragment extends Fragment {
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Context mContext;

    public BussinessProfileFragment() {
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

        View view = inflater.inflate(R.layout.fragment_bussiness_profile, container, false);
        final View Logout = view.findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });


        //camera
        Button addCertificate = view.findViewById(R.id.add_certificate);
        addCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivity(intent);
                SelectImage();
            }
        });

        //edit Profile
        Button editProfile = view.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(moveIntent);
            }
        });

        return view;
    }

    private void SelectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Select Method");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                else if (items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivity(intent.createChooser(intent, "Select File"));
                }
                else if (items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void LogOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are You sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent moveIntent = new Intent(getActivity(), LoginActivity.class);
                        moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(moveIntent);
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

