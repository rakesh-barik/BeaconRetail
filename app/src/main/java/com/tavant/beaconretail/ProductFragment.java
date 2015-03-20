package com.tavant.beaconretail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.net.ProductJsonParser;
import com.tavant.beaconretail.net.VolleySingleton;

import org.json.JSONArray;

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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /*mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProducts(), R.layout.product_row, getActivity());
        mRecyclerView.setAdapter(mProductListAdapter);*/
        if(ProductManager.getInstance().getProducts() == null)
        {
            getProductsFromServer();
        }else{
            mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProducts(), R.layout.product_row, getActivity());
            mRecyclerView.setAdapter(mProductListAdapter);
        }
        return rootView;
    }

    private void getProductsFromServer() {
        JsonArrayRequest request = new JsonArrayRequest(getResources().getString(R.string.feed_url),new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    VolleyLog.v("Response :%n %s",response.toString());
                    new ProductJsonParser(response);
                    mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProducts(), R.layout.product_row, getActivity());
                    mRecyclerView.setAdapter(mProductListAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

}
