package com.tavant.beaconretail.proximity;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.tavant.beaconretail.BaseActivity;
import com.tavant.beaconretail.LandingActivity;
import com.tavant.beaconretail.R;
import com.tavant.beaconretail.model.ProductManager;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;

public class ProximityMarketing extends Application {

    private static final double NEAR_DISTANCE = 0.5;
    private BeaconManager beaconManager;
    private NotificationManager notificationManager;
    private Region mainEntranceRegion, entryRegion, menSectionRegion, womenSectionRegion;
    private boolean isProxyShoppingRunning = false;

    private static final int ENTRY_NOTIFICATION_ID = 123;
    private static final int MEN_NOTIFICATION_ID = 456;
    private static final int WOMEN_NOTIFICATION_ID = 789;
    private static final int EXIT_NOTIFICATION_ID = 012;
    private static final String TAG = "ProximityMarketing";
    private ForeGroundCheck mFGCheck = null;


    @Override
    public void onCreate() {
        mFGCheck = new ForeGroundCheck();
        registerActivityLifecycleCallbacks(mFGCheck);

    }


    public void startProximityMarketing() {

        if (isProxyShoppingRunning)
            return;

        beaconManager = new BeaconManager(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        beaconManager.setBackgroundScanPeriod(TimeUnit.SECONDS.toMillis(1), 0);

        final UserEntryCheck userEntryCheck = UserEntryCheck.getInstance();

        /*beaconManager.setMonitoringListener(new MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> beacons) {
                for (Beacon beacon : beacons) {
                    if (beacon.getMinor() == getResources().getInteger(R.integer.main_entrance_minor)) {
                        if(userEntryCheck.isInsidePremise() && userEntryCheck.isAtEntry()){
                            if(mFGCheck.isApplicationInForeground() && !userEntryCheck.isAnyPopUpShowing()){
                                if(ProductManager.getInstance().getCartProducts() != null){
                                    BaseActivity.showPopUp(getResources().getString(R.string.check_out),mFGCheck.getDisplayContext());
                                    userEntryCheck.setAnyPopUpShowing(true);
                                }else {
                                    BaseActivity.showPopUp(getResources().getString(R.string.general_offer),mFGCheck.getDisplayContext());
                                    userEntryCheck.setAnyPopUpShowing(true);
                                }
                            }else{
                                if(ProductManager.getInstance().getCartProducts() != null){
                                    postNotification(getString(R.string.exit_msg_with_cart), EXIT_NOTIFICATION_ID, null);
                                }else {
                                    postNotification(getString(R.string.exit_msg), EXIT_NOTIFICATION_ID, null);
                                }
                            }
                        }
                        userEntryCheck.setInsidePremise(true);
                        userEntryCheck.setAtEntry(false);
                        userEntryCheck.setAtMenSection(false);
                        userEntryCheck.setAtWomenSection(false);
                    } else if (beacon.getMinor() == getResources().getInteger(R.integer.entry_minor)) {
                        if (userEntryCheck.isEntryOfferShown() && userEntryCheck.isInsidePremise() && !userEntryCheck.isAtEntry()) {
                            if(mFGCheck.isApplicationInForeground() && !userEntryCheck.isAnyPopUpShowing()){
                                BaseActivity.showPopUp(getResources().getString(R.string.general_offer),mFGCheck.getDisplayContext());
                                userEntryCheck.setAnyPopUpShowing(true);
                            }else{
                                postNotification(getString(R.string.welcome_msg), ENTRY_NOTIFICATION_ID,getResources().getString(R.string.general_offer) );
                            }
                            userEntryCheck.setEntryOfferShown(false);
                            userEntryCheck.setAtEntry(true);
                        }
                    } else if (beacon.getMinor() == getResources().getInteger(R.integer.men_minor)) {
                        if (userEntryCheck.isMenSectionOfferShown() && userEntryCheck.isAtEntry()) {
                            if(mFGCheck.isApplicationInForeground() && !userEntryCheck.isAnyPopUpShowing()){
                                BaseActivity.showPopUp(getResources().getString(R.string.men_section_offer),mFGCheck.getDisplayContext());
                                userEntryCheck.setAnyPopUpShowing(true);
                            }else{
                                postNotification(getString(R.string.men_section_msg), MEN_NOTIFICATION_ID,getResources().getString(R.string.men_section_offer));
                            }
                            userEntryCheck.setMenSectionOfferShown(false);
                            userEntryCheck.setAtMenSection(true);
                        }
                    } else if (beacon.getMinor() == getResources().getInteger(R.integer.women_minor)) {
                        if (userEntryCheck.isWomenSectionOfferShown() && userEntryCheck.isAtEntry()) {
                            if(mFGCheck.isApplicationInForeground() && !userEntryCheck.isAnyPopUpShowing()){
                                BaseActivity.showPopUp(getResources().getString(R.string.women_section_offer),mFGCheck.getDisplayContext());
                                userEntryCheck.setAnyPopUpShowing(true);
                            }else{
                                postNotification(getString(R.string.women_section_msg), WOMEN_NOTIFICATION_ID,getResources().getString(R.string.women_section_offer));
                            }
                            userEntryCheck.setWomenSectionOfferShown(false);
                            userEntryCheck.setAtWomenSection(true);
                        }
                    }
                }

            }

            @Override
            public void onExitedRegion(Region region) {

            }
        });*/

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                for (Beacon beacon : beacons) {
                    if (beacon.getMinor() == getResources().getInteger(R.integer.main_entrance_minor) && Utils.computeAccuracy(beacon) < NEAR_DISTANCE) {
                        if (mFGCheck.isApplicationInForeground() ) {
                            if (ProductManager.getInstance().getCartProducts() != null && !userEntryCheck.isCheckOutPopUpShowing()) {
                                BaseActivity.showPopUp(getResources().getString(R.string.check_out), mFGCheck.getDisplayContext());
                                userEntryCheck.setCheckOutPopUpShowing(true);
                            } else if(!userEntryCheck.isAnyPopUpShowing() && !userEntryCheck.isEntryOfferShown()){
                                /*BaseActivity.showPopUp(getResources().getString(R.string.general_offer), mFGCheck.getDisplayContext());
                                userEntryCheck.setAnyPopUpShowing(true);*/
                            }

                        } else if (!mFGCheck.isApplicationInForeground()) {
                            if (ProductManager.getInstance().getCartProducts() != null) {
                                postNotification(getString(R.string.exit_msg_with_cart), EXIT_NOTIFICATION_ID, null);
                            } else {
                                postNotification(getString(R.string.exit_msg), EXIT_NOTIFICATION_ID, null);
                            }
                        }
                    } else if (beacon.getMinor() == getResources().getInteger(R.integer.entry_minor) && Utils.computeAccuracy(beacon) < NEAR_DISTANCE) {
                        if (mFGCheck.isApplicationInForeground() && !userEntryCheck.isAnyPopUpShowing() && !userEntryCheck.isEntryOfferShown() ) {
                            BaseActivity.showPopUp(getResources().getString(R.string.general_offer), mFGCheck.getDisplayContext());
                            userEntryCheck.setAnyPopUpShowing(true);
                            userEntryCheck.setEntryOfferShown(true);
                        } else if (!mFGCheck.isApplicationInForeground()) {
                            postNotification(getString(R.string.welcome_msg), ENTRY_NOTIFICATION_ID, getResources().getString(R.string.general_offer));
                        }
                    } else if (beacon.getMinor() == getResources().getInteger(R.integer.men_minor) && Utils.computeAccuracy(beacon) < NEAR_DISTANCE) {
                        if (mFGCheck.isApplicationInForeground() && !userEntryCheck.isAnyPopUpShowing() && !userEntryCheck.isMenSectionOfferShown()) {
                            BaseActivity.showPopUp(getResources().getString(R.string.men_section_offer), mFGCheck.getDisplayContext());
                            userEntryCheck.setAnyPopUpShowing(true);
                            userEntryCheck.setMenSectionOfferShown(true);
                        } else if (!mFGCheck.isApplicationInForeground()) {
                            postNotification(getString(R.string.men_section_msg), MEN_NOTIFICATION_ID, getResources().getString(R.string.men_section_offer));
                        }
                    } else if (beacon.getMinor() == getResources().getInteger(R.integer.women_minor) && Utils.computeAccuracy(beacon) < NEAR_DISTANCE) {
                        if (mFGCheck.isApplicationInForeground() && !userEntryCheck.isAnyPopUpShowing() && !userEntryCheck.isWomenSectionOfferShown()) {
                            BaseActivity.showPopUp(getResources().getString(R.string.women_section_offer), mFGCheck.getDisplayContext());
                            userEntryCheck.setAnyPopUpShowing(true);
                            userEntryCheck.setWomenSectionOfferShown(true);
                        } else if (!mFGCheck.isApplicationInForeground()) {
                            postNotification(getString(R.string.women_section_msg), WOMEN_NOTIFICATION_ID, getResources().getString(R.string.women_section_offer));
                        }
                    }
                }

            }
        });

        mainEntranceRegion = new Region(getString(R.string.main_entrance_beacon),
                getString(R.string.beacon_uuid),
                getResources().getInteger(R.integer.main_entrance_major),
                getResources().getInteger(R.integer.main_entrance_minor));

        entryRegion = new Region(getString(R.string.entry_beacon),
                getString(R.string.beacon_uuid),
                getResources().getInteger(R.integer.entry_major),
                getResources().getInteger(R.integer.entry_minor));

        menSectionRegion = new Region(getString(R.string.men_section_beacon),
                getString(R.string.beacon_uuid),
                getResources().getInteger(R.integer.men_major),
                getResources().getInteger(R.integer.men_minor));

        womenSectionRegion = new Region(getString(R.string.women_section_beacon),
                getString(R.string.beacon_uuid),
                getResources().getInteger(R.integer.women_major),
                getResources().getInteger(R.integer.women_minor));

        //notificationManager.cancel(NOTIFICATION_ID);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    /*beaconManager.startMonitoring(mainEntranceRegion);
                    beaconManager.startMonitoring(entryRegion);
                    beaconManager.startMonitoring(menSectionRegion);
                    beaconManager.startMonitoring(womenSectionRegion);*/

                    beaconManager.startRanging(mainEntranceRegion);
                    beaconManager.startRanging(entryRegion);
                    beaconManager.startRanging(menSectionRegion);
                    beaconManager.startRanging(womenSectionRegion);
                } catch (RemoteException e) {
                    Log.d(TAG, "Error while starting ranging");
                }
            }
        });

        isProxyShoppingRunning = true;

    }


    public BeaconManager getBeaconManager() {
        return beaconManager;
    }

    private void postNotification(String msg, int notificationId, String section) {
        Intent notifyIntent = new Intent(ProximityMarketing.this, LandingActivity.class);
        notifyIntent.putExtra("Section", section);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Random random = new Random();
        PendingIntent pendingIntent = PendingIntent.getActivities(
                ProximityMarketing.this,
                random.nextInt(),
                new Intent[]{notifyIntent},
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.shopping);
        Notification notification = new Notification.Builder(ProximityMarketing.this)
                .setSmallIcon(R.drawable.retail_notif)
                .setStyle(new Notification.BigPictureStyle()
                        .bigPicture(icon))
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
