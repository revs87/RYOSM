package com.ryosm.core.com.ryosm.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by revs on 09-11-2016.
 */

public abstract class ServiceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.i("SERVICE_BROADCAST", "intent == null");
        } else {
            Log.i("SERVICE_BROADCAST", "Intent: " + intent + " Action: " + intent.getAction());
        }
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("start_service_on_boot", true)) {
            Intent serviceIntent = new Intent(context, getServiceClass());
            context.startService(serviceIntent);
        }
    }

    protected abstract Class getServiceClass();


}