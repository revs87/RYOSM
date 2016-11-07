
package com.ryosm.core.com.ryosm.comms.api.responses;

import com.google.gson.annotations.SerializedName;
import com.ryosm.core.com.ryosm.comms.api.ResponseObj;

public class ResponseRegister extends ResponseObj {

    @SerializedName("Message")
    private String message;
    @SerializedName("UserToken")
    private String userToken;

    public ResponseRegister(String nonce, String response, String message, String userToken) {
        super(nonce, response);
        this.message = message;
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getMessage() {
        return message;
    }
}
