
package com.ryosm.core.com.ryosm.comms.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseObject implements Serializable {

    private static final long serialVersionUID = 7697330253727352850L;
    private String responseStr;

    @SerializedName("Nonce")
    private String nonce;
    @SerializedName("Response")
    private String response;


    public ResponseObject(String nonce, String response) {
        this.nonce = nonce;
        this.response = response;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }
}
