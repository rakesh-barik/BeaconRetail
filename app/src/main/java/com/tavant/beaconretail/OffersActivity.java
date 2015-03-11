package com.tavant.beaconretail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.proximity.ProximityMarketing;


public class OffersActivity extends BaseActivity {

    private static final String TAG = OffersActivity.class.getSimpleName();
    private static final int NOTIFICATION_ID = 123;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProximityMarketing app = (ProximityMarketing) getApplication();
        app.startProximityMarketing();
    
    /*String msg = (String) getIntent().getCharSequenceExtra("Offer");
    TextView statusTextView = (TextView) findViewById(R.id.status);
    statusTextView.setText(msg);*/

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProducts(), R.layout.product_row, this);
        mRecyclerView.setAdapter(mProductListAdapter);


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_offer;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
