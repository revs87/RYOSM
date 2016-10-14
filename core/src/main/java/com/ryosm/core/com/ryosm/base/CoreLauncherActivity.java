package com.ryosm.core.com.ryosm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by revs on 13/10/2016.
 */
public class CoreLauncherActivity extends AppCompatActivity {

    private static Context context;
    private static CoreBaseActivity currentActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContext(this);
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
