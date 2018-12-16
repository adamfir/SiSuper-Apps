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
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vitorizkiimanda.sisuper_apps.BuildConfig;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;
import com.example.vitorizkiimanda.sisuper_apps.provider.SingleUploadBroadcastReceiver;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class TambahUsahaActivity extends AppCompatActivity {

    Bitmap bitmap;
    ImageView LogoUsaha;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

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

    Uri logoUsaha;
    Button TambahUsaha;

    SessionManagement session;

    private final SingleUploadBroadcastReceiver uploadReceiver = new SingleUploadBroadcastReceiver();


    private static final int STORAGE_PERMISSION_CODE = 123;
    private String Username;
    private String Email;
    private String Address;
    private String Phone;
    private String Images;
    private String ID;
    private String Token;

    private View mProgressView;
    private TambahUsahaTask tambahUsahaTask;

    boolean cancel = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_usaha);

        //define
        LogoUsaha = findViewById(R.id.logo_tambah_usaha);
        NamaUsaha = findViewById(R.id.nama_tambah_usaha);
        LamaUsaha = findViewById(R.id.lama_tambah_usaha);
        OmzetUsaha = findViewById(R.id.omzet_tambah_usaha);
        DeskripsiUsaha = findViewById(R.id.deskripsi_tambah_usaha);
        AlamatUsaha = findViewById(R.id.alamat_tambah_usaha);
        EmailUsaha = findViewById(R.id.email_tambah_usaha);
        TeleponUsaha = findViewById(R.id.telepon_tambah_usaha);
        WebsiteUsaha = findViewById(R.id.website_tambah_usaha);
        FacebookUsaha = findViewById(R.id.facebook_tambah_usaha);
        TwitterUsaha = findViewById(R.id.twitter_tambah_usaha);
        LineUsaha = findViewById(R.id.line_tambah_usaha);
        InstagramUsaha = findViewById(R.id.instagram_tambah_usaha);

        mProgressView = findViewById(R.id.edit_business_progress);

        TambahUsaha = findViewById(R.id.tambahUsaha);

        //picture permission
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        requestStoragePermission();

        //session
        session = new SessionManagement(getApplicationContext());

        //gettingData
        getData();

        LogoUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        TambahUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View focusView = null;
                cancel = false;

                //get text
                String namaUsaha = NamaUsaha.getText().toString();
                String lamaUsaha = LamaUsaha.getText().toString();
                String omzetUsaha = OmzetUsaha.getText().toString();
                String deskripsiUsaha = DeskripsiUsaha.getText().toString();
                String alamatUsaha = AlamatUsaha.getText().toString();
                String emailUsaha = EmailUsaha.getText().toString();
                String teleponUsaha = TeleponUsaha.getText().toString();
                String websiteUsaha = WebsiteUsaha.getText().toString();
                String facebookUsaha = FacebookUsaha.getText().toString();
                String twitterUsaha = TwitterUsaha.getText().toString();
                String lineUsaha = LineUsaha.getText().toString();
                String instagramUsaha = InstagramUsaha.getText().toString();

                // Check for a valid submit.
                if (TextUtils.isEmpty(namaUsaha)) {
                    NamaUsaha.setError(getString(R.string.error_field_required));
                    focusView = NamaUsaha;
                    cancel = true;
                }

                if (TextUtils.isEmpty(lamaUsaha)) {
                    LamaUsaha.setError(getString(R.string.error_field_required));
                    focusView = LamaUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(omzetUsaha)) {
                    OmzetUsaha.setError(getString(R.string.error_field_required));
                    focusView = OmzetUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(deskripsiUsaha)) {
                    DeskripsiUsaha.setError(getString(R.string.error_field_required));
                    focusView = DeskripsiUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(alamatUsaha)) {
                    AlamatUsaha.setError(getString(R.string.error_field_required));
                    focusView = AlamatUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(emailUsaha)) {
                    EmailUsaha.setError(getString(R.string.error_field_required));
                    focusView = EmailUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(teleponUsaha)) {
                    TeleponUsaha.setError(getString(R.string.error_field_required));
                    focusView = TeleponUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(websiteUsaha)) {
                    WebsiteUsaha.setError(getString(R.string.error_field_required));
                    focusView = WebsiteUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(facebookUsaha)) {
                    FacebookUsaha.setError(getString(R.string.error_field_required));
                    focusView = FacebookUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(twitterUsaha)) {
                    TwitterUsaha.setError(getString(R.string.error_field_required));
                    focusView = TwitterUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(lineUsaha)) {
                    LineUsaha.setError(getString(R.string.error_field_required));
                    focusView = LineUsaha;
                    cancel = true;
                }
                if (TextUtils.isEmpty(instagramUsaha)) {
                    InstagramUsaha.setError(getString(R.string.error_field_required));
                    focusView = InstagramUsaha;
                    cancel = true;
                }


                if (cancel){
                    focusView.requestFocus();
                }
                else if(logoUsaha == null){
                    Toast.makeText(TambahUsahaActivity.this, "Pilih Gambar Usaha", Toast.LENGTH_SHORT).show();
                }
                else {
                    showProgress(true);
                    tambahUsahaTask =  new TambahUsahaTask(logoUsaha, namaUsaha, lamaUsaha, omzetUsaha, deskripsiUsaha, alamatUsaha, emailUsaha, teleponUsaha, websiteUsaha, lineUsaha, facebookUsaha, twitterUsaha, instagramUsaha);
                    tambahUsahaTask.execute((Void) null);
                }
            }
        });

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
            cancel = false;
            if (requestCode == REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                logoUsaha = data.getData();
                bitmap = (Bitmap) bundle.get("data");
                LogoUsaha.setImageBitmap(bitmap);

            }
            else if(requestCode == SELECT_FILE){
                logoUsaha = data.getData();
                Log.d("uri", logoUsaha.toString());

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), logoUsaha);
                    LogoUsaha.setImageBitmap(bitmap);

                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println(e.toString());
                }
            }
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
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    public class TambahUsahaTask extends AsyncTask<Void, Void, Boolean> implements SingleUploadBroadcastReceiver.Delegate{
        private final Uri uri;
        private final String namaUsaha;
        private final String lamaUsaha;
        private final String omzetUsaha;
        private final String deskripsiUsaha;
        private final String alamatUsaha;
        private final String emailUsaha;
        private final String teleponUsaha;
        private final String websiteUsaha;
        private final String lineUsaha;
        private final String facebookUsaha;
        private final String twitterUsaha;
        private final String instagramUsaha;

        TambahUsahaTask(Uri uri, String namaUsaha, String lamaUsaha, String omzetUsaha, String deskripsiUsaha, String alamatUsaha, String emailUsaha, String teleponUsaha, String websiteUsaha, String lineUsaha, String facebookUsaha, String twitterUsaha, String instagramUsaha){

            this.uri = uri;
            this.namaUsaha = namaUsaha;
            this.lamaUsaha = lamaUsaha;
            this.omzetUsaha = omzetUsaha;
            this.deskripsiUsaha = deskripsiUsaha;
            this.alamatUsaha = alamatUsaha;
            this.emailUsaha = emailUsaha;
            this.teleponUsaha = teleponUsaha;
            this.websiteUsaha = websiteUsaha;
            this.lineUsaha = lineUsaha;
            this.facebookUsaha = facebookUsaha;
            this.twitterUsaha = twitterUsaha;
            this.instagramUsaha = instagramUsaha;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            simpanUsaha(uri, namaUsaha, lamaUsaha, omzetUsaha, deskripsiUsaha, alamatUsaha, emailUsaha, teleponUsaha, websiteUsaha, lineUsaha, facebookUsaha, twitterUsaha, instagramUsaha);
            return null;
        }

        private void simpanUsaha(Uri filePath, String namaUsaha, String lamaUsaha, String omzetUsaha, String deskripsiUsaha, String alamatUsaha, String emailUsaha, String teleponUsaha, String websiteUsaha, String lineUsaha, String facebookUsaha, String twitterUsaha, String instagramUsaha){
                //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                uploadReceiver.setDelegate(this);
                uploadReceiver.setUploadID(uploadId);

                //getting the actual path of the image
                String path = getPath(filePath);

                //Creating a multi part request
                new MultipartUploadRequest(TambahUsahaActivity.this, uploadId, EndPoints.ROOT_URL+"/business/addBusiness")
                        .addFileToUpload(path, "businessPicture") //Adding file
                        .addParameter("userId", ID).addParameter("businessName", namaUsaha).addParameter("businessCategory", "haha")
                        .addParameter("establishedDate", lamaUsaha).addParameter("businessRevenue", omzetUsaha).addParameter("businessDescription", deskripsiUsaha)
                        .addParameter("businessAddress", alamatUsaha).addParameter("businessEmail", emailUsaha).addParameter("businessPhone", teleponUsaha)
                        .addParameter("businessSite", websiteUsaha).addParameter("businessFacebook", facebookUsaha).addParameter("businessTwitter", twitterUsaha)
                        .addParameter("businessLine", lineUsaha).addParameter("businessInstagram", instagramUsaha)
                        .addHeader("Authorization", "Bearer " + Token)
                        .setMaxRetries(2)
//                        .setNotificationConfig(new UploadNotificationConfig())
                        .startUpload(); //Starting the upload



            } catch (Exception exc) {
//                Toast.makeText(TambahUsahaActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onProgress(int progress) {
            System.out.println("sss");
        }

        @Override
        public void onProgress(long uploadedBytes, long totalBytes) {
        }

        @Override
        public void onError(Exception exception) {
            Toast.makeText(TambahUsahaActivity.this, "Gagal, Pilih Gambar", Toast.LENGTH_SHORT).show();
            showProgress(false);
        }

        public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
            //your implementation
            Toast.makeText(TambahUsahaActivity.this, "Upload Sukses", Toast.LENGTH_SHORT).show();
            showProgress(false);

            Intent moveIntent = new Intent(TambahUsahaActivity.this, BusinessListActivity.class);
            startActivity(moveIntent);
        }

        @Override
        public void onCancelled() {

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
    }
}
