package com.ryosm.core.com.ryosm.service;

/**
 * Created by revs on 09-11-2016.
 */

public interface IServiceBindHandler {

    void serviceBound();

    void serviceUnbound(final CoreService service);

    void setService(final CoreService service);

}