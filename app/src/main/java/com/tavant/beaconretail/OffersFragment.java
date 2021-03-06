package com.tavant.beaconretail;


import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
 * A simple {@link Fragment} subclass.
 */
public class OffersFragment extends Fragment implements OfferListAdapter.ItemClickListener{
    private RecyclerView mRecyclerView;
    private OfferListAdapter mProductListAdapter;
    private String sectionIdentifier;

    public OffersFragment() {
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

        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Special Offers");
        ((ActionBarActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));



        rootView = getRootView(inflater, container, rootView);

        if(ProductManager.getInstance().getGeneralOffer() == null)
        {
            getOffersFromServer();
        }
        else {
            mProductListAdapter = new OfferListAdapter(ProductManager.getInstance().getProducts(), R.layout.offer_row,OffersFragment.this, getActivity());
            mRecyclerView.setAdapter(mProductListAdapter);
        }
        return rootView;
    }

    private View getRootView(LayoutInflater inflater, ViewGroup container, View rootView) {

            rootView = inflater.inflate(R.layout.offers_fragment, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    private void getOffersFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.offer_url), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                new OfferJsonParser(response);

                if (sectionIdentifier == null || sectionIdentifier.equals(getResources().getString(R.string.general_offer))) {
                mProductListAdapter = new OfferListAdapter(ProductManager.getInstance().getProducts(), R.layout.offer_row,OffersFragment.this, getActivity());
                mRecyclerView.setAdapter(mProductListAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void itemClicked(Product offer,View v) {
        View imageView = v.findViewById(R.id.productImage);
        String url = (String) imageView.getTag();
        ProductDetailActivity.launch(this,imageView,offer,url);
        getActivity().overridePendingTransition(0, 0);
    }
}
