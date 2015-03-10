package com.tavant.beaconretail.proximity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.BeaconManager.MonitoringListener;
import com.tavant.beaconretail.OffersActivity;
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
	private Region region;
	private boolean isProxyShoppingRunning = false;
	
	private static final int NOTIFICATION_ID = 123;
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
	        postNotification("Welcome to Proxy Store");
	      }

	      @Override
	      public void onExitedRegion(Region region) {
	        postNotification("Exited region");
	      }
	    });
		
	    region = new Region("regionId", "b9407f30-f5f8-466e-aff9-25556b57fe6d", 64990, 59688);
	    
	    notificationManager.cancel(NOTIFICATION_ID);
	    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	      @Override
	      public void onServiceReady() {
	        try {
	          beaconManager.startMonitoring(region);
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
	
	private void postNotification(String msg) {
		    Intent notifyIntent = new Intent(ProximityMarketing.this, OffersActivity.class);
		    notifyIntent.putExtra("Offer", msg);
		    notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		    PendingIntent pendingIntent = PendingIntent.getActivities(
		    		ProximityMarketing.this,
		        0,
		        new Intent[]{notifyIntent},
		        PendingIntent.FLAG_UPDATE_CURRENT);
		    Notification notification = new Notification.Builder(ProximityMarketing.this)
		        .setSmallIcon(R.drawable.beacon_gray)
		        .setContentTitle("Notify Demo")
		        .setContentText(msg)
		        .setAutoCancel(true)
		        .setContentIntent(pendingIntent)
		        .build();
		    notification.defaults |= Notification.DEFAULT_SOUND;
		    notification.defaults |= Notification.DEFAULT_LIGHTS;
		    notificationManager.notify(NOTIFICATION_ID, notification);
	}
}
