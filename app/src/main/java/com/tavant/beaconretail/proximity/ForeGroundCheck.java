package com.tavant.beaconretail.proximity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by rakesh.barik on 06-04-2015.
 */
public class ForeGroundCheck implements Application.ActivityLifecycleCallbacks {

    private static int resumed;
    private static int stopped;
    private Activity displayContext = null;

    // And add this public static function
    public boolean isApplicationInForeground() {
        return resumed > stopped;
    }

    public Context getDisplayContext(){
        return displayContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //displayContext = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        //displayContext = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        displayContext = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //displayContext = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        //displayContext = activity;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //displayContext = activity;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //displayContext = activity;
    }
}
