package com.tavant.beaconretail;

import android.content.Context;
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

    public ProductListAdapter(List<Product> productList, int rowLayout, Context context){
        this.mProductList = productList;
        this.mRowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mRowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mProductList.get(position);
        if(product.getName() != null) holder.productName.setText(product.getName());
        holder.productImage.setImageDrawable(mContext.getResources().getDrawable(product.getImageResourceId(mContext)));
        if(product.getDescription() != null) holder.productDescription.setText(product.getDescription());
        if(product.getSize() != null)holder.productSize.setText(product.getSize());
        if(product.getPrice() != null)holder.productPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productImage;
        public TextView productName;
        public TextView productDescription;
        public TextView productSize;
        public TextView productPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productImage = (ImageView)itemView.findViewById(R.id.productImage);
            productDescription = (TextView) itemView.findViewById(R.id.productDescription);
            productSize = (TextView) itemView.findViewById(R.id.productSize);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
        }
    }
}
