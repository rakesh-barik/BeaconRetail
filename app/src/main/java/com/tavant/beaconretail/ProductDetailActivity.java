package com.tavant.beaconretail;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String ARG_ITEM = "ARG_ITEM";
    public static final String PACKAGE = "com.tavant.beaconretail";

    public static final String EXTRA_IMAGE = "DetailActivity:image";
    private ImageView mImageView;
    private TextView mTextView;
    private Button sizeSix,sizeSeven,sizeEight,sizeNine,sizeTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
        mImageView = (ImageView)findViewById(R.id.productImage);
        mTextView = (TextView)findViewById(R.id.text);

        ViewCompat.setTransitionName(mImageView, EXTRA_IMAGE);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(mImageView);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUpToFromChild(ProductDetailActivity.this, IntentCompat.makeMainActivity(new ComponentName(ProductDetailActivity.this, LandingActivity.class)));
            }
        });

        initializeButtons();
        fabButtonAction();
    }

    private void fabButtonAction() {
        findViewById(R.id.cart_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetailActivity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeButtons() {
        sizeSix     = (Button)findViewById(R.id.size_six);
        sizeSeven   = (Button)findViewById(R.id.size_seven);
        sizeEight   = (Button)findViewById(R.id.size_eight);
        sizeNine    = (Button)findViewById(R.id.size_nine);
        sizeTen     = (Button)findViewById(R.id.size_ten);

        sizeSix.setOnClickListener(this);
        sizeSeven.setOnClickListener(this);
        sizeEight.setOnClickListener(this);
        sizeNine.setOnClickListener(this);
        sizeTen.setOnClickListener(this);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_product_detail;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public static void launch(ProductFragment activity, View transitionView, String url) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity.getActivity(), transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(activity.getActivity(), ProductDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity.getActivity(), intent, options.toBundle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.size_six:
                v.setBackgroundColor(getResources().getColor(R.color.material_blue_A200));
                sizeSeven.setBackgroundColor(getResources().getColor(R.color.white));
                sizeEight.setBackgroundColor(getResources().getColor(R.color.white));
                sizeNine.setBackgroundColor(getResources().getColor(R.color.white));
                sizeTen.setBackgroundColor(getResources().getColor(R.color.white));

                break;
            case R.id.size_seven:
                v.setBackgroundColor(getResources().getColor(R.color.material_blue_A200));
                sizeSix.setBackgroundColor(getResources().getColor(R.color.white));
                sizeEight.setBackgroundColor(getResources().getColor(R.color.white));
                sizeNine.setBackgroundColor(getResources().getColor(R.color.white));
                sizeTen.setBackgroundColor(getResources().getColor(R.color.white));

                break;
            case R.id.size_eight:
                v.setBackgroundColor(getResources().getColor(R.color.material_blue_A200));
                sizeSix.setBackgroundColor(getResources().getColor(R.color.white));
                sizeSeven.setBackgroundColor(getResources().getColor(R.color.white));
                sizeNine.setBackgroundColor(getResources().getColor(R.color.white));
                sizeTen.setBackgroundColor(getResources().getColor(R.color.white));

                break;
            case R.id.size_nine:
                v.setBackgroundColor(getResources().getColor(R.color.material_blue_A200));
                sizeSix.setBackgroundColor(getResources().getColor(R.color.white));
                sizeSeven.setBackgroundColor(getResources().getColor(R.color.white));
                sizeEight.setBackgroundColor(getResources().getColor(R.color.white));
                sizeTen.setBackgroundColor(getResources().getColor(R.color.white));

                break;
            case R.id.size_ten:
                v.setBackgroundColor(getResources().getColor(R.color.material_blue_A200));
                sizeSix.setBackgroundColor(getResources().getColor(R.color.white));
                sizeSeven.setBackgroundColor(getResources().getColor(R.color.white));
                sizeEight.setBackgroundColor(getResources().getColor(R.color.white));
                sizeNine.setBackgroundColor(getResources().getColor(R.color.white));

                break;

        }
    }
}
