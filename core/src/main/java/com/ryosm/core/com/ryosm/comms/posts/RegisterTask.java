package com.ryosm.core.com.ryosm.comms.posts;

import android.view.View;

import com.ryosm.core.com.ryosm.comms.api.ResponseObject;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseRegister;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by revs on 16/10/2016.
 */

public class RegisterTask extends MessageTask<ResponseRegister> {
    protected String publicKey;
    protected RegisterListener registerListener = new NullRegisterListener();

    public RegisterTask(String url, String message, String nonce, String publicKey, RegisterListener registerListener) {
        super(ResponseRegister.class, url, message, nonce);
        this.publicKey = publicKey;
        this.registerListener = registerListener;
    }

    public RegisterTask(String url, String message, String nonce, String publicKey, View loadingView, RegisterListener registerListener) {
        super(ResponseRegister.class, url, message, nonce, loadingView);
        this.publicKey = publicKey;
        this.registerListener = registerListener;
    }

    @Override
    public void postTaskExecute() {
        postTask.execute(new BasicNameValuePair("message", message), new BasicNameValuePair("nonce", nonce), new BasicNameValuePair("publicKey", publicKey));
    }

    @Override
    public PostListener getExtendedPostListener() {
        return new PostListener() {
            @Override
            public void onSuccess(Object response, String responseStr) {
                ((ResponseObject) response).setResponseStr(responseStr);
                registerListener.onSuccess((ResponseRegister) response);
            }

            @Override
            public void onFail(Exception e) {
                registerListener.onFail(e);
            }
        };
    }

    public interface RegisterListener {
        void onSuccess(ResponseRegister response);

        void onFail(Exception e);
    }

    public class NullRegisterListener implements RegisterListener {
        @Override
        public void onSuccess(ResponseRegister response) {

        }

        @Override
        public void onFail(Exception e) {

        }
    }
}
