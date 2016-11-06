
package com.ryosm.core.com.ryosm.comms.api.requests;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestLoginObj implements Serializable {

    private static final long serialVersionUID = 7697330253727352850L;

    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    @SerializedName("PublicKey")
    private String publicKey;

    public RequestLoginObj(String username, String password, String publicKey) {
        this.username = username;
        this.password = password;
        this.publicKey = publicKey;
    }
}
