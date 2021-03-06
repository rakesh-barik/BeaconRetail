package com.tavant.beaconretail;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.poliveira.parallaxrecyclerview.HeaderLayoutManagerFixed;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.squareup.picasso.Picasso;
import com.tavant.beaconretail.animator.SlideInOutLeftItemAnimator;
import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements ParallaxRecyclerAdapter.OnClickEvent, ParallaxRecyclerAdapter.OnParallaxScroll{
    private RecyclerView mRecyclerView;
    private ParallaxRecyclerAdapter adapter;
    private Toolbar mToolbar;
    private BaseActivity activity;
    private TextView tvTotalPrice;
    double totalPrice = 0;
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(false).setEnabled(false);
        menu.findItem(R.id.action_cart).setEnabled(false).setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_cart, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pay:
                BaseActivity.showPopUp(this.getTotalPrice(),getResources().getString(R.string.check_out),getActivity());
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        tvTotalPrice = (TextView)rootView.findViewById(R.id.totalPrice);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Cart");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (ProductManager.getInstance().getCartProducts() == null) {
            Toast.makeText(getActivity(),"No items in the cart",Toast.LENGTH_LONG).show();
        } else {
            createCardAdapter(ProductManager.getInstance().getCartProducts());
        }

        return rootView;
    }

    public void setFromSearch(String queryText){
        if(ProductManager.getInstance().getProducts() != null){
            List<Product> searchedProduct = new ArrayList<Product>();
            for(Product product : ProductManager.getInstance().getProducts()){
                if(product.getName().toLowerCase().contains(queryText.toLowerCase())){
                    searchedProduct.add(product);
                }
            }
            if (searchedProduct != null){
                createCardAdapter(searchedProduct);
            }
        }
    }

    private void createCardAdapter(List<Product> products) {
        adapter = new ParallaxRecyclerAdapter(products);
        HeaderLayoutManagerFixed layoutManagerFixed = new HeaderLayoutManagerFixed(getActivity());
        mRecyclerView.setLayoutManager(layoutManagerFixed);
        View header = getActivity().getLayoutInflater().inflate(R.layout.cart_header, mRecyclerView, false);
        layoutManagerFixed.setHeaderIncrementFixer(header);
        adapter.setShouldClipView(false);
        adapter.setParallaxHeader(header, mRecyclerView);
        adapter.setData(products);
        adapter.setOnClickEvent(CartFragment.this);
        adapter.setOnParallaxScroll(CartFragment.this);
        adapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                if(adapter.getData().size() > i) {
                    final Product product = (Product) adapter.getData().get(i);
                    if (product.getName() != null)
                        ((ProductViewHolder) viewHolder).setProductName(product.getName());

                    if(product.getImageUrl() != null)
                        ((ProductViewHolder) viewHolder).setProductImage(product.getImageUrl());

                    if (product.getDescription() != null)
                        ((ProductViewHolder) viewHolder).setProductDescription(product.getDescription());
                    if (product.getSize() != null)
                        ((ProductViewHolder) viewHolder).setProductSize(product.getSize());
                    if (product.getPrice() != null)
                        ((ProductViewHolder) viewHolder).setProductPrice(product.getPrice());
                }
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
                return adapter.getData().size();
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
        setTotalPrice();
    }

    private void setTotalPrice() {
        totalPrice =  0;
        List<Product> cartProducts = adapter.getData();
        for(Product product : cartProducts){
            double itemPrice = Double.valueOf(product.getPrice().substring(1));
            totalPrice = totalPrice + itemPrice ;
        }
        this.tvTotalPrice.setText("$" + String.valueOf(totalPrice));
    }

    private double getTotalPrice(){
        return totalPrice;
    }


    public void itemClicked(View v,Product product) {
        View imageView = v.findViewById(R.id.productImage);
        String url = (String) imageView.getTag();
        ProductDetailActivity.launch(this,imageView,product,url);
    }

    @Override
    public void onClick(View v, int position) {
        if (position != -1) {
            itemClicked(v,(Product) adapter.getData().get(position));
        }
    }

    @Override
    public void onLongClick(View v, int position) {
        if (position != -1) {
            showAlertDialog(position);

        }
    }

    private void showAlertDialog(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(R.string.decision);
        alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.removeItem(adapter.getData().get(position));
                adapter.notifyDataSetChanged();
                setTotalPrice();
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
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
