package com.ryosm.core.com.ryosm.comms.RoboSpice.tasks;

import android.view.View;

import com.ryosm.core.com.ryosm.comms.RoboSpice.BaseRequestListener;

public class BaseRSTask<T> {

    protected BaseRequestListener<T> spiceListener;

    public void updateUI(View rootView) {
        if (spiceListener != null) {
            spiceListener.updateUI(rootView);
        }
    }

}
