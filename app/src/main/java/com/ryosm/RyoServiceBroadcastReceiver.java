package com.ryosm;

import com.ryosm.core.com.ryosm.broadcastReceivers.ServiceBroadcastReceiver;
import com.ryosm.core.com.ryosm.service.CoreService;

/**
 * Created by revs on 09-11-2016.
 */

public class RyoServiceBroadcastReceiver extends ServiceBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return CoreService.class;
    }
}