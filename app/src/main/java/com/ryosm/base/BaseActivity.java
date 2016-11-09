package com.ryosm.base;

import com.ryosm.core.com.ryosm.base.CoreBaseActivity;
import com.ryosm.core.com.ryosm.service.CoreService;


public class BaseActivity extends CoreBaseActivity {

    private static final String TAG = "BaseActivity";

    public BaseActivity(Class<? extends CoreService> serviceClass) {
        super(serviceClass);
    }

    public BaseActivity(Class<? extends CoreService> serviceClass, Integer layoutId) {
        super(serviceClass, layoutId);
    }


    @Override
    public void showExternalStorageErrorDialog(boolean finish) {

    }

    @Override
    protected void onServiceUnbound() {

    }
}
