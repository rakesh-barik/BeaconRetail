package com.tavant.beaconretail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tavant.beaconretail.model.Offer;
import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.proximity.ProximityMarketing;
import com.tavant.beaconretail.proximity.UserEntryCheck;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 05-03-2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected Toolbar toolbar;

    protected static boolean isVisible = false;

    private static Context context;

    private static final int menBeaconId      = 1;
    private static final int womenBeaconId    = 2;
    private static final int kidsBeaconId     = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BaseActivity.this;
        setContentView(getLayoutResource());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }
    }

    protected abstract int getLayoutResource();

    protected void setActionBarIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setVisible(true);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        setVisible(false);
    }

    public static void showPopUp(final String section,Context displayContext) {

        final Dialog dialog = new Dialog(displayContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(!section.equalsIgnoreCase("checkout")){
            insidePremisesOffers(section, dialog);
        }else{
            checkoutOffers(dialog,displayContext);
        }
        dialog.show();

    }

    private static void checkoutOffers(final Dialog dialog, final Context displayContext) {
        dialog.setContentView(R.layout.checkout_popup);

        List<Product> cartProducts = ProductManager.getInstance().getCartProducts();
        String productNames = "";

        final Button walletButton = (Button) dialog.findViewById(R.id.wallet_button);
        walletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCart(displayContext, dialog);
            }
        });
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCart(displayContext, dialog);
            }
        });


    }

    private static void loadCart(Context displayContext, Dialog dialog) {
        UserEntryCheck.getInstance().setAnyPopUpShowing(false);
        Intent intent = new Intent(displayContext, LandingActivity.class);
        intent.putExtra("Section", "cart");
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        displayContext.startActivity(intent);
        dialog.dismiss();
    }

    private static void insidePremisesOffers(String section, final Dialog dialog) {
        dialog.setContentView(R.layout.offer_popup);

        Product product = null;
        Offer offer = null;

        TextView productName = (TextView)dialog.findViewById(R.id.productName);

        ImageView offerImage = (ImageView)dialog.findViewById(R.id.productImage);
        TextView offerDescription = (TextView)dialog.findViewById(R.id.offerDescription);
        TextView offerPrice = (TextView)dialog.findViewById(R.id.offerPrice);

        if(section.equalsIgnoreCase(context.getResources().getString(R.string.women_section_offer))){
            product = ProductManager.getInstance().getSectionWiseProduct(womenBeaconId);
            offer = ProductManager.getInstance().getSectionWiseOffer(product.getOfferId());

            Picasso.with(context).load(product.getImageUrl()).into(offerImage);
            productName.setText(product.getName());
            offerDescription.setText(offer.getOfferDescription());
            offerPrice.setText(offer.getOfferHeading());
        }else if (section.equalsIgnoreCase(context.getResources().getString(R.string.men_section_offer))) {
            product = ProductManager.getInstance().getSectionWiseProduct(menBeaconId);
            offer = ProductManager.getInstance().getSectionWiseOffer(product.getOfferId());

            Picasso.with(context).load(product.getImageUrl()).into(offerImage);
            productName.setText(product.getName());
            offerDescription.setText(offer.getOfferDescription());
            offerPrice.setText(offer.getOfferHeading());
        }else if (section.equalsIgnoreCase(context.getResources().getString(R.string.general_offer))) {
            product = ProductManager.getInstance().getSectionWiseProduct(kidsBeaconId);
            offer = ProductManager.getInstance().getSectionWiseOffer(product.getOfferId());

            Picasso.with(context).load(product.getImageUrl()).into(offerImage);
            productName.setText(product.getName());
            offerDescription.setText(offer.getOfferDescription());
            offerPrice.setText(offer.getOfferHeading());
        }

        final Button cancelButton = (Button)dialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntryCheck.getInstance().setAnyPopUpShowing(false);
                dialog.dismiss();
            }
        });

        final Button seeButton = (Button)dialog.findViewById(R.id.see_button);
        final Product finalProduct = product;
        seeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.EXTRA_IMAGE, finalProduct.getImageUrl());
                intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT, finalProduct);
                context.startActivity(intent);
                UserEntryCheck.getInstance().setAnyPopUpShowing(false);
                dialog.dismiss();
            }
        });
    }

}