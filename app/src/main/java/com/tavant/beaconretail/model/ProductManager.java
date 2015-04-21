package com.tavant.beaconretail.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by rakesh.barik on 06-03-2015.
 */
public class ProductManager {

    private static ProductManager mInstance;
    private List<Product> products;
    private List<Product> cartProducts;
    private List<Offer> generalOffer;
    private List<Section> sections;

    private HashMap<Integer,Product> sectionWiseProducts = new HashMap<>();
    private HashMap<Integer,Offer> sectionWiseOffers = new HashMap<>();


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
            setSectionWiseProduct();
        }
    }

    public List<Product> getCartProducts() {
        Map<Integer, Product> map = new LinkedHashMap<>();
        for (Product cartProduct : cartProducts) {
            map.put(Integer.valueOf(cartProduct.getId()), cartProduct);
        }
        cartProducts.clear();
        cartProducts.addAll(map.values());
        return cartProducts;
    }

    public void addProductToCart(Product cartProduct) {
        if(cartProducts == null){
            cartProducts = new ArrayList<>();
        }
        this.cartProducts.add(cartProduct);
    }


    //Using duplicate method to return different set of products for now
    public List<Offer> getGeneralOffer() {
        if (generalOffer == null) {
            return null;
        }else{
            return generalOffer;
        }

    }

    public void setGeneralOffer(List<Offer> offerList) {
        if(generalOffer == null){
            generalOffer = new ArrayList<Offer>();
        }
        this.generalOffer.addAll(offerList);
        setSectionWiseOffers();
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        if(this.sections == null){
            this.sections = new ArrayList<Section>();
        }
        this.sections.addAll(sections);
    }

    public Product getSectionWiseProduct(int sectionID) {
        return sectionWiseProducts.get(sectionID);
    }

    public void setSectionWiseProduct() {
        if(products != null){
            for(Product product:products){
                // for the demo we are just keeping only one product to show for each section.
                sectionWiseProducts.put(product.getSectionId(), product);
            }
        }
    }

    public void setSectionWiseOffers(){
        if(generalOffer != null){
            for(Offer offer : generalOffer){
                sectionWiseOffers.put(offer.getOfferId(),offer);
            }
        }
    }

    public Offer getSectionWiseOffer(int offerID){
        return sectionWiseOffers.get(offerID);
    }
}
