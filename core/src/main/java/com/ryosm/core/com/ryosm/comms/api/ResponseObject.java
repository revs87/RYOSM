
package com.ryosm.core.com.ryosm.comms.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseObject implements Serializable {

    private static final long serialVersionUID = 7697330253727352850L;

    @SerializedName("Result")
    private String result;

    public ResponseObject(String result) {
        this.result = result;
    }

    public String getResult() {
        
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
