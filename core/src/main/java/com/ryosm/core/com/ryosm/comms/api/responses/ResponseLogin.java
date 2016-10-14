
package com.ryosm.core.com.ryosm.comms.api.responses;

import com.google.gson.annotations.SerializedName;
import com.ryosm.core.com.ryosm.comms.api.ResponseObject;

import java.io.Serializable;

public class ResponseLogin extends ResponseObject implements Serializable {

    private static final long serialVersionUID = 7697330253727352850L;

    @SerializedName("UserToken")
    private String userToken;


    public ResponseLogin(String result, String userToken) {
        super(result);
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }
}
