package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.BusinessListActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.EditUserProfile;
import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.TambahUsahaActivity;
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
public class UserProfileFragment extends Fragment {

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Context mContext;
    SessionManagement session;
    String Username;
    String Email;
    String Phone;
    String Address;
    String Image;
    String Id;



    public UserProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Session Manager
        session = new SessionManagement(mContext);


        View Logout = view.findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });


        //move to EditProfileUser
        Button editProfile = view.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent moveIntent = new Intent(getActivity(), EditUserProfile.class);
                startActivity(moveIntent);
            }
        });

        //get data from session
        getData(view);


        //camera
        Button addCertificate = view.findViewById(R.id.add_certificate);
        addCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
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
                        session.logoutUser();
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

    public void getData(View view){
        HashMap result = session.getUserDetails();
        Id = (String) result.get("id");
        Username = (String) result.get("username");
        Email = (String) result.get("email");
        Address = (String) result.get("address");
        Phone = (String) result.get("phone");
        Image = (String) result.get("image");

        //parse data
        EditText Usernames = (EditText) view.findViewById(R.id.nama);
        EditText Emails = (EditText) view.findViewById(R.id.email);
        EditText Phones = (EditText) view.findViewById(R.id.phone_user);
        EditText Addresses = (EditText) view.findViewById(R.id.alamat_user);
        ImageView Image = (ImageView) view.findViewById(R.id.UserProfileFragment_img);

        //set data to text
        Usernames.setText(Username);
        Emails.setText(Email);
        Phones.setText(Phone);
        Addresses.setText(Address);
        Glide.with(view)
                .load(EndPoints.ROOT_URL + "/users/profilePicture/" + Id)
                .apply(RequestOptions.signatureOf(new ObjectKey(Long.toString(System.currentTimeMillis()))))
                .into(Image);

    }


    @Override
    public void onResume() {
        super.onResume();
        getData(getView());
    }
}
