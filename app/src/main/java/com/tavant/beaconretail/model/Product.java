package com.tavant.beaconretail.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rakesh.barik on 06-03-2015.
 */
public class Product implements Parcelable {
    public static final Product NONE = new Product();

    private int id;
    private int sectionId;
    private String beaconId;
    private int offerId;
    private String name;
    private String description;
    private String imageUrl;
    private String price;
    private String size;
    private String sectionName;

    public Product(){}

    private Product(Parcel in){
        this.id = in.readInt();
        this.sectionId = in.readInt();
        this.beaconId = in.readString();
        this.offerId = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.price = in.readString();
        this.size = in.readString();
        this.sectionName = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public static final Creator<Product> CREATOR = new ClassLoaderCreator<Product>() {
        @Override
        public Product createFromParcel(Parcel source, ClassLoader loader) {
            return new Product(source);
        }

        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageUrl, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(sectionId);
        dest.writeString(beaconId);
        dest.writeInt(offerId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(price);
        dest.writeString(size);
        dest.writeString(sectionName);
    }
}
