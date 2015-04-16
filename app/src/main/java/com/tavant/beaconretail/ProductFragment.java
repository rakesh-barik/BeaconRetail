package com.tavant.beaconretail;


import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.squareup.picasso.Picasso;
import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.net.ProductJsonParser;
import com.tavant.beaconretail.net.VolleySingleton;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements ParallaxRecyclerAdapter.OnClickEvent, ParallaxRecyclerAdapter.OnParallaxScroll{
    private RecyclerView mRecyclerView;
    private ParallaxRecyclerAdapter adapter;
    private Toolbar mToolbar;
    private BaseActivity activity;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Beacon Retails");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (ProductManager.getInstance().getProducts() == null) {
            getProductsFromServer();
        } else {
            createCardAdapter(ProductManager.getInstance().getProducts());
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

                    createCardAdapter(ProductManager.getInstance().getProducts());


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

    public void setFromSearch(String queryText){
        if(ProductManager.getInstance().getProducts() != null){
            List<Product> searchedProduct = new ArrayList<Product>();
            for(Product product : ProductManager.getInstance().getProducts()){
                if(product.getName().contains(queryText)){
                    searchedProduct.add(product);
                }
            }
            createCardAdapter(searchedProduct);
        }
    }

    private void createCardAdapter(List<Product> products) {
        adapter = new ParallaxRecyclerAdapter(ProductManager.getInstance().getProducts());
        HeaderLayoutManagerFixed layoutManagerFixed = new HeaderLayoutManagerFixed(getActivity());
        mRecyclerView.setLayoutManager(layoutManagerFixed);
        View header = getActivity().getLayoutInflater().inflate(R.layout.header, mRecyclerView, false);
        layoutManagerFixed.setHeaderIncrementFixer(header);
        adapter.setShouldClipView(false);
        adapter.setParallaxHeader(header, mRecyclerView);
        adapter.setData(products);
        adapter.setOnClickEvent(ProductFragment.this);
        adapter.setOnParallaxScroll(ProductFragment.this);
        adapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                final Product product = (Product) adapter.getData().get(i);
                if (product.getName() != null)
                    ((ProductViewHolder) viewHolder).setProductName(product.getName());

                ((ProductViewHolder) viewHolder).setProductImage(getActivity().getResources().getString(R.string.image_url) + String.valueOf(i + 1));

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


    public void itemClicked(View v,Product product) {
        View imageView = v.findViewById(R.id.productImage);
        String url = (String) imageView.getTag();
        ProductDetailActivity.launch(this,imageView,url);
    }

    @Override
    public void onClick(View v, int position) {
        if (position != -1) {
           itemClicked(v,(Product) adapter.getData().get(position));
        }
    }

    @Override
    public void onParallaxScroll(float percentage, float offset, View parallax) {

        Drawable c = mToolbar.getBackground();
        c.setAlpha(Math.round(percentage * 255));
        mToolbar.setBackground(getActivity().getResources().getDrawable(R.color.primary));
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

            Picasso.with(this.parent.getContext())
                    .load(productImageUrl)
                    .into(productImage);
            this.productImage.setTag(productImageUrl);
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
