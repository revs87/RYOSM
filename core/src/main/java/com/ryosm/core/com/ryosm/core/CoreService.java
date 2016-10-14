package com.ryosm.core.com.ryosm.core;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by revs on 10/09/2016.
 */
public class CoreService extends Service {


    private Core core = null;
    private NotificationManager notificationManager;
    private ConnectivityManager connectivityManager;
    private SocketManager socketManager;
    private boolean foregroundModeEnabled;
    private String androidId;

    @Override
    public void onCreate() {

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        socketManager = new SocketManager();

        androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        core = new Core(this);
    }

    @Override
    public void onDestroy() {
        core = null;
        super.onDestroy();
    }

    private void showNotification() {

    }

    /**
     * Gets
     * */
    public Core getCore() {
        return core;
    }

    public String getAndroidId() {
        return androidId;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public SocketManager getSocketManager() {
        return socketManager;
    }

    /**
     * Binder
     * */
    private final IBinder binder = new CoreServiceBinder();

    public IBinder getBinder() {
        return binder;
    }

    public class CoreServiceBinder extends Binder {
        public CoreService getService() {
            return CoreService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
