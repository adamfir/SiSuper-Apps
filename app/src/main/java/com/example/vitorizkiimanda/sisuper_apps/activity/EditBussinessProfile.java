package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.fragment.ProductListFragment;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditBussinessProfile extends AppCompatActivity {
    private ImageView photoProfile;
    SessionManagement session;
    BusinessClass model;
    Bundle bundle;

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
    Button SaveEdit;

    View mProgressView;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bundle = getIntent().getExtras();
        model = bundle.getParcelable("model");
        System.out.println(model.getID());

        //session
        session = new SessionManagement(this);

        //parsing
        NamaUsaha = findViewById(R.id.nama_usaha_edit);
        LamaUsaha = findViewById(R.id.lama_usaha_edit);
        OmzetUsaha = findViewById(R.id.omzet_pertahun_edit);
        DeskripsiUsaha = findViewById(R.id.deskripsi_usaha_edit);
        AlamatUsaha = findViewById(R.id.alamat_usaha_edit);
        EmailUsaha = findViewById(R.id.email_usaha_edit);
        TeleponUsaha = findViewById(R.id.no_telepon_usaha_edit);
        WebsiteUsaha = findViewById(R.id.website_usaha_edit);
        FacebookUsaha = findViewById(R.id.facebook_edit);
        TwitterUsaha = findViewById(R.id.twitter_edit);
        LineUsaha = findViewById(R.id.id_line_edit);
        InstagramUsaha = findViewById(R.id.instagram_edit);
        parsingData();

        //progressview
        mProgressView = findViewById(R.id.progress_edit_business_profile);


        //change photo profile
        photoProfile = findViewById(R.id.photo_profile);
        photoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             //   startActivityForResult(intent, 0);

                SelectImage();
            }
        });

        //save change

        SaveEdit = findViewById(R.id.save_edit);
        SaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                updateBusinessProfile();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                photoProfile.setImageBitmap(bitmap);
            }
            else if(requestCode == SELECT_FILE){
                Uri selectedImage = data.getData();
                photoProfile.setImageURI(selectedImage);
            }
        }

    }

    private void SelectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditBussinessProfile.this);
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


    public void updateBusinessProfile(){
        HashMap result = session.getBusiness();
        HashMap getToken = session.getUserDetails();
        final String Token = (String) getToken.get("token");
        final String businessID = (String) result.get("business");

        final String url = EndPoints.ROOT_URL+"/business/editBusiness";
        StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject results = new JSONObject(response);
                            Toast.makeText(EditBussinessProfile.this, "Update Profile Sukses", Toast.LENGTH_LONG).show();
                            System.out.println(results);
                            showProgress(false);

                        } catch (JSONException e) {
                            Toast.makeText(EditBussinessProfile.this, "Internal Server Error", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditBussinessProfile.this, "Internal Server Error", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("businessId", businessID);
                params.put("businessName", NamaUsaha.getText().toString());
                params.put("businessCategory", "haha");
                params.put("establishedDate", LamaUsaha.getText().toString());
                params.put("businessRevenue", OmzetUsaha.getText().toString());
                params.put("businessDescription", DeskripsiUsaha.getText().toString());
                params.put("businessAddress", AlamatUsaha.getText().toString());
                params.put("businessEmail", EmailUsaha.getText().toString());
                params.put("businessPhone", TeleponUsaha.getText().toString());
                params.put("businessSite", WebsiteUsaha.getText().toString());
                params.put("businessFacebook", FacebookUsaha.getText().toString());
                params.put("businessTwitter", TwitterUsaha.getText().toString());
                params.put("businessInstagram", InstagramUsaha.getText().toString());

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

        RequestQueue requestQueue = Volley.newRequestQueue(EditBussinessProfile.this);
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



    public void parsingData(){
        NamaUsaha.setText(model.getNamaUsaha());
        LamaUsaha.setText(model.getLamaUsaha());
        OmzetUsaha.setText(model.getOmzetUsaha());
        DeskripsiUsaha.setText(model.getDeskripsiUsaha());
        AlamatUsaha.setText(model.getAlamatUsaha());
        EmailUsaha.setText(model.getEmailUsaha());
        TeleponUsaha.setText(model.getTeleponUsaha());
        WebsiteUsaha.setText(model.getWebsiteUsaha());
        FacebookUsaha.setText(model.getFacebokUsaha());
        TwitterUsaha.setText(model.getTwitterUsaha());
        LineUsaha.setText(model.getLineUsaha());
        InstagramUsaha.setText(model.getInstagramUsaha());

    }
}
