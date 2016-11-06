package com.ryosm.core.com.ryosm.core;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by revs on 10/09/2016.
 */
public class CoreService extends Service {


    private static final int NOTIFICATION_ID = 12341234;
    private Context context;
    private Core core = null;
    private NotificationManager notificationManager;
    private ConnectivityManager connectivityManager;
    private SocketManager socketManager;
    private boolean foregroundModeEnabled;
    private String androidId;
    private Notification.Builder m_notificationBuilder;

    public CoreService() {
    }

    public CoreService(Context context) {
        this.context = context;

        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        socketManager = new SocketManager();

        androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        core = new Core();
    }

    private void showNotification() {

    }

    /**
     * Gets Sets
     */
    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
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
     */
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


    /**
     *
     * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private CoreService m_service;

    private ServiceConnection m_serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            m_service = ((CoreService.CoreServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            m_service = null;
        }
    };

    public ServiceConnection getM_serviceConnection() {
        return m_serviceConnection;
    }

//    private void addNotification(Activity activity) {
//        // create the notification
//        m_notificationBuilder = new Notification.Builder(activity)
//                .setContentTitle("Service name")
//                .setContentText("service_status_monitor")
//                .setSmallIcon(android.R.drawable.ic_dialog_alert);
//
//        // create the pending intent and add to the notification
//        Intent intent = new Intent(activity, CoreService.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
//        m_notificationBuilder.setContentIntent(pendingIntent);
//
//        // send the notification
//        notificationManager.notify(NOTIFICATION_ID, m_notificationBuilder.build());
//    }
//
//    public void addNotificationToForeground(Activity activity) {
//        addNotification(activity);
//        startForeground(NOTIFICATION_ID, m_notificationBuilder.build());
//    }

}
