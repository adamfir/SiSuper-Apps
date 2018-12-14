package com.example.vitorizkiimanda.sisuper_apps.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;

import java.util.ArrayList;

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.ViewHolder>{
    private static final String TAG = "BusinessListAdapter";
    private Context context;
    private ArrayList<BusinessClass> businessList;
    private OnItemClickListener mListener;

    public BusinessListAdapter(Context context, ArrayList<BusinessClass> businessList){
        this.context = context;
        this.businessList = businessList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_business_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusinessClass currentBusiness = businessList.get(position);

        String namaUsaha = currentBusiness.getNamaUsaha();
        String lamaUsaha = currentBusiness.getLamaUsaha();
        String omzetUsaha = currentBusiness.getOmzetUsaha();
        String deskripsiUsaha = currentBusiness.getDeskripsiUsaha();
        String alamatUsaha = currentBusiness.getAlamatUsaha();
        String emailUsaha = currentBusiness.getEmailUsaha();
        String teleponUsaha = currentBusiness.getTeleponUsaha();
        String websiteUsaha = currentBusiness.getWebsiteUsaha();
        String lineUsaha = currentBusiness.getLineUsaha();
        String facebookUsaha = currentBusiness.getFacebokUsaha();
        String twitterUsaha = currentBusiness.getTwitterUsaha();
        String instagramUsaha = currentBusiness.getInstagramUsaha();


        holder.namaUsaha.setText(namaUsaha);
//        holder.numberInvitation.setText(eventLocation);
//        holder.date.setText(eventDate);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView namaUsaha;
        TextView numberInvitation;
        RelativeLayout parentEventListLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_business_list);
            namaUsaha = itemView.findViewById(R.id.name_business_list);
            numberInvitation = itemView.findViewById(R.id.number_business_list);

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
}
