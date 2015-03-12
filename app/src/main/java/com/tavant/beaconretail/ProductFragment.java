package com.tavant.beaconretail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tavant.beaconretail.model.ProductManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProductListAdapter mProductListAdapter;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProducts(),R.layout.product_row,getActivity());
        mRecyclerView.setAdapter(mProductListAdapter);

        return rootView;
    }


}
