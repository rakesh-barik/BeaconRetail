package com.tavant.beaconretail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tavant.beaconretail.model.DrawerMenuItem;
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.net.OfferJsonParser;
import com.tavant.beaconretail.net.ProductJsonParser;
import com.tavant.beaconretail.net.SectionJsonParser;
import com.tavant.beaconretail.net.VolleySingleton;
import com.tavant.beaconretail.proximity.ProximityMarketing;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class LandingActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    //private ProductListAdapter mProductListAdapter;
    private ListView mDrawerMenuList;
    private List<DrawerMenuItem> drawerMenuItemList;

    public static final String[] titles = new String[]{"user_credentials","Products", "Offers", "Cart", "Map", "Logout"};
    public static final int[] images = new int[]{R.drawable.profile_icon,R.drawable.productlist, R.drawable.offer, R.drawable.cart, R.drawable.map, R.drawable.logout};
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateNetworkOperations();
        setActionBarIcon(R.drawable.ic_ab_drawer);
        toggleToolbarToDrawer();
        String componentIdentifier = getIntent().getStringExtra("Section");
        //Finally has to handle multiple beacon ids and has to open respective components
        if(componentIdentifier != null && componentIdentifier.equalsIgnoreCase("cart")){
            loadFragment(new CartFragment(),componentIdentifier);
        }
        else if (componentIdentifier != null) {
            loadFragment(new OffersFragment(), componentIdentifier);
        }
        else {
            loadFragment(new ProductFragment(), null);
        }
        initializeMenu();

    }

    private void initiateNetworkOperations() {
        if(ProductManager.getInstance().getProducts() == null) {
            getSectionsFromServer();
            getProductsFromServer();
            getOffersFromServer();
        }
    }

    private void getSectionsFromServer(){
        JsonArrayRequest request = new JsonArrayRequest(getResources().getString(R.string.sections_url), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    VolleyLog.v("Response :%n %s", response.toString());
                    new SectionJsonParser(response);
                    ProximityMarketing app = (ProximityMarketing) getApplication();
                    app.startProximityMarketing();
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
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void getProductsFromServer() {
        JsonArrayRequest request = new JsonArrayRequest(getResources().getString(R.string.products_url), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    VolleyLog.v("Response :%n %s", response.toString());
                    new ProductJsonParser(response);
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
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void getOffersFromServer() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.offer_url), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                new OfferJsonParser(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void initializeMenu() {
        drawerMenuItemList = new ArrayList<DrawerMenuItem>();
        for (int i = 0; i < titles.length; i++) {
            DrawerMenuItem item = new DrawerMenuItem(images[i], titles[i]);
            drawerMenuItemList.add(item);
        }

        mDrawerMenuList = (ListView) findViewById(R.id.menu_list);
        MenuListAdapter menuListAdapter = new MenuListAdapter(this, drawerMenuItemList);
        mDrawerMenuList.setAdapter(menuListAdapter);
        mDrawerMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    loadSelectedScreen(position);
                }
            }
        });
    }

    private void loadSelectedScreen(int position) {
        switch (position) {
            case 1:
                loadFragment(new ProductFragment(), null);
                drawerLayout.closeDrawers();
                break;
            case 2:
                loadFragment(new OffersFragment(), null);
                drawerLayout.closeDrawers();
                break;
            case 3:
                loadFragment(new CartFragment(), null);
                drawerLayout.closeDrawers();
                break;
            case 4:
                loadFragment(new MapFragment(),null);
                drawerLayout.closeDrawers();
                break;
            case 5:
                logoutAlert();
                drawerLayout.closeDrawers();
                break;

            default:
                break;
        }
    }

    private void logoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LandingActivity.this);

        alertDialog.setTitle("Logout"); // Sets title for your alertbox

        alertDialog.setMessage("Are you sure you want to Logout ?"); // Message to be displayed on alertbox

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(LandingActivity.this, "Successfully Logged Out", Toast.LENGTH_LONG).show();
            }
        });

/* When negative (No/cancel) button is clicked*/
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    private void toggleToolbarToDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    protected int getLayoutResource() {
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
            case R.id.action_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.action_cart:
                loadFragment(new CartFragment(),"cart");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String componentIdentifier = intent.getStringExtra("Section");
        if(componentIdentifier.equalsIgnoreCase("cart")){
            loadFragment(new CartFragment(),componentIdentifier);
        }
        else if (componentIdentifier != null) {
            loadFragment(new OffersFragment(), componentIdentifier);
        }
        else {
            loadFragment(new ProductFragment(), null);
        }
    }

    public void loadFragment(Fragment fragment, String componentIdentifier) {
        if (componentIdentifier != null) {
            Bundle args = new Bundle();
            args.putString("identifier", componentIdentifier);
            fragment.setArguments(args);
        }
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
