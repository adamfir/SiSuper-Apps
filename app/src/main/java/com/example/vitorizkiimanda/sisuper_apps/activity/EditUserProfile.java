package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
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
import com.bumptech.glide.Glide;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfile extends AppCompatActivity {
    SessionManagement session;
    String Username;
    String Email;
    String Phone;
    String Address;
    String Image;
    String ID;
    String Token;

    private View mScrollView;
    private View mProgressView;
    private editProfileTask editProfileJobs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        //session
        session = new SessionManagement(getApplicationContext());

        //parse data
        EditText Usernames = (EditText) findViewById(R.id.name_user_edit);
        EditText Emails = (EditText) findViewById(R.id.email_user_edit);
        final EditText Phones = (EditText) findViewById(R.id.phone_user_edit);
        final EditText Addresses = (EditText) findViewById(R.id.address_user_edit);
        Button editProfile  = (Button) findViewById(R.id.button_user_edit);
        ImageView Image = (ImageView) findViewById(R.id.image_user_edit);
        mScrollView = findViewById(R.id.profile_user_edit);
        mProgressView = findViewById(R.id.edit_user_progress);

        //get data
        getData();

        //settext
        Usernames.setText(Username);
        Emails.setText(Email);
        Phones.setText(Phone);
        Addresses.setText(Address);
        Glide.with(getApplicationContext()).load("http://sisuper.codepanda.web.id/users/profilePicture/" + ID).into(Image);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String editAddress = Addresses.getText().toString();
                String editPhones = Phones.getText().toString();

                showProgress(true);
                editProfileJobs = new editProfileTask(editAddress, editPhones, Token, ID);
                editProfileJobs.execute((Void) null);
            }
        });

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });



    }

    public void getData(){
        HashMap result = session.getUserDetails();
        Username = (String) result.get("username");
        Email = (String) result.get("email");
        Address = (String) result.get("address");
        Phone = (String) result.get("phone");
        Image = (String) result.get("image");
        ID = (String) result.get("id");
        Token = (String) result.get("token");


        System.out.println("hasil " + ID);
    }


    private void SelectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public class editProfileTask extends AsyncTask<Void, Void, Boolean>{
        private final String mAddress;
        private final String mPhone;
        private final String mToken;
        private final String mId;


        editProfileTask(String mAddress, String mPhone, String mToken, String mId){

            this.mAddress = mAddress;
            this.mPhone = mPhone;
            this.mToken = mToken;
            this.mId = mId;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            editProfile();
            return null;
        }

        private void editProfile(){
            final String url = "http://sisuper.codepanda.web.id/users/editProfile/" +mId;

            StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d("Response", response);

                            try {
                                JSONObject result = new JSONObject(response);
                                System.out.println(mId + " " + mToken + " " + mPhone);
                                System.out.println(result);
                                session.updateSession(mAddress, mPhone);
                                Toast.makeText(getApplication(), "Edit Profile Sukses", Toast.LENGTH_LONG).show();
                                showProgress(false);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplication(), "Internal Server Error", Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("phone", mPhone);
                    params.put("address", mAddress);

                    return params;
                }

                /** Passing some request headers* */
                @Override
                public Map getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Authorization", "Bearer " + mToken);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(EditUserProfile.this);
            requestQueue.add(postRequest);
        }
    }

}

