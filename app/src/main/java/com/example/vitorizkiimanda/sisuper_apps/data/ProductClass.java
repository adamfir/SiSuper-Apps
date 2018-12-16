package com.example.vitorizkiimanda.sisuper_apps.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductClass implements Parcelable {
    private String productId;
    private String productName;
    private String productPrice;
    private String productUnit;

    public ProductClass(){
        this.productId = "";
        this.productName = "";
        this.productPrice = "";
        this.productUnit = "";
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductUnit() {
        return productUnit;
    }

    protected ProductClass(Parcel in) {
        this.productId = in.readString();
        this.productName = in.readString();
        this.productPrice = in.readString();
        this.productUnit = in.readString();
    }

    public static final Creator<ProductClass> CREATOR = new Creator<ProductClass>() {
        @Override
        public ProductClass createFromParcel(Parcel in) {
            return new ProductClass(in);
        }

        @Override
        public ProductClass[] newArray(int size) {
            return new ProductClass[size];
        }
    };

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.productId);
        parcel.writeString(this.productName);
        parcel.writeString(this.productPrice);
        parcel.writeString(this.productUnit);
    }

}
