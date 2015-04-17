package com.tavant.beaconretail.net;

import com.tavant.beaconretail.model.Offer;
import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 25-03-2015.
 */
public class OfferJsonParser {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String OFFER_ID        = "offerId";
    private static final String OFFER_HEADING   = "offerHeading";
    private static final String OFFER_DESC      = "offerDescription";
    private static final String ENTRY_OFFER     = "onEntryOffer";
    private static final String EXIT_OFFER      = "onExitOffer";


    private List<Offer> offerList;


    public
    OfferJsonParser(JSONArray response){
        offerList = new ArrayList<>();
        parseJsonArray(response);
    }

    private void parseJsonArray(JSONArray response){
        for(int i = 0; i< response.length();i++){

            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Offer offer = new Offer();
                offer.setOfferId(jsonObject.getInt(OFFER_ID));
                offer.setOfferHeading(jsonObject.getString(OFFER_HEADING));
                offer.setOfferDescription(jsonObject.getString(OFFER_DESC));
                offer.setOnEntryOffer(jsonObject.getBoolean(ENTRY_OFFER));
                offer.setOnExitOffer(jsonObject.getBoolean(EXIT_OFFER));
                offerList.add(offer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ProductManager.getInstance().setGeneralOffer(offerList);
    }
}
