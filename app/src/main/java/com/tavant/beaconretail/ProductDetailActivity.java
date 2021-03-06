package com.tavant.beaconretail;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tavant.beaconretail.model.Product;
import com.tavant.beaconretail.model.ProductManager;


public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String ARG_ITEM = "ARG_ITEM";
    public static final String PACKAGE = "com.tavant.beaconretail";

    public static final String EXTRA_IMAGE = "DetailActivity:image";
    public static final String EXTRA_PRODUCT = "PRODUCT";
    private ImageView mImageView;
    private TextView  tvProductName,tvProductSection,tvProductPrice,tvProductDesc;
    private Button sizeSix,sizeSeven,sizeEight,sizeNine,sizeTen;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Beacon Retails");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));

        mImageView = (ImageView)findViewById(R.id.productImage);
        tvProductName = (TextView)findViewById(R.id.productName);
        tvProductSection = (TextView)findViewById(R.id.productSection);
        tvProductPrice = (TextView)findViewById(R.id.productPrice);
        tvProductDesc = (TextView)findViewById(R.id.product_desc);

        ViewCompat.setTransitionName(mImageView, EXTRA_IMAGE);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(mImageView);

        product = getIntent().getParcelableExtra(EXTRA_PRODUCT);
        if(null != product) {
            tvProductName.setText(product.getName());
            tvProductSection.setText(product.getSectionName());
            tvProductPrice.setText(product.getPrice());
            tvProductDesc.setText(product.getDescription());
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUpToFromChild(ProductDetailActivity.this, IntentCompat.makeMainActivity(new ComponentName(ProductDetailActivity.this, LandingActivity.class)));
            }
        });

        initializeButtons();
        fabButtonAction();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_product_detail;
    }

    private void fabButtonAction() {
        findViewById(R.id.cart_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductManager.getInstance().addProductToCart(product);
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

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }else if(id == R.id.action_cart){
            Intent i=new Intent(this, LandingActivity.class);
            i.putExtra("Section", "cart");
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public static void launch(Fragment fragment, View transitionView,Product product, String url) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        fragment.getActivity(), transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(fragment.getActivity(), ProductDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
        intent.putExtra(EXTRA_PRODUCT,product);
        ActivityCompat.startActivity(fragment.getActivity(), intent, options.toBundle());
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
