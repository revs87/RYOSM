package com.ryosm.core.com.ryosm.objects;

/**
 * Created by revs on 16-11-2016.
 */

public class User {
    private String identifier;
    private String publicKey;

    public User(String identifier, String publicKey) {
        this.identifier = identifier;
        this.publicKey = publicKey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
