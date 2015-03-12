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
                  if (beacon.getMinor() == 59688){
                      //entry beacon detected
                      postNotification("Welcome to Tavant Retail Store",ENTRY_NOTIFICATION_ID);
                  }else if(beacon.getMinor() == 39431){
                      //men section beacon detected
                      postNotification("We have exciting offers for you in Men section",MEN_NOTIFICATION_ID);
                  }else if(beacon.getMinor() == 41861){
                      //women section beacon detected
                      postNotification("We have exciting offers for you in Women section", WOMEN_NOTIFICATION_ID);
                  }
              }

	      }

	      @Override
	      public void onExitedRegion(Region region) {
            if(region.getMinor() == 59688)
	        postNotification("Thanks for visiting Tavant Retail Store",EXIT_NOTIFICATION_ID);
	      }
	    });
		
	    entryRegion         = new Region("entryId", "b9407f30-f5f8-466e-aff9-25556b57fe6d", 64990, 59688);
	    menSectionRegion    = new Region("menSectionBeacon", "b9407f30-f5f8-466e-aff9-25556b57fe6d", 32673, 39431);
        womenSectionRegion  = new Region("WomenSectionBeacon", "b9407f30-f5f8-466e-aff9-25556b57fe6d", 36164, 41861);

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
		        random.nextInt(),
		        new Intent[]{notifyIntent},
		        PendingIntent.FLAG_UPDATE_CURRENT);
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
