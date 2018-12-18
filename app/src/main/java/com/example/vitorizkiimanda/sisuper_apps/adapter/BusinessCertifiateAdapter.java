package com.example.vitorizkiimanda.sisuper_apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.ProductInput;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.data.ProductClass;
import com.example.vitorizkiimanda.sisuper_apps.fragment.BussinessProfileFragment;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;

import java.security.cert.Certificate;
import java.util.ArrayList;

public class BusinessCertifiateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList <BusinessClass> certificateList;
    private OnItemClickListener mListener;

    String certificate;
    String certificateId;

    public BusinessCertifiateAdapter(Context context, ArrayList<BusinessClass> certificateList){
        this.context = context;
        this.certificateList = certificateList ;
    }

//    public static void setOnItemClickListener(BussinessProfileFragment bussinessProfileFragment) {
//    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public int getItemViewType(int position){
        if (certificateList.get(position).getCertificateId() == "sertifikat") {

            return 1;
        } else if (certificateList.get(position).getCertificateId() == "profil") {

            return 2;
        } else if (certificateList.get(position) != null) {
            return 3;
        }
        return -1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){

            View v = LayoutInflater.from(context).inflate(R.layout.adapter_add_certificate, parent, false);
            return new ViewHolderAdd(v);


        }
        else if(viewType == 2){
            View v = LayoutInflater.from(context).inflate(R.layout.adapter_business_edit_profile, parent, false);
            return new ViewHolderProfile(v);
        }
        else {
            View v = LayoutInflater.from(context).inflate(R.layout.adapter_business_certifiate, parent, false);
            return new ViewHolderCerificate(v);

        }
    }

    private void initLayoutList(ViewHolderCerificate holder, int position){
        Glide.with(context).load(EndPoints.ROOT_URL + "/certificates/getCertificatePict/" +certificate).into(holder.Certificate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();

        BusinessClass currentEvent = certificateList.get(position);
        certificate = currentEvent.getCertificate();
        certificateId = currentEvent.getCertificateId();

        if(viewType == 3){
            initLayoutList((ViewHolderCerificate) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return certificateList.size();
    }

    public class ViewHolderCerificate extends RecyclerView.ViewHolder{

        ImageView Certificate;
        TextView Name;

        public ViewHolderCerificate(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.business_certificate_name);
            Certificate = itemView.findViewById(R.id.business_certificate_pict);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public class ViewHolderAdd extends RecyclerView.ViewHolder{

        public ViewHolderAdd(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public class ViewHolderProfile extends RecyclerView.ViewHolder{

        public ViewHolderProfile(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent moveIntent = new Intent(context, ProductInput.class);
                    context.startActivity(moveIntent);
                }
            });
        }
    }
}
