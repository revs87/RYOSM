package com.ryosm.core.com.ryosm.core;

import com.ryosm.core.com.ryosm.comms.libsodium.RyoLibsodium;

/**
 * Created by revs on 10/09/2016.
 */
public class Core {
    private CoreService service;
    private RyoLibsodium ryoLibsodium;

    public Core() {
        ryoLibsodium = new RyoLibsodium();

    }


    public RyoLibsodium getRyoLibsodium() {
        return ryoLibsodium;
    }

    public void setRyoLibsodium(RyoLibsodium ryoLibsodium) {
        this.ryoLibsodium = ryoLibsodium;
    }
}
