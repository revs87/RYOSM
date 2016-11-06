
package com.ryosm.core.com.ryosm.comms.api.responses;

import com.ryosm.core.com.ryosm.comms.api.ResponseObject;

import java.io.Serializable;

public class ResponseLogin extends ResponseObject implements Serializable {

    private static final long serialVersionUID = 7697330253727352850L;

    public ResponseLogin(String result, String nonce) {
        super(result, nonce);
    }
}
