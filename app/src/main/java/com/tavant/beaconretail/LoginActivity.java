package com.tavant.beaconretail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tavant.beaconretail.proximity.ProximityMarketing;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ProximityMarketing app = (ProximityMarketing)getApplication();
//        app.startProximityMarketing();
        setContentView(R.layout.activity_login);
        Button loginButton = (Button) this.findViewById(android.R.id.content).findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }


}
