package com.tavant.beaconretail.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 18-03-2015.
 */
public class ProductFeed implements Serializable {

    public static final ProductFeed NONE = new ProductFeed();

    private final List<Product> products = new ArrayList<Product>();

    public void addProducts(Product product){
        products.add(product);
    }

    public List<Product> getProducts(){
        return products;
    }
}
