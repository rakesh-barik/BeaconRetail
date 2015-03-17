package com.tavant.beaconretail.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 06-03-2015.
 */
public class ProductManager {
    private static String[] generalProductArray = {"Shoe1", "Shoe11", "jacket", "pant", "phone", "jacket2","Shoe11","Shoe12","Shoe13","Shoe14","jacket","jacket2","tshirt"};
    private static String[] offerForWomenArray  = {"Shoe11","Shoe12","Shoe13","Shoe14"};
    private static String[] offerForMenArray    = {"jacket","jacket2","tshirt"};
    private static String[] generalOfferArray = {"Shoe1", "jacket", "pant", "phone", "jacket2","Shoe14","jacket"};
    private static String   loremIpsum          = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut " +
            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
            "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private static ProductManager mInstance;
    private List<Product> products;
    private List<Product> productsforWomen;
    private List<Product> productsforMen;
    private List<Product> generalOffer;

    public static ProductManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProductManager();
        }

        return mInstance;
    }

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<Product>();

            for (String productName : generalProductArray) {
                Product product = new Product();
                product.name = productName;
                product.description = loremIpsum;
                product.price = "2500";
                product.size = "S - XL";
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

            for (String productName : offerForWomenArray) {
                Product product = new Product();
                product.name = productName;
                product.description = loremIpsum;
                product.imageName = productName.replaceAll("\\s+","").toLowerCase();
                productsforWomen.add(product);
            }
        }

        return productsforWomen;
    }

    //Using duplicate method to return different set of products for now
    public List<Product> getProductsforMen() {
        if (productsforMen == null) {
            productsforMen = new ArrayList<Product>();

            for (String productName : offerForMenArray) {
                Product product = new Product();
                product.name = productName;
                product.description = loremIpsum;
                product.imageName = productName.replaceAll("\\s+","").toLowerCase();
                productsforMen.add(product);
            }
        }

        return productsforMen;
    }

    //Using duplicate method to return different set of products for now
    public List<Product> getGeneralOffer() {
        if (generalOffer == null) {
            generalOffer = new ArrayList<Product>();

            for (String productName : generalOfferArray) {
                Product product = new Product();
                product.name = productName;
                product.description = loremIpsum;
                product.imageName = productName.replaceAll("\\s+","").toLowerCase();
                generalOffer.add(product);
            }
        }

        return generalOffer;
    }

}
