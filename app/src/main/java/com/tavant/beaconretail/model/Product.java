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
    private int beaconId;

    private String name;
    private String description;
    private String imageName;
    private String price;
    private String size;

    public Product(){}

    private Product(Parcel in){
        this.id = in.readInt();
        this.sectionId = in.readInt();
        this.beaconId = in.readInt();

        this.name = in.readString();
        this.description = in.readString();
        this.imageName = in.readString();
        this.price = in.readString();
        this.size = in.readString();
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

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
            return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());

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
        dest.writeInt(beaconId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageName);
        dest.writeString(price);
        dest.writeString(size);
    }
}
