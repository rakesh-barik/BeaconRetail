package com.tavant.beaconretail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tavant.beaconretail.model.Offer;
import com.tavant.beaconretail.model.Product;

import java.util.List;

/**
 * Created by rakesh.barik on 25-03-2015.
 */
public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.ViewHolder> {

    List<Product> mOfferList;
    int mRowLayout;
    Context mContext;
    ItemClickListener itemClickListener;

    public OfferListAdapter(List<Product> offerList, int rowLayout,@NonNull ItemClickListener itemClickListener, Context context){
        this.mOfferList = offerList;
        this.mRowLayout = rowLayout;
        this.mContext = context;
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(mRowLayout, viewGroup, false);

        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = mOfferList.get(position);
        if (product.getImageUrl() != null)
            holder.setProductImage(product.getImageUrl());

        if (product.getDescription() != null)
            holder.setProductDescription(product.getName());

        /*if (product.getSize() != null)
            holder.setProductSize(product.getSize());

        if (product.getPrice() != null)
            holder.setProductPrice(product.getPrice());*/

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(product,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOfferList == null ? 0 : mOfferList.size();
    }

    @Override
    public long getItemId(int position) {
        return mOfferList.get(position).getOfferId();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder{
        private final View parent;
        private final ImageView productImage;
        private final TextView productDescription;
        private final TextView productSize;
        private final TextView productPrice;

        public static ViewHolder newInstance(View parent){
            ImageView productImage = (ImageView)parent.findViewById(R.id.productImage);
            //TextView productName = (TextView) parent.findViewById(R.id.offer_for);
            TextView productDescription = (TextView) parent.findViewById(R.id.offerDescription);
            TextView productSize = (TextView) parent.findViewById(R.id.productSize);
            TextView productPrice = (TextView) parent.findViewById(R.id.productPrice);

            return new ViewHolder(parent,productImage,productDescription,productSize,productPrice);
        }

        private ViewHolder(View parent,ImageView productImage,TextView productDescription,TextView productSize,TextView productPrice) {
            super(parent);
            this.parent = parent;
            this.productImage = productImage;
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

        public void setOnClickListener(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }
    }

    /*public static final class ViewHolder extends RecyclerView.ViewHolder{
        private final View parent;
        private final ImageView offerImage;
        //private final TextView productName;
        private final TextView offerDescription;
        private final TextView productSize;
        private final TextView productPrice;

        public static ViewHolder newInstance(View parent){

            ImageView productImage = (ImageView)parent.findViewById(R.id.offerImage);
            //TextView productName = (TextView) parent.findViewById(R.id.offer_for);
            TextView productDescription = (TextView) parent.findViewById(R.id.offerDescription);
            TextView productSize = (TextView) parent.findViewById(R.id.productSize);
            TextView productPrice = (TextView) parent.findViewById(R.id.productPrice);

            return new ViewHolder(parent,productImage,productDescription,productSize,productPrice);
        }

        private ViewHolder(View parent,ImageView offerImage,TextView offerDescription,TextView productSize,TextView productPrice) {
            super(parent);
            this.parent = parent;
            this.offerImage = offerImage;
            //this.productName = productName;
            this.offerDescription = offerDescription;
            this.productSize = productSize;
            this.productPrice = productPrice;
        }



        public void setOfferImage(String productImageUrl) {
            //this.offerImage.setImageDrawable(mContext.getResources().getDrawable(product.getImageResourceId(mContext)));;
        }



        *//*public void setProductName(String productName) {
            this.productName.setText(productName);
        }*//*



        public void setOfferDescription(String offerDescription) {
            this.offerDescription.setText(offerDescription);
        }



        public void setProductSize(String productSize) {
            this.productSize.setText(productSize);
        }



        public void setProductPrice(String productPrice) {
            this.productPrice.setText(productPrice);
        }

        public View getOfferImage() {
            return offerImage;
        }
        public View getProductPrice() {
            return productPrice;
        }
        public View getProductSize() {
            return productSize;
        }

        public View getOfferDescription() {
            return offerDescription;
        }

        *//*public View getProductName() {
            return productName;
        }*//*

        public void setOnClickListener(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }
    }*/

    public interface ItemClickListener {
        void itemClicked(Product offer,View v);
    }
}
