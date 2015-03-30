package com.tavant.beaconretail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.tavant.beaconretail.model.Product;

import java.util.List;

/**
 * Created by rakesh.barik on 27-03-2015.
 */
public class SampleListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private final float SCROLL_MULTIPLIER = 0.5f;

    public static class VIEW_TYPES {
        public static final int NORMAL = 1;
        public static final int HEADER = 2;
        public static final int FIRST_VIEW = 3;
    }

    public interface OnParallaxScroll {
        /**
         * Event triggered when the parallax is being scrolled.
         *
         * @param percentage
         * @param offset
         * @param parallax
         */
        void onParallaxScroll(float percentage, float offset, View parallax);
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
