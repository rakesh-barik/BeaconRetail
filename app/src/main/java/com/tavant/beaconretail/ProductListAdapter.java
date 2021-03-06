package com.tavant.beaconretail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tavant.beaconretail.model.Product;

import java.util.List;

/**
 * Created by rakesh.barik on 06-03-2015.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    List<Product> mProductList;
    int mRowLayout;
    Context mContext;
    ItemClickListener itemClickListener;

    public ProductListAdapter(List<Product> productList, int rowLayout,@NonNull ItemClickListener itemClickListener, Context context){
        this.mProductList = productList;
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
        final Product product = mProductList.get(position);
        if(product.getName() != null) holder.setProductName(product.getName());
        holder.productImage.setImageDrawable(mContext.getResources().getDrawable(product.getImageResourceId(mContext)));
        if(product.getDescription() != null) holder.setProductDescription(product.getDescription());
        if(product.getSize() != null)holder.setProductSize(product.getSize());
        if(product.getPrice() != null)holder.setProductPrice(product.getPrice());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    @Override
    public long getItemId(int position) {
        return mProductList.get(position).getId();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder{
        private final View parent;
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productDescription;
        private final TextView productSize;
        private final TextView productPrice;

        public static ViewHolder newInstance(View parent){
            ImageView productImage = (ImageView)parent.findViewById(R.id.productImage);
            TextView productName = (TextView) parent.findViewById(R.id.productName);
            TextView productDescription = (TextView) parent.findViewById(R.id.productDescription);
            TextView productSize = (TextView) parent.findViewById(R.id.productSize);
            TextView productPrice = (TextView) parent.findViewById(R.id.productPrice);

            return new ViewHolder(parent,productImage,productName,productDescription,productSize,productPrice);
        }

        private ViewHolder(View parent,ImageView productImage,TextView productName,TextView productDescription,TextView productSize,TextView productPrice) {
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

        public void setOnClickListener(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Product product);
    }
}
