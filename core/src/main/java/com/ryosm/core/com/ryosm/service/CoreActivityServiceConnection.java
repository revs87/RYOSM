package com.ryosm.core.com.ryosm.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.ryosm.core.com.ryosm.utils.L;


public class CoreActivityServiceConnection implements ServiceConnection {

    private final String LOG_TAG = "CoreActivityServiceConnection";

    private final IServiceBindHandler bindHandler;
    private CoreService service = null;

    public CoreActivityServiceConnection(final IServiceBindHandler bindHandler) {
        this.bindHandler = bindHandler;
    }

    public void onServiceConnected(ComponentName className, IBinder binder) {
        service = ((CoreService.CoreServiceBinder) binder).getService();
        bindHandler.setService(service);
        L.i(LOG_TAG, "Connected to Service");
        bindHandler.serviceBound();
    }

    public void onServiceDisconnected(ComponentName className) {
        bindHandler.serviceUnbound(service);
        bindHandler.setService(null);
        L.i(LOG_TAG, "Disconnected from Service");
    }
}
