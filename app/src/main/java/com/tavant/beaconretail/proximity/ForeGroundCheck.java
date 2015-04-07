package com.tavant.beaconretail.proximity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by rakesh.barik on 06-04-2015.
 */
public class ForeGroundCheck implements Application.ActivityLifecycleCallbacks {

    private static int resumed;
    private static int stopped;

    // And add this public static function
    public static boolean isApplicationInForeground() {
        return resumed > stopped;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
