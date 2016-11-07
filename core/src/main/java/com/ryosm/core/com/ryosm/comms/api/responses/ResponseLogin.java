
package com.ryosm.core.com.ryosm.comms.api.responses;

import com.google.gson.annotations.SerializedName;
import com.ryosm.core.com.ryosm.comms.api.ResponseObj;

public class ResponseLogin extends ResponseObj {

    @SerializedName("UserToken")
    private String userToken;
    @SerializedName("CSRF")
    private String csrf;

    public ResponseLogin(String nonce, String response, String userToken, String csrf) {
        super(nonce, response);
        this.userToken = userToken;
        this.csrf = csrf;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }
}
