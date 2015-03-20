package com.tavant.beaconretail;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.net.VolleySingleton;

import org.json.JSONArray;


/**
 * A simple {@link Fragment} subclass.
 */
public class OffersFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductListAdapter mProductListAdapter;
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

        if (sectionIdentifier == null || sectionIdentifier.equals(getResources().getString(R.string.general_offer))) {
            rootView = inflater.inflate(R.layout.offers_fragment, container, false);
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getGeneralOffer(), R.layout.product_row, getActivity());
            mRecyclerView.setAdapter(mProductListAdapter);
        } else if (sectionIdentifier.equals(getResources().getString(R.string.women_section_offer))) {
            rootView = inflater.inflate(R.layout.offer_row, container, false);
            ImageView productImage = (ImageView) rootView.findViewById(R.id.productImage);
            productImage.setImageResource(R.drawable.womenoffer);
            TextView offerForWomen = (TextView) rootView.findViewById(R.id.offer_for);
            offerForWomen.setText("Pick Women's shoe at");
        } else if (sectionIdentifier.equals(getResources().getString(R.string.men_section_offer))) {
            rootView = inflater.inflate(R.layout.offer_row, container, false);
            ImageView productImage = (ImageView) rootView.findViewById(R.id.productImage);
            productImage.setImageResource(R.drawable.menoffer);
            TextView offerForMen = (TextView) rootView.findViewById(R.id.offer_for);
            offerForMen.setText("Pick Men's shoe at");
        }
        return rootView;
    }

    private void getOffersFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.offer_url), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }

}
