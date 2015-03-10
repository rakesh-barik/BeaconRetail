package com.tavant.beaconretail;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.tavant.beaconretail.proximity.ProximityMarketing;


public class OffersActivity extends Activity {

  private static final String TAG = OffersActivity.class.getSimpleName();
  private static final int NOTIFICATION_ID = 123;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_offer);
    
    ProximityMarketing app = (ProximityMarketing)getApplication();
    app.startProximityMarketing();
    
    String msg = (String) getIntent().getCharSequenceExtra("Offer");
    TextView statusTextView = (TextView) findViewById(R.id.status);
    statusTextView.setText(msg);

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

}
