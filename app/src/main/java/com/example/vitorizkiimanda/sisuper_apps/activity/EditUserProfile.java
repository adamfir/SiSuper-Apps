package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.vitorizkiimanda.sisuper_apps.BuildConfig;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditUserProfile extends AppCompatActivity {
    SessionManagement session;
    String Username;
    String Email;
    String Phone;
    String Address;
    String Images;
    String ID;
    String Token;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ImageView Image;
    Bitmap bitmap;

    private View mScrollView;
    private View mProgressView;
    private editProfileTask editProfileJobs = null;
    private editImageTask editImageJobs = null;
    private static final int STORAGE_PERMISSION_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        //session
        session = new SessionManagement(getApplicationContext());
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        requestStoragePermission();


        //parse data
        EditText Usernames = (EditText) findViewById(R.id.name_user_edit);
        EditText Emails = (EditText) findViewById(R.id.email_user_edit);
        final EditText Phones = (EditText) findViewById(R.id.phone_user_edit);
        final EditText Addresses = (EditText) findViewById(R.id.address_user_edit);
        Button editProfile  = (Button) findViewById(R.id.button_user_edit);
        Image = (ImageView) findViewById(R.id.image_user_edit);
        mScrollView = findViewById(R.id.profile_user_edit);
        mProgressView = findViewById(R.id.edit_user_progress);

        //get data
        getData();

        //settext
        Usernames.setText(Username);
        Emails.setText(Email);
        Phones.setText(Phone);
        Addresses.setText(Address);
        URI uri = URI.create("http://sisuper.codepanda.web.id/users/profilePicture/" + ID);
        Glide.with(getApplicationContext())
                .load(Uri.parse("http://sisuper.codepanda.web.id/users/profilePicture/" + ID))
                .apply(RequestOptions.signatureOf(new ObjectKey(Long.toString(System.currentTimeMillis()))))
                .into(Image);


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
        Images = (String) result.get("image");
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
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
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
                bitmap = (Bitmap) bundle.get("data");
                Image.setImageBitmap(bitmap);


            }
            else if(requestCode == SELECT_FILE){
                Uri selectImage = data.getData();
//                Image.setImageURI(selectImage);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectImage);
                    Image.setImageBitmap(bitmap);
                    uploadMultipart(selectImage);


                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println(e.toString());
                }
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();


        return path;
    }

    public void uploadMultipart(Uri filePath) {
        
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, "1", "http://sisuper.codepanda.web.id/users/editProfilePicture/5be6c607474dd72b66cbdf81")
                    .addFileToUpload(path, "userProfilePicture") //Adding file
                    .addHeader("Authorization", "Bearer " + Token)
                    .setMaxRetries(2)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .startUpload(); //Starting the upload

            Toast.makeText(this, "Upload Sukses", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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
            final String url = EndPoints.ROOT_URL+"/users/editProfile/" +mId;

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

    public class editImageTask extends  AsyncTask<Void, Void, Boolean>{
        private final String image;
        private final Bitmap images;

        public editImageTask(String image, Bitmap images) {
            this.image = image;
            this.images = images;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            imgUpload(this.images);
            return null;
        }

        public String getFileDataFromDrawable(Bitmap fotos) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            fotos.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        }


        private void imgUpload(final Bitmap fotos){
            final String url = EndPoints.ROOT_URL+"/testing";

            StringRequest postRequest  =  new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d("Response", response);

                            try {
                                JSONObject result = new JSONObject(response);
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
                    params.put("image", getFileDataFromDrawable(fotos));

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(EditUserProfile.this);
            requestQueue.add(postRequest);

        }
    }

}

