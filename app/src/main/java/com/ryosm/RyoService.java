package com.ryosm;

import com.ryosm.activity.HomeActivity;
import com.ryosm.activity.LauncherActivity;
import com.ryosm.core.BuildConfig;
import com.ryosm.core.com.ryosm.service.CoreService;

/**
 * Created by revs on 09-11-2016.
 */

public class RyoService extends CoreService {

    public RyoService() {
        super(new EnvironmentsConfig(), BuildConfig.DEBUG, LauncherActivity.class, HomeActivity.class, RyoService.class);
        ((EnvironmentsConfig) environmentVariables).setContext(this);
    }
}
