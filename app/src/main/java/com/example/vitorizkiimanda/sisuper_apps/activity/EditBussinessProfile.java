package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.fragment.ProductListFragment;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

import java.util.HashMap;

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
        LineUsaha = findViewById(R.id.twitter_edit);
        InstagramUsaha = findViewById(R.id.instagram_edit);
        parsingData();


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

    }
}
