package com.tavant.beaconretail.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 06-03-2015.
 */
public class ProductManager {
    private static String[] productArray = {"Shoe1", "Shoe2", "Shoe3", "Shoe4", "Shoe5", "Shoe6"};
    private static  String[] productsForWomenArray = {"Shoe11","Shoe12","Shoe13","Shoe14"};
    private static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut " +
            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
            "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private static ProductManager mInstance;
    private List<Product> products;
    private List<Product> productsforWomen;

    public static ProductManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProductManager();
        }

        return mInstance;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<Product>();

            for (String productName : productArray) {
                Product product = new Product();
                product.name = productName;
                product.description = loremIpsum;
                product.imageName = productName.replaceAll("\\s+","").toLowerCase();
                products.add(product);
            }
        }

        return products;
    }

    //Using duplicate method to return different set of products for now
    public List<Product> getProductsforWomen() {
        if (productsforWomen == null) {
            productsforWomen = new ArrayList<Product>();

            for (String productName : productsForWomenArray) {
                Product product = new Product();
                product.name = productName;
                product.description = loremIpsum;
                product.imageName = productName.replaceAll("\\s+","").toLowerCase();
                productsforWomen.add(product);
            }
        }

        return productsforWomen;
    }

}
