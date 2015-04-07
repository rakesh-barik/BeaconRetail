package com.tavant.beaconretail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tavant.beaconretail.proximity.ProximityMarketing;

/**
 * Created by rakesh.barik on 05-03-2015.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected Toolbar toolbar;

    protected static boolean isVisible = false;

    private static Context context;


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

    public static void showPopUp(String section) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.offer_popup);
        ImageView offerImage = (ImageView)dialog.findViewById(R.id.offerImage);
        if(section.equals(context.getResources().getString(R.string.women_section_offer))){
            offerImage.setImageResource(R.drawable.womenoffer);
        }else if (section.equals(context.getResources().getString(R.string.men_section_offer))) {
            offerImage.setImageResource(R.drawable.menoffer);
        }else if (section.equals(context.getResources().getString(R.string.general_offer))) {
            offerImage.setImageResource(R.drawable.offer_for_men);
        }
        dialog.show();
    }
}