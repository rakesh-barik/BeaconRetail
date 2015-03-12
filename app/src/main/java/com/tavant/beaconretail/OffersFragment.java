package com.tavant.beaconretail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tavant.beaconretail.model.ProductManager;


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
        View rootView = inflater.inflate(R.layout.offers_fragment, container, false);
        Bundle args = getArguments();
        if(args!=null){
            sectionIdentifier = args.getString("identifier");
        }
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if(sectionIdentifier!=null)
            mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProductsforWomen(), R.layout.product_row, getActivity());
        else
            mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProducts(), R.layout.product_row, getActivity());
        mRecyclerView.setAdapter(mProductListAdapter);

        return rootView;
    }


}
