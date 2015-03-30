package com.tavant.beaconretail.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by rakesh.barik on 25-03-2015.
 */
public class Offer implements Parcelable {

    private int offerId;
    private int sectionId;
    private int productId;
    private String beaconId;
    private String offerHeading;
    private String offerDescription;
    private boolean onEntryOffer;
    private boolean onExitOffer;

    private List<Product> productsOnOffer;

    public Offer(){}

    public Offer(Parcel in){
        this.offerId = in.readInt();
        this.sectionId = in.readInt();
        this.productId = in.readInt();
        this.beaconId = in.readString();
        this.offerHeading = in.readString();
        this.offerDescription = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getOfferHeading() {
        return offerHeading;
    }

    public void setOfferHeading(String offerHeading) {
        this.offerHeading = offerHeading;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public boolean isOnEntryOffer() {
        return onEntryOffer;
    }

    public void setOnEntryOffer(boolean onEntryOffer) {
        this.onEntryOffer = onEntryOffer;
    }

    public boolean isOnExitOffer() {
        return onExitOffer;
    }

    public void setOnExitOffer(boolean onExitOffer) {
        this.onExitOffer = onExitOffer;
    }

    public List<Product> getProductsOnOffer() {
        return productsOnOffer;
    }

    public void setProductsOnOffer(List<Product> productsOnOffer) {
        this.productsOnOffer = productsOnOffer;
    }

    public static final Creator<Offer> CREATOR = new ClassLoaderCreator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source, ClassLoader loader) {
            return new Offer(source);
        }

        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
}
