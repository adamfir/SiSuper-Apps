package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vitorizkiimanda.sisuper_apps.BuildConfig;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import static android.os.Build.ID;

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


    private static final int STORAGE_PERMISSION_CODE = 123;
    private String Username;
    private String Email;
    private String Address;
    private String Phone;
    private String Images;
    private String ID;
    private String Token;

    String imageFilePath;


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
        AlamatUsaha = findViewById(R.id.email_tambah_usaha);
        EmailUsaha = findViewById(R.id.email_tambah_usaha);
        TeleponUsaha = findViewById(R.id.telepon_tambah_usaha);
        WebsiteUsaha = findViewById(R.id.website_tambah_usaha);
        FacebookUsaha = findViewById(R.id.facebook_tambah_usaha);
        TwitterUsaha = findViewById(R.id.twitter_tambah_usaha);
        LineUsaha = findViewById(R.id.line_tambah_usaha);
        InstagramUsaha = findViewById(R.id.instagram_tambah_usaha);

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
                simpanUsaha(logoUsaha);
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

    private void simpanUsaha(Uri filePath){
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


        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //getting the actual path of the image
            String path = getPath(filePath);

            //Creating a multi part request
            new MultipartUploadRequest(this, "1", EndPoints.ROOT_URL+"/business/addBusiness")
                    .addFileToUpload(path, "businessPicture") //Adding file
                    .addParameter("userId", ID).addParameter("businessName", namaUsaha).addParameter("businessCategory", "haha")
                    .addParameter("establishedDate", lamaUsaha).addParameter("businessRevenue", omzetUsaha).addParameter("businessDescription", deskripsiUsaha)
                    .addParameter("businessAddress", alamatUsaha).addParameter("businessEmail", emailUsaha).addParameter("businessPhone", teleponUsaha)
                    .addParameter("businessSite", websiteUsaha).addParameter("businessFacebook", facebookUsaha).addParameter("businessTwitter", twitterUsaha)
                    .addParameter("businessLine", lineUsaha).addParameter("businessInstagram", instagramUsaha)
                    .addHeader("Authorization", "Bearer " + Token)
                    .setMaxRetries(2)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .startUpload(); //Starting the upload

            Toast.makeText(this, "Upload Sukses", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }


        System.out.println(namaUsaha);
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


}
