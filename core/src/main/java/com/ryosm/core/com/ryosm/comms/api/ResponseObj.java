
package com.ryosm.core.com.ryosm.comms.api;

import com.google.gson.annotations.SerializedName;

public class ResponseObj extends Response {

    @SerializedName("Result")
    private String result;

    public ResponseObj(String nonce, String response) {
        super(nonce, response);
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
