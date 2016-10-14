package com.ryosm.core.com.ryosm.core;

/**
 * Created by revs on 10/09/2016.
 */
public class Core {
    private CoreService service;

    public Core(CoreService service) {
        this.service = service;
    }

    public CoreService getService() {
        return service;
    }

}
