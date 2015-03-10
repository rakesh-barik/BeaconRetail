package com.tavant.beaconretail;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.tavant.beaconretail.model.DrawerMenuItem;
import com.tavant.beaconretail.model.ProductManager;

import java.util.ArrayList;
import java.util.List;


public class LandingActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mProductListAdapter;
    private ListView mDrawerMenuList;

    private List<DrawerMenuItem> drawerMenuItemList;

    public static final String[] titles = new String[]{"Products","Offers","Cart","Map"};
    public static final int[] images = new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_ab_drawer);
        toggleToolbarToDrawer();

        mRecyclerView = (RecyclerView)findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductListAdapter = new ProductListAdapter(ProductManager.getInstance().getProducts(),R.layout.product_row,this);
        mRecyclerView.setAdapter(mProductListAdapter);

        drawerMenuItemList = new ArrayList<DrawerMenuItem>();
        for(int i = 0; i < titles.length; i++){
            DrawerMenuItem item = new DrawerMenuItem(images[i],titles[i]);
            drawerMenuItemList.add(item);
        }

        mDrawerMenuList = (ListView)findViewById(R.id.menu_list);
        MenuListAdapter menuListAdapter = new MenuListAdapter(this, drawerMenuItemList);
        mDrawerMenuList.setAdapter(menuListAdapter);

    }

    private void toggleToolbarToDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ){
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override protected int getLayoutResource() {
        return R.layout.activity_landing;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
