package com.tavant.beaconretail.model;

/**
 * Created by rakesh.barik on 10-03-2015.
 */
public class DrawerMenuItem {
    private int imageId;
    private String title;

    public DrawerMenuItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
