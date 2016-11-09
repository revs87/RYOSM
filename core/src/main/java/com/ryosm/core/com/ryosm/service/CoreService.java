package com.ryosm.core.com.ryosm.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.f.security.cryptography.SHA1;
import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.core.SocketManager;

/**
 * Created by revs on 10/09/2016.
 */
public abstract class CoreService extends Service {

    private static final int NOTIFICATION_ID = 12341234;
    private Context context;
    private Core core = null;
    private NotificationManager notificationManager;
    private ConnectivityManager connectivityManager;
    private SocketManager socketManager;
    private boolean foregroundModeEnabled;
    private String androidId;
    private String serial;
    private Notification.Builder m_notificationBuilder;


    private Class<?> launcherActivityClass;
    private Class<?> homeActivityClass;
    private Class<?> serviceClass;
    private String imei;

    public CoreService(final IEnvironmentVariables environmentVariables, final boolean debug, final Class<?> launcherActivityClass, final Class<?> homeActivityClass, final Class<?> serviceClass) {
        this(environmentVariables, debug ? Environment.DEVELOPMENT : Environment.PRODUCTION, launcherActivityClass, homeActivityClass, serviceClass);
    }

    public CoreService(final IEnvironmentVariables environmentVariables, final Environment environment, final Class<?> launcherActivityClass, final Class<?> homeActivityClass, final Class<?> serviceClass) {
        super();
        this.environmentVariables = environmentVariables;
        this.environment = environment;
        this.launcherActivityClass = launcherActivityClass;
        this.homeActivityClass = homeActivityClass;
        this.serviceClass = serviceClass;

    }

    public CoreService() {
        super();
    }

    public CoreService(Class<?> homeActivityClass) {
        this.homeActivityClass = homeActivityClass;
    }

    public CoreService(Context context) {
        this.context = context;

        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        socketManager = new SocketManager();


        core = new Core(this, environmentVariables, environment);
    }

    @Override
    public void onCreate() {

        this.context = getApplicationContext();
        Preferences.setAppContext(context);

        androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        serial = android.os.Build.SERIAL;
        imei = SHA1.hash(androidId + serial);

        core = new Core(this, environmentVariables, environment);

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

    public Class<?> getLauncherActivityClass() {
        return launcherActivityClass;
    }

    public Class<?> getHomeActivityClass() {
        return homeActivityClass;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }


    protected IEnvironmentVariables environmentVariables;
    private Environment environment;

    public Preferences getPreferences() {
        return environmentVariables.getPreferences(environment, getBaseContext());
    }

    public String getImei() {
        return imei;
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


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

}
