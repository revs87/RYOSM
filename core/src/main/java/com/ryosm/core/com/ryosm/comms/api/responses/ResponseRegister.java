
package com.ryosm.core.com.ryosm.comms.api.responses;

import com.google.gson.annotations.SerializedName;
import com.ryosm.core.com.ryosm.comms.api.ResponseObject;

import java.io.Serializable;

public class ResponseRegister extends ResponseObject implements Serializable {

    private static final long serialVersionUID = 7697330253727352850L;

    @SerializedName("UserToken")
    private String userToken;


    public ResponseRegister(String result, String nonce, String userToken) {
        super(result, nonce);
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }
}
