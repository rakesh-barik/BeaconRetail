package com.tavant.beaconretail.model;

import android.content.Context;

/**
 * Created by rakesh.barik on 06-03-2015.
 */
public class Product {
    public String name;
    public String description;
    public String imageName;

    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
