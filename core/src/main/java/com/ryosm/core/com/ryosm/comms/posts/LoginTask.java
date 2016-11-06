package com.ryosm.core.com.ryosm.comms.posts;

import android.view.View;

import com.ryosm.core.com.ryosm.comms.api.ResponseObject;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by revs on 16/10/2016.
 */

public class LoginTask extends MessageTask<ResponseLogin> {
    protected String publicKey;
    protected LoginListener loginListener = new NullLoginListener();

    public LoginTask(String url, String message, String nonce, String publicKey, LoginListener loginListener) {
        super(ResponseLogin.class, url, message, nonce);
        this.publicKey = publicKey;
        this.loginListener = loginListener;
    }

    public LoginTask(String url, String message, String nonce, String publicKey, View loadingView, LoginListener loginListener) {
        super(ResponseLogin.class, url, message, nonce, loadingView);
        this.publicKey = publicKey;
        this.loginListener = loginListener;
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
                ((ResponseLogin) response).setResponseStr(responseStr);
                loginListener.onSuccess((ResponseLogin) response);
            }

            @Override
            public void onFail(Exception e) {
                loginListener.onFail(e);
            }
        };
    }

    public interface LoginListener {
        void onSuccess(ResponseLogin response);

        void onFail(Exception e);
    }

    public class NullLoginListener implements LoginListener {
        @Override
        public void onSuccess(ResponseLogin response) {

        }

        @Override
        public void onFail(Exception e) {

        }
    }
}
