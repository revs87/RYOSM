package com.ryosm.core.com.ryosm.comms.posts;

import android.view.View;

import com.google.gson.Gson;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;
import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.db.PersistentData;

import org.apache.http.message.BasicNameValuePair;


/**
 * Created by revs on 16/10/2016.
 */

public class LoginTask extends MessageTask<ResponseLogin> {
    protected String username;
    protected String publicKey;
    protected LoginListener loginListener = new NullLoginListener();

    public LoginTask(Core core, String url, String username, RequestLogin request, View loadingView, LoginListener loginListener) {
        super(core, ResponseLogin.class, url, request.getMessage(), request.getNonce(), loadingView);
        this.username = username;
        this.publicKey = request.getPublicKey();
        this.loginListener = loginListener;
    }

    @Override
    public void postTaskExecute() {
        postTask.execute(
                new BasicNameValuePair("message", message),
                new BasicNameValuePair("nonce", nonce),
                new BasicNameValuePair("publicKey", publicKey));
    }

    @Override
    public PostListener getExtendedPostListener() {
        return new PostListener() {
            @Override
            public void onSuccess(Object resp, String responseStr) {
                ResponseLogin response = (ResponseLogin) resp;
                response.setResponseStr(responseStr);

                Gson gson = new Gson();
                String responseStrDecrypted = getCore().getRyoLibsodium().decryptObject(response);
                ResponseLogin responseObj = gson.fromJson(responseStrDecrypted, ResponseLogin.class);
                responseObj.setResponseStr(responseStrDecrypted);

                if (responseObj.getResult().equalsIgnoreCase("True")) {
                    PersistentData.getSingleton().setUsername(username);
                    PersistentData.getSingleton().setUserToken(responseObj.getUserToken());
                    PersistentData.getSingleton().setCsrf(responseObj.getCsrf());
                }

                loginListener.onSuccess(responseObj);
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
