package com.ryosm;

import android.app.Application;
import android.content.Context;

import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.service.Environment;
import com.ryosm.core.com.ryosm.service.IEnvironmentVariables;
import com.ryosm.core.com.ryosm.service.Preferences;

/**
 * Created by revs on 09-11-2016.
 */

public class EnvironmentsConfig extends Application implements IEnvironmentVariables {

    private Context context;

    public EnvironmentsConfig() {
    }


    @Override
    public String getApiEndPoint(Environment env) {
        if (env == Environment.DEVELOPMENT) {
            return CommunicationCenter.TEST_SERVER_URL;
        } else if (env == Environment.PRODUCTION) {
            return CommunicationCenter.PROD_SERVER_URL;
        }
        return null;
    }

    public Preferences getPreferences(Environment environment, Context context) {
        return new RyoPreferences(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
