package com.tavant.beaconretail;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tavant.beaconretail.model.Offer;
import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.net.OfferJsonParser;
import com.tavant.beaconretail.net.VolleySingleton;

import org.json.JSONArray;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class RecommendationFragment extends Fragment implements OfferListAdapter.ItemClickListener{
    private RecyclerView mRecyclerView;
    private OfferListAdapter mProductListAdapter;
    private String sectionIdentifier;

    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        Bundle args = getArguments();
        if (args != null) {
            sectionIdentifier = args.getString("identifier");
        }

        rootView = getRootView(inflater, container, rootView);

        if(ProductManager.getInstance().getGeneralOffer() == null)
        {
            getRecommendationsFromServer();
        }
        else {
            mProductListAdapter = new OfferListAdapter(ProductManager.getInstance().getProducts(), R.layout.offer_row,RecommendationFragment.this, getActivity());
            mRecyclerView.setAdapter(mProductListAdapter);
        }

        return rootView;
    }

    private View getRootView(LayoutInflater inflater, ViewGroup container, View rootView) {
        if (sectionIdentifier == null || sectionIdentifier.equals(getResources().getString(R.string.general_offer))) {
            rootView = inflater.inflate(R.layout.offers_fragment, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),0,false));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        } else if (sectionIdentifier.equals(getResources().getString(R.string.women_section_offer))) {
            rootView = inflater.inflate(R.layout.offer_row, container, false);
            ImageView offerImage = (ImageView) rootView.findViewById(R.id.offerImage);
            offerImage.setImageResource(R.drawable.womenoffer);
            TextView userName = (TextView) rootView.findViewById(R.id.offerDescription);
            userName.setText("Hi! Martina");
            /*TextView offerForWomen = (TextView) rootView.findViewById(R.id.offer_for);
            offerForWomen.setText("Pick Women's shoe at");*/
        } else if (sectionIdentifier.equals(getResources().getString(R.string.men_section_offer))) {
            rootView = inflater.inflate(R.layout.offer_row, container, false);
            ImageView offerImage = (ImageView) rootView.findViewById(R.id.offerImage);
            offerImage.setImageResource(R.drawable.menoffer);
            TextView userName = (TextView) rootView.findViewById(R.id.offerDescription);
            userName.setText("Hi! John Andrew");
            /*TextView offerForMen = (TextView) rootView.findViewById(R.id.offer_for);
            offerForMen.setText("Pick Men's shoe at");*/
        }
        return rootView;
    }

    private void getRecommendationsFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.offer_url), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                new OfferJsonParser(response);

                mProductListAdapter = new OfferListAdapter(ProductManager.getInstance().getProducts(),R.layout.recommendation_item,RecommendationFragment.this,getActivity());
                mRecyclerView.setAdapter(mProductListAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }


    @Override
    public void itemClicked(Product offer, View v) {
        View imageView = v.findViewById(R.id.productImage);
        String url = (String) imageView.getTag();
        ProductDetailActivity.launch(this,imageView,offer,url);
        getActivity().overridePendingTransition(0, 0);
    }
}
