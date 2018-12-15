package com.example.vitorizkiimanda.sisuper_apps.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.vitorizkiimanda.sisuper_apps.activity.EditBussinessProfile;

import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.MainActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.TambahUsahaActivity;
import com.example.vitorizkiimanda.sisuper_apps.adapter.BusinessListAdapter;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;
import com.example.vitorizkiimanda.sisuper_apps.provider.SingleUploadBroadcastReceiver;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.os.Build.ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class BussinessProfileFragment extends Fragment implements SingleUploadBroadcastReceiver.Delegate {


    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Context mContext;
    SessionManagement session;

    EditText NamaUsaha;
    EditText LamaUsaha;
    EditText OmzetUsaha;
    EditText DeskripsiUsaha;
    EditText AlamatUsaha;
    EditText EmailUsaha;
    EditText TeleponUsaha;
    EditText WebsiteUsaha;
    EditText FacebookUsaha;
    EditText TwitterUsaha;
    EditText LineUsaha;
    EditText InstagramUsaha;
    ImageView LogoUsaha;

    private View mProgressView;
    private View mRcView;
    String Token;
    String ID;
    Uri certificatePict;

    BusinessClass model;
    Dialog namesDialog;
    String certificatesName = "";

    private final SingleUploadBroadcastReceiver uploadReceiver = new SingleUploadBroadcastReceiver();


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

        //define
        NamaUsaha = view.findViewById(R.id.nama_usaha_business);
        LamaUsaha = view.findViewById(R.id.lama_usaha_business);
        OmzetUsaha = view.findViewById(R.id.omzet_pertahun_business);
        DeskripsiUsaha = view.findViewById(R.id.deskripsi_usaha_business);
        AlamatUsaha = view.findViewById(R.id.alamat_usaha_business);
        EmailUsaha = view.findViewById(R.id.email_usaha_business);
        TeleponUsaha = view.findViewById(R.id.no_telepon_usaha_business);
        WebsiteUsaha = view.findViewById(R.id.website_usaha_business);
        FacebookUsaha = view.findViewById(R.id.facebook_business);
        TwitterUsaha = view.findViewById(R.id.twitter_business);
        LineUsaha = view.findViewById(R.id.line_business);
        InstagramUsaha = view.findViewById(R.id.instagram_business);
        LogoUsaha = view.findViewById(R.id.logo_business);

        //progressbar
        mRcView = view.findViewById(R.id.business_form);
        mProgressView = view.findViewById(R.id.business_profile_progress);

        //bundle
        Bundle bundle = this.getArguments();
        model = bundle.getParcelable("model");

        // Session Manager
        session = new SessionManagement(mContext);

        View Logout = view.findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            LogOut();
            }
        });
        // Inflate the layout for this fragment


        //camera
        Button addCertificate = view.findViewById(R.id.add_certificate);
        addCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivity(intent);
//                SelectImage();
                showDialog();
            }
        });

        //edit Profile
        Button editProfile = view.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(getActivity(), EditBussinessProfile.class);
                moveIntent.putExtra("model", model);
                startActivity(moveIntent);
            }
        });
        showProgress(true);
        BussinessProfileFragment.getBusisnessTask getBusisnessTask = new BussinessProfileFragment.getBusisnessTask();
        getBusisnessTask.execute();
        return view;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRcView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRcView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRcView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mRcView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
                else if (items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                certificatePict = data.getData();
                uploadMultipart(certificatePict, certificatesName);

            }
            else if(requestCode == SELECT_FILE){
                certificatePict = data.getData();
                Log.d("uri", certificatePict.toString());
                Log.d("name", certificatesName);

                uploadMultipart(certificatePict, certificatesName);
            }
        }
    }

    public void getData(){
        HashMap result = session.getBusiness();
        HashMap getToken = session.getUserDetails();
        Token = (String) getToken.get("token");
        ID = (String) result.get("business");

        final String url = EndPoints.ROOT_URL+"/business/getBusinessById";
        StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject results = new JSONObject(response);
                            JSONObject result = results.getJSONObject("result");
                            NamaUsaha.setText(result.getString("name"));
                            LamaUsaha.setText(result.getString("established_date"));
                            OmzetUsaha.setText(result.getString("revenue"));
                            DeskripsiUsaha.setText(result.getString("description"));
                            AlamatUsaha.setText(result.getString("address"));
                            EmailUsaha.setText(result.getString("email"));
                            TeleponUsaha.setText(result.getString("phone"));
                            WebsiteUsaha.setText(result.getString("site"));
                            FacebookUsaha.setText(result.getString("facebook"));
                            TwitterUsaha.setText(result.getString("twitter"));
                            LineUsaha.setText(result.getString("line"));
                            InstagramUsaha.setText(result.getString("instagram"));
                            showProgress(false);

//                            Glide.with(mContext)
//                                    .load(EndPoints.ROOT_URL + "/business/getBusinessPicture/" + result.getString("logo"))
//                                    .apply(RequestOptions.signatureOf(new ObjectKey(Long.toString(System.currentTimeMillis()))))
//                                    .into(LogoUsaha);
                            System.out.println(result);

                        } catch (JSONException e) {
                            Toast.makeText(mContext, "Internal Server Error", Toast.LENGTH_LONG).show();
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
                params.put("businessId", ID);

                return params;
            }

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer " + Token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(postRequest);

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

    private void showDialog(){
        namesDialog = new Dialog(mContext);
        namesDialog.setContentView(R.layout.dialog);
        final EditText certificateNames = namesDialog.findViewById(R.id.body);
        namesDialog.show();
        Button okBtn = (Button) namesDialog.findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certificatesName = certificateNames.getText().toString();
                if(TextUtils.isEmpty(certificatesName)){
                    certificateNames.setError("This field is required");
                }
                else{
                    SelectImage();
                    namesDialog.dismiss();
                }

            }
        });

        Button cancelBtn = (Button) namesDialog.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namesDialog.dismiss();
            }
        });

    }

    public String getPath(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = mContext.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();


        return path;
    }


    @Override
    public void onPause() {
        super.onPause();
        uploadReceiver.unregister(mContext);
    }

    public void uploadMultipart(Uri filePath, String namaCertificate) {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            uploadReceiver.setDelegate(this);
            uploadReceiver.setUploadID(uploadId);


            //Creating a multi part request
            new MultipartUploadRequest(mContext, uploadId, EndPoints.ROOT_URL +"/certificates/addCertificateUser")
                    .addFileToUpload(path, "certificatePicture") //Adding file
                    .addHeader("Authorization", "Bearer " + Token)
                    .addParameter("idOwner", ID)
                    .setMaxRetries(2)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            exc.printStackTrace();
            Toast.makeText(mContext, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {

    }

    @Override
    public void onError(Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        System.out.println(serverResponseBody.toString());
        Toast.makeText(mContext, "Upload Sukses", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled() {

    }


    public class getBusisnessTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            getData();
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        uploadReceiver.register(mContext);
        getData();
    }
}

