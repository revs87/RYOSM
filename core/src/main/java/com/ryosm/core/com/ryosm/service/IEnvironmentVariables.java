package com.ryosm.core.com.ryosm.service;

import android.content.Context;

/**
 * Created by revs on 09-11-2016.
 */

public interface IEnvironmentVariables {
    public String getApiEndPoint(Environment env);

    public Preferences getPreferences(Environment environment, Context context);
}
