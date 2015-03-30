package com.tavant.beaconretail;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.poliveira.parallaxrecyclerview.HeaderLayoutManagerFixed;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.net.ProductJsonParser;
import com.tavant.beaconretail.net.VolleySingleton;

import org.json.JSONArray;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements ParallaxRecyclerAdapter.OnClickEvent{
    private RecyclerView mRecyclerView;
    private ParallaxRecyclerAdapter adapter;

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

        if (ProductManager.getInstance().getProducts() == null) {
            getProductsFromServer();
        } else {
            createCardAdapter();
        }
        return rootView;
    }

    private void getProductsFromServer() {
        JsonArrayRequest request = new JsonArrayRequest(getResources().getString(R.string.feed_url), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    VolleyLog.v("Response :%n %s", response.toString());
                    new ProductJsonParser(response);

                    createCardAdapter();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    private void createCardAdapter() {
        adapter = new ParallaxRecyclerAdapter(ProductManager.getInstance().getProducts());
        HeaderLayoutManagerFixed layoutManagerFixed = new HeaderLayoutManagerFixed(getActivity());
        mRecyclerView.setLayoutManager(layoutManagerFixed);
        View header = getActivity().getLayoutInflater().inflate(R.layout.header, mRecyclerView, false);
        layoutManagerFixed.setHeaderIncrementFixer(header);
        adapter.setShouldClipView(false);
        adapter.setParallaxHeader(header, mRecyclerView);
        adapter.setData(ProductManager.getInstance().getProducts());
        adapter.setOnClickEvent(ProductFragment.this);
        adapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                final Product product = (Product) adapter.getData().get(i);
                if (product.getName() != null)
                    ((ProductViewHolder) viewHolder).setProductName(product.getName());
                ((ProductViewHolder) viewHolder).productImage.setImageDrawable(getActivity().getResources().getDrawable(product.getImageResourceId(getActivity())));
                if (product.getDescription() != null)
                    ((ProductViewHolder) viewHolder).setProductDescription(product.getDescription());
                if (product.getSize() != null)
                    ((ProductViewHolder) viewHolder).setProductSize(product.getSize());
                if (product.getPrice() != null)
                    ((ProductViewHolder) viewHolder).setProductPrice(product.getPrice());
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

                Context context = viewGroup.getContext();
                View parent = LayoutInflater.from(context).inflate(R.layout.product_row, viewGroup, false);
                ProductViewHolder productViewHolder = ProductViewHolder.newInstance(parent);

                return productViewHolder;

            }

            @Override
            public int getItemCount() {
                return ProductManager.getInstance().getProducts().size();
            }
        });
        mRecyclerView.setAdapter(adapter);
    }


    public void itemClicked(Product product) {
        Intent detailIntent = new Intent(getActivity(), ProductDetailActivity.class);
        detailIntent.putExtra(ProductDetailActivity.ARG_ITEM, product);
        startActivity(detailIntent);
    }

    @Override
    public void onClick(View v, int position) {
        if (position != -1) {
           itemClicked((Product) adapter.getData().get(position));
        }
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productDescription;
        private final TextView productSize;
        private final TextView productPrice;

        public static ProductViewHolder newInstance(View parent) {
            ImageView productImage = (ImageView) parent.findViewById(R.id.productImage);
            TextView productName = (TextView) parent.findViewById(R.id.productName);
            TextView productDescription = (TextView) parent.findViewById(R.id.productDescription);
            TextView productSize = (TextView) parent.findViewById(R.id.productSize);
            TextView productPrice = (TextView) parent.findViewById(R.id.productPrice);

            return new ProductViewHolder(parent, productImage, productName, productDescription, productSize, productPrice);
        }

        private ProductViewHolder(View parent, ImageView productImage, TextView productName, TextView productDescription, TextView productSize, TextView productPrice) {
            super(parent);
            this.parent = parent;
            this.productImage = productImage;
            this.productName = productName;
            this.productDescription = productDescription;
            this.productSize = productSize;
            this.productPrice = productPrice;
        }


        public void setProductImage(String productImageUrl) {
            //this.productImage.setImageDrawable(mContext.getResources().getDrawable(product.getImageResourceId(mContext)));;
        }


        public void setProductName(String productName) {
            this.productName.setText(productName);
        }


        public void setProductDescription(String productDescription) {
            this.productDescription.setText(productDescription);
        }


        public void setProductSize(String productSize) {
            this.productSize.setText(productSize);
        }


        public void setProductPrice(String productPrice) {
            this.productPrice.setText(productPrice);
        }

        public View getProductImage() {
            return productImage;
        }

        public View getProductPrice() {
            return productPrice;
        }

        public View getProductSize() {
            return productSize;
        }

        public View getProductDescription() {
            return productDescription;
        }

        public View getProductName() {
            return productName;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
