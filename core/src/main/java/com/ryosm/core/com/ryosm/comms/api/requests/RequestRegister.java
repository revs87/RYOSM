
package com.ryosm.core.com.ryosm.comms.api.requests;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestRegister implements Serializable {

    private static final long serialVersionUID = 7697330253727352850L;

    @SerializedName("message")
    private String message;
    @SerializedName("nonce")
    private String nonce;
    @SerializedName("publicKey")
    private String publicKey;


    public RequestRegister(String message, String nonce, String publicKey) {
        this.message = message;
        this.nonce = nonce;
        this.publicKey = publicKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
