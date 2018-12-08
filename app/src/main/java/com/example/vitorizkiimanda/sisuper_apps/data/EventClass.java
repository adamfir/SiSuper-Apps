package com.example.vitorizkiimanda.sisuper_apps.data;

import android.os.Parcel;
import android.os.Parcelable;

public class EventClass implements Parcelable {

    private String image;
    private String date;
    private String eventName;
    private String eventPlace;
    private String organized;
    private String description;

    public EventClass(){
        this.image = "";
        this.date = "";
        this.eventName = "";
        this.eventPlace = "";
        this.organized = "";
        this.description = "";

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganized() {
        return organized;
    }

    public void setOrganized(String organized) {
        this.organized = organized;
    }

    protected EventClass(Parcel in) {
        this.image = in.readString();
        this.date = in.readString();
        this.eventName = in.readString();
        this.eventPlace = in.readString();
        this.description = in.readString();
        this.organized = in.readString();
    }

    public static final Creator<EventClass> CREATOR = new Creator<EventClass>() {
        @Override
        public EventClass createFromParcel(Parcel in) {
            return new EventClass(in);
        }

        @Override
        public EventClass[] newArray(int size) {
            return new EventClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.image);
        parcel.writeString(this.date);
        parcel.writeString(this.eventName);
        parcel.writeString(this.eventPlace);
        parcel.writeString(this.description);
        parcel.writeString(this.organized);
    }
}
