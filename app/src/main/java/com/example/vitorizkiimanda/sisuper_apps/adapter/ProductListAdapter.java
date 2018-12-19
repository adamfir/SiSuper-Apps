package com.example.vitorizkiimanda.sisuper_apps.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.activity.ProductInput;
import com.example.vitorizkiimanda.sisuper_apps.activity.TambahUsahaActivity;
import com.example.vitorizkiimanda.sisuper_apps.data.BusinessClass;
import com.example.vitorizkiimanda.sisuper_apps.data.ProductClass;
import com.example.vitorizkiimanda.sisuper_apps.fragment.ProductListFragment;
import com.example.vitorizkiimanda.sisuper_apps.provider.EndPoints;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ProductClass> productList;
    private OnItemClickListener mListener;

    String productName;
    String productPrice;
    String productUnit;
    int priceFormated;
    Locale localeID = new Locale("in", "ID");

    public ProductListAdapter(Context context, ArrayList<ProductClass> productList){
        this.context = context;
        this.productList = productList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public int getItemViewType(int position){
        if (productList.get(position).getProductName() == "null") {
            return 1;
        } else if (productList.get(position) != null) {
            return 2;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View v = LayoutInflater.from(context).inflate(R.layout.adapter_product_list_button, parent, false);
            return new ViewHolderTwo(v);
        }
        else {
            View v = LayoutInflater.from(context).inflate(R.layout.adapter_product_list, parent, false);
            return new ViewHolder(v);
        }
    }

    private void initLayoutList(ViewHolder holder, int position){
        holder.productName.setText(productName);
        holder.productPrice.setText(productPrice);
        holder.productUnit.setText(productUnit);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();

        ProductClass currentEvent = productList.get(position);


//        //format price
//        String temp = currentEvent.getProductPrice();
//        priceFormated = Integer.parseInt(temp);
//        java.text.NumberFormat format = java.text.ChoiceFormat.getCurrencyInstance(localeID);
//

        productName = currentEvent.getProductName();
//        productPrice = format.format(priceFormated);
        productPrice = "Rp."+currentEvent.getProductPrice();
        productUnit = currentEvent.getProductUnit();

        if(viewType == 2){
            initLayoutList((ViewHolder) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView productName;
        TextView productPrice;
        TextView productUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.list_product_name);
            productPrice = itemView.findViewById(R.id.list_product_price);
            productUnit = itemView.findViewById(R.id.list_product_unit);

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
                Intent moveIntent = new Intent(context, ProductInput.class);
                context.startActivity(moveIntent);
                }
            });
        }
    }


}
