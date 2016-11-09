package com.ryosm.core.com.ryosm.service;


import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.utils.L;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by revs on 09-11-2016.
 */

public class CoreAccessProvider {

    private final String LOG_TAG = "CoreAccessProvider";

    private AtomicBoolean coreAvailable = new AtomicBoolean();

    private Vector<ICoreAvailableHandler> pendingRequests = new Vector<ICoreAvailableHandler>();

    private Core core = null;

    public void requestCore(final ICoreAvailableHandler coreAvailableHandler) {
        L.d(LOG_TAG, "requestCore");
        if (coreAvailable.get()) {
            L.d(LOG_TAG, "requestCore - available, returning immediately");
            coreAvailableHandler.coreAvailable(core);
        } else {
            L.d(LOG_TAG, "requestCore - not available, queuing");
            pendingRequests.add(coreAvailableHandler);
        }
    }

    public void coreAvailable(final Core core) {
        L.d(LOG_TAG, "coreAvailable");
        this.core = core;
        coreAvailable.set(true);
        for (final ICoreAvailableHandler handler : pendingRequests) {
            handler.coreAvailable(core);
        }
    }

    public void coreUnavailable() {
        L.d(LOG_TAG, "coreUnavailable");
        coreAvailable.set(false);
        core = null;
    }

    public boolean isCoreAvailable() {
        return coreAvailable.get() && core != null;
    }
}
