package com.example.vitorizkiimanda.sisuper_apps.data;

import android.os.Parcel;
import android.os.Parcelable;

public class BusinessClass implements Parcelable{
    private String ID;
    private String category;
    private String namaUsaha;
    private String lamaUsaha;
    private String omzetUsaha;
    private String deskripsiUsaha;
    private String alamatUsaha;
    private String emailUsaha;
    private String teleponUsaha;
    private String websiteUsaha;
    private String lineUsaha;
    private String facebokUsaha;
    private String twitterUsaha;
    private String instagramUsaha;
    private String certificate;
    private String certificateId;
    String logoUsaha;

    public BusinessClass(){
        this.ID = "";
        this.category = "";
        this.namaUsaha = "";
        this.lamaUsaha = "";
        this.omzetUsaha = "";
        this.deskripsiUsaha = "";
        this.alamatUsaha ="";
        this.emailUsaha = "";
        this.teleponUsaha = "";
        this.websiteUsaha = "";
        this.lineUsaha = "";
        this.facebokUsaha = "";
        this.twitterUsaha = "";
        this.instagramUsaha = "";
        this.logoUsaha = "";
        this.certificate = "";
        this.certificateId = "";
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNamaUsaha() {
        return namaUsaha;
    }

    public void setNamaUsaha(String namaUsaha) {
        this.namaUsaha = namaUsaha;
    }

    public String getLamaUsaha() {
        return lamaUsaha;
    }

    public void setLamaUsaha(String lamaUsaha) {
        this.lamaUsaha = lamaUsaha;
    }

    public String getOmzetUsaha() {
        return omzetUsaha;
    }

    public void setOmzetUsaha(String omzetUsaha) {
        this.omzetUsaha = omzetUsaha;
    }

    public String getDeskripsiUsaha() {
        return deskripsiUsaha;
    }

    public void setDeskripsiUsaha(String deskripsiUsaha) {
        this.deskripsiUsaha = deskripsiUsaha;
    }

    public String getAlamatUsaha() {
        return alamatUsaha;
    }

    public void setAlamatUsaha(String alamatUsaha) {
        this.alamatUsaha = alamatUsaha;
    }

    public String getEmailUsaha() {
        return emailUsaha;
    }

    public void setEmailUsaha(String emailUsaha) {
        this.emailUsaha = emailUsaha;
    }

    public String getFacebokUsaha() {
        return facebokUsaha;
    }

    public void setFacebokUsaha(String facebokUsaha) {
        this.facebokUsaha = facebokUsaha;
    }

    public String getInstagramUsaha() {
        return instagramUsaha;
    }

    public void setInstagramUsaha(String instagramUsaha) {
        this.instagramUsaha = instagramUsaha;
    }

    public String getLineUsaha() {
        return lineUsaha;
    }

    public void setLineUsaha(String lineUsaha) {
        this.lineUsaha = lineUsaha;
    }

    public String getTeleponUsaha() {
        return teleponUsaha;
    }

    public void setTeleponUsaha(String teleponUsaha) {
        this.teleponUsaha = teleponUsaha;
    }

    public String getTwitterUsaha() {
        return twitterUsaha;
    }

    public void setTwitterUsaha(String twitterUsaha) {
        this.twitterUsaha = twitterUsaha;
    }

    public String getWebsiteUsaha() {
        return websiteUsaha;
    }

    public void setWebsiteUsaha(String websiteUsaha) {
        this.websiteUsaha = websiteUsaha;
    }

    public String getLogoUsaha() {
        String[] Pisah = logoUsaha.split("\\.");
        return Pisah[0];
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCertificate() {
        String[] Pisah = certificate.split("\\.");
        return Pisah[0];
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setLogoUsaha(String logoUsaha) {
        this.logoUsaha = logoUsaha;
    }

    protected BusinessClass(Parcel in) {
        this.ID = in.readString();
        this.category = in.readString();
        this.namaUsaha = in.readString();
        this.lamaUsaha = in.readString();
        this.omzetUsaha = in.readString();
        this.deskripsiUsaha = in.readString();
        this.alamatUsaha = in.readString();
        this.emailUsaha = in.readString();
        this.teleponUsaha = in.readString();
        this.websiteUsaha = in.readString();
        this.lineUsaha = in.readString();
        this.facebokUsaha = in.readString();
        this.twitterUsaha = in.readString();
        this.instagramUsaha = in.readString();
        this.logoUsaha = in.readString();
        this.certificate = in.readString();
        this.certificateId = in.readString();
    }

    public static final Creator<BusinessClass> CREATOR = new Creator<BusinessClass>() {
        @Override
        public BusinessClass createFromParcel(Parcel in) {
            return new BusinessClass(in);
        }

        @Override
        public BusinessClass[] newArray(int size) {
            return new BusinessClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.ID);
        parcel.writeString(this.category);
        parcel.writeString(this.namaUsaha);
        parcel.writeString(this.lamaUsaha);
        parcel.writeString(this.omzetUsaha);
        parcel.writeString(this.deskripsiUsaha);
        parcel.writeString(this.emailUsaha);
        parcel.writeString(this.alamatUsaha);
        parcel.writeString(this.teleponUsaha);
        parcel.writeString(this.websiteUsaha);
        parcel.writeString(this.lineUsaha);
        parcel.writeString(this.facebokUsaha);
        parcel.writeString(this.twitterUsaha);
        parcel.writeString(this.instagramUsaha);
        parcel.writeString(this.logoUsaha);
        parcel.writeString(this.certificate);
        parcel.writeString(this.certificateId);
    }
}
