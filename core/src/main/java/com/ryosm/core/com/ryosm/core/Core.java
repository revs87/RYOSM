package com.ryosm.core.com.ryosm.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ryosm.core.com.ryosm.base.CoreBaseActivity;
import com.ryosm.core.com.ryosm.comms.libsodium.RyoLibsodium;
import com.ryosm.core.com.ryosm.db.DatabaseClient;
import com.ryosm.core.com.ryosm.service.CoreService;
import com.ryosm.core.com.ryosm.service.Environment;
import com.ryosm.core.com.ryosm.service.IEnvironmentVariables;
import com.ryosm.core.com.ryosm.service.Preferences;

/**
 * Created by revs on 10/09/2016.
 */
public class Core implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final Environment environment;
    private final IEnvironmentVariables environmentVariables;
    private final String imei;
    private String appVersion;
    private CoreService service;
    private RyoLibsodium ryoLibsodium;
    private CoreBaseActivity currentActivity;
    private DatabaseClient databaseClient;

    public Core(CoreService coreService, IEnvironmentVariables environmentVariables, Environment environment) {
        this.environment = environment;
        this.environmentVariables = environmentVariables;
        this.service = coreService;

        imei = service.getImei();

        //Set App Version
        final Context appContext = service.getApplicationContext();
        final String packageName = appContext.getPackageName();
        final PackageManager packageManager = appContext.getPackageManager();

        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            appVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }

        databaseClient = new DatabaseClient(this);

        ryoLibsodium = new RyoLibsodium(this);
    }


    public RyoLibsodium getRyoLibsodium() {
        return ryoLibsodium;
    }

    public void setRyoLibsodium(RyoLibsodium ryoLibsodium) {
        this.ryoLibsodium = ryoLibsodium;
    }

    public void setCurrentActivity(CoreBaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Preferences getPreferences() {
        return service.getPreferences();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    public CoreService getService() {
        return service;
    }

    public DatabaseClient getDatabaseClient() {
        return databaseClient;
    }

    public void setDatabaseClient(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }


}
