package com.tavant.beaconretail.net;

import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 18-03-2015.
 */
public class ProductJsonParser {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String PRODUCT_ID      = "productId";
    private static final String PRODUCT_NAME    = "productName";
    private static final String PRODUCT_IMAGE   = "productImageUrl";
    private static final String PRODUCT_DESC    = "productDescription";
    private static final String PRODUCT_PRICE   = "price";
    private static final String PRODUCT_SIZE    = "size";
    private static final String SECTION_ID      = "sectionId";
    private static final String BEACON_ID       = "beaconId";

    private List<Product> productsList;


    public ProductJsonParser(JSONArray response){
        productsList = new ArrayList<>();
        parseJsonArray(response);
    }

    private void parseJsonArray(JSONArray response){
        for(int i = 0; i< response.length();i++){

            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Product product = new Product();
                product.setId(jsonObject.getInt(PRODUCT_ID));
                product.setName(jsonObject.getString(PRODUCT_NAME));
                product.setImageName(jsonObject.getString(PRODUCT_IMAGE));
                product.setDescription(jsonObject.getString(PRODUCT_DESC));
                product.setPrice(jsonObject.getString(PRODUCT_PRICE));
                product.setSize(jsonObject.getString(PRODUCT_SIZE));
                product.setSectionId(jsonObject.getInt(SECTION_ID));
                product.setBeaconId(jsonObject.getString(BEACON_ID));
                productsList.add(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ProductManager.getInstance().setProducts(productsList);
    }

}

