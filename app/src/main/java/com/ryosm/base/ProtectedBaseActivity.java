package com.ryosm.base;

import com.ryosm.core.com.ryosm.service.CoreService;

/**
 * Created by revs on 03/03/2016.
 * <p/>
 * Must be used whenever a new private activity is created during workflow.
 * This class instance type grants RestoreForeground Protection in the new private activity.
 * Configs.RESTORE_FOREGROUND_PROTECTION must be enabled.
 */
public class ProtectedBaseActivity extends BaseActivity {
    public ProtectedBaseActivity(Class<? extends CoreService> serviceClass) {
        super(serviceClass);
    }

    public ProtectedBaseActivity(Class<? extends CoreService> serviceClass, Integer layoutId) {
        super(serviceClass, layoutId);
    }
}
