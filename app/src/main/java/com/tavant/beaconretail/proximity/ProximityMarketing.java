package com.tavant.beaconretail.proximity;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.BeaconManager.MonitoringListener;
import com.tavant.beaconretail.LandingActivity;
import com.tavant.beaconretail.R;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;

public class ProximityMarketing extends Application{

	private BeaconManager beaconManager;
	private NotificationManager notificationManager;
	private Region entryRegion,menSectionRegion,womenSectionRegion;
	private boolean isProxyShoppingRunning = false;
	
	private static final int ENTRY_NOTIFICATION_ID          = 123;
    private static final int MEN_NOTIFICATION_ID            = 456;
    private static final int WOMEN_NOTIFICATION_ID          = 789;
    private static final int EXIT_NOTIFICATION_ID           = 012;
	private static final String TAG = "ProximityMarketing";
	
	public void startProximityMarketing(){
		if(isProxyShoppingRunning)
			return;
		
		beaconManager = new BeaconManager(this);
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);

	    beaconManager.setMonitoringListener(new MonitoringListener() {
	      @Override
	      public void onEnteredRegion(Region region, List<Beacon> beacons) {
              for(Beacon beacon : beacons){
                  if (beacon.getMinor() == getResources().getInteger(R.integer.entry_minor)){
                      postNotification(getString(R.string.welcome_msg),ENTRY_NOTIFICATION_ID);
                  }else if(beacon.getMinor() == getResources().getInteger(R.integer.men_minor)){
                      postNotification(getString(R.string.men_section_msg),MEN_NOTIFICATION_ID);
                  }else if(beacon.getMinor() == getResources().getInteger(R.integer.women_minor)){
                      postNotification(getString(R.string.women_section_msg), WOMEN_NOTIFICATION_ID);
                  }
              }

	      }

	      @Override
	      public void onExitedRegion(Region region) {
            if(region.getMinor() == getResources().getInteger(R.integer.entry_minor))
	        postNotification(getString(R.string.exit_msg),EXIT_NOTIFICATION_ID);
	      }
	    });
		
	    entryRegion         = new Region(getString(R.string.entry_beacon),
                                        getString(R.string.beacon_uuid),
                                        getResources().getInteger(R.integer.entry_major),
                                        getResources().getInteger(R.integer.entry_minor));

	    menSectionRegion    = new Region(getString(R.string.men_section_beacon),
                                        getString(R.string.beacon_uuid),
                                        getResources().getInteger(R.integer.men_major),
                                        getResources().getInteger(R.integer.men_minor));

        womenSectionRegion  = new Region(getString(R.string.women_section_beacon),
                                        getString(R.string.beacon_uuid),
                                        getResources().getInteger(R.integer.women_major),
                                        getResources().getInteger(R.integer.women_minor));

	    //notificationManager.cancel(NOTIFICATION_ID);
	    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	      @Override
	      public void onServiceReady() {
	        try {
	          beaconManager.startMonitoring(entryRegion);
              beaconManager.startMonitoring(menSectionRegion);
              beaconManager.startMonitoring(womenSectionRegion);
	        } catch (RemoteException e) {
	          Log.d(TAG, "Error while starting monitoring");
	        }
	      }
	    });
	    
	    isProxyShoppingRunning = true;
		
	}
	
	public BeaconManager getBeaconManager(){
		return beaconManager;
	}
	
	private void postNotification(String msg,int notificationId) {
		    Intent notifyIntent = new Intent(ProximityMarketing.this, LandingActivity.class);
		    notifyIntent.putExtra("Offer", msg);
		    notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Random random = new Random();
		    PendingIntent pendingIntent = PendingIntent.getActivities(
		    		ProximityMarketing.this,
		        0,
		        new Intent[]{notifyIntent},
		        PendingIntent.FLAG_ONE_SHOT);
		    Notification notification = new Notification.Builder(ProximityMarketing.this)
		        .setSmallIcon(R.drawable.beacon_gray)
		        .setContentTitle("Tavant Retail")
		        .setContentText(msg)
		        .setAutoCancel(true)
		        .setContentIntent(pendingIntent)
		        .build();
		    notification.defaults |= Notification.DEFAULT_SOUND;
		    notification.defaults |= Notification.DEFAULT_LIGHTS;
		    notificationManager.notify(notificationId, notification);
	}
}
