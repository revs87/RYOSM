package com.ryosm.core.com.ryosm.base;

import android.content.Context;
import android.os.Bundle;

import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.service.CoreService;

/**
 * Created by revs on 13/10/2016.
 */
public class CoreLauncherActivity extends CoreBaseActivity {

    public CoreLauncherActivity() {
        super(CoreService.class);
    }

    public CoreLauncherActivity(Class<? extends CoreService> serviceClass) {
        super(serviceClass);
    }

    public CoreLauncherActivity(Class<? extends CoreService> serviceClass, Integer layoutId) {
        super(serviceClass, layoutId);
    }

    public static Context getContext() {
        return getCurrentActivity();
    }

    @Override
    public void showExternalStorageErrorDialog(boolean finish) {

    }

    @Override
    protected void onServiceBound(Bundle savedInstanceState, Core core) {
        super.onServiceBound(savedInstanceState, core);
    }

    @Override
    protected void onServiceUnbound() {
    }

    public Core getCore() {
        return getService().getCore();
    }

}
