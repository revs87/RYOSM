package com.ryosm.core.com.ryosm.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.core.CoreService;

/**
 * Created by revs on 13/10/2016.
 */
public class CoreLauncherActivity extends AppCompatActivity {

    private static Context context;
    private static CoreBaseActivity currentActivity;
    private static CoreService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContext(this);

        /**
         * CoreService
         * */
        service = new CoreService(this);
        Intent intent = new Intent(this, CoreService.class);
        bindService(intent, service.getM_serviceConnection(), BIND_AUTO_CREATE);


    }

    public static CoreService getService() {
        return service;
    }

    public static void setService(CoreService service) {
        CoreLauncherActivity.service = service;
    }

    public static Core getCore() {
        return getService().getCore();
    }

    public static void setCore(Core core) {
        getService().setCore(core);
    }

    public static CoreBaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(CoreBaseActivity currentActivity) {
        CoreLauncherActivity.currentActivity = currentActivity;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        CoreLauncherActivity.context = context;
    }

}
