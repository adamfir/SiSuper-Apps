package com.example.vitorizkiimanda.sisuper_apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.BusinessListActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.TambahUsahaActivity;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.data.EventClass;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;

import java.util.ArrayList;

public class BusinessListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = "BusinessListAdapter";
    private Context context;
    private ArrayList<BusinessClass> businessList;
    private OnItemClickListener mListener;
    String namaUsaha;
    String logoUsaha;

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


    public int getItemViewType(int position){
        if (businessList.get(position).getID() == "null") {
            return 1;
        } else if (businessList.get(position) != null) {
            return 2;
        }
        return -1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View s = LayoutInflater.from(context).inflate(R.layout.adapter_new_business, parent, false);
            return new ViewHolderTwo(s);
        }
        else{
            View v = LayoutInflater.from(context).inflate(R.layout.adapter_business_list, parent, false);
            return new ViewHolderOne(v);
        }
    }

    private void initLayoutList(ViewHolderOne holder, int position){
        holder.namaUsaha.setText(namaUsaha);

        if(logoUsaha != null){
            Glide.with(context).load(EndPoints.ROOT_URL+"/business/getBusinessPicture/" + logoUsaha).into(holder.image);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        BusinessClass currentBusiness = businessList.get(position);

        namaUsaha = currentBusiness.getNamaUsaha();
        logoUsaha = currentBusiness.getLogoUsaha();
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


        if(viewType == 2){
            initLayoutList((ViewHolderOne) holder, position);
        }

//        holder.numberInvitation.setText(eventLocation);
//        holder.date.setText(eventDate);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder{

        ImageView image;
        TextView namaUsaha;
        TextView numberInvitation;
        public ViewHolderOne(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_business_list);
            namaUsaha = itemView.findViewById(R.id.name_business_list);
//            numberInvitation = itemView.findViewById(R.id.number_business_list);

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
    public class ViewHolderTwo extends RecyclerView.ViewHolder{

        public ViewHolderTwo(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent movesIntent = new Intent(context, TambahUsahaActivity.class);
                    context.startActivity(movesIntent);
                }
            });
        }
    }
}
