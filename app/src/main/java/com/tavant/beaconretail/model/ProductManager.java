package com.tavant.beaconretail.model;


import com.tavant.beaconretail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 06-03-2015.
 */
public class ProductManager {
    private static int[] productImageArray = {R.drawable.shoe1,R.drawable.shoe11,R.drawable.shoe12,R.drawable.shoe13,R.drawable.shoe14,R.drawable.jacket,R.drawable.jacket2,R.drawable.tshirt};
    private static String[] generalProductArray = {"Shoe1", "Shoe11", "jacket", "pant", "phone", "jacket2", "Shoe11", "Shoe12", "Shoe13", "Shoe14", "jacket", "jacket2", "tshirt"};
    private static String[] offerForWomenArray = {"womenoffer"};
    private static String[] offerForMenArray = {"menoffer"};
    private static String[] generalOfferArray = {"Shoe1", "jacket", "pant", "phone", "jacket2", "Shoe14", "jacket"};
    private static String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut " +
            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
            "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private static ProductManager mInstance;
    private List<Product> products;
    private List<Offer> productsforWomen;
    private List<Offer> productsforMen;
    private List<Offer> generalOffer;

    public static ProductManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProductManager();
        }

        return mInstance;
    }

    public List<Product> getProducts() {
        if (products == null) {
            return null;
        } else {
            return products;
        }
    }

    public void setProducts(List<Product> productList) {
        if (products == null) {
            products = new ArrayList<>();

            products.addAll(productList);
            /*for (Product downloadedProduct : productList) {
                for (String productName : generalProductArray) {

                    downloadedProduct.setImageUrl(productName.replaceAll("\\s+", "").toLowerCase());

                }

                products.add(downloadedProduct);
            }*/
        }
    }

    //Using duplicate method to return different set of products for now
    public List<Offer> getOffersForWomen() {
        if (productsforWomen == null) {
            return null;
        }

        return productsforWomen;
    }

    public void setProductsforWomen(List<Offer> offersForWomen) {
        if(productsforWomen == null){
            productsforWomen = new ArrayList<Offer>();
        }

        this.productsforWomen.clear();
        this.productsforWomen.addAll(offersForWomen);
    }

    public void setProductsforMen(List<Offer> offersforMen) {
        if(productsforMen == null){
            productsforMen = new ArrayList<Offer>();
        }

        this.productsforMen.clear();
        this.productsforMen.addAll(offersforMen);
    }

    //Using duplicate method to return different set of products for now
    public List<Offer> getOffersForMen() {
        if (productsforMen == null) {
            return null;
        }

        return productsforMen;
    }

    //Using duplicate method to return different set of products for now
    public List<Offer> getGeneralOffer() {
        if (generalOffer == null) {
            return null;
        }

        return generalOffer;
    }

    public void setGeneralOffer(List<Offer> offerList) {
        if(generalOffer == null){
            generalOffer = new ArrayList<Offer>();
        }
        this.generalOffer.addAll(offerList);
    }


}
