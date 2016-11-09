package com.ryosm.core.com.ryosm.comms.posts;

import android.view.View;

import com.google.gson.Gson;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseRegister;
import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.db.PersistentData;

import org.apache.http.message.BasicNameValuePair;


/**
 * Created by revs on 16/10/2016.
 */

public class RegisterTask extends MessageTask<ResponseRegister> {
    protected String publicKey;
    protected RegisterListener registerListener = new NullRegisterListener();

    public RegisterTask(Core core, String url, RequestLogin request, View loadingView, RegisterListener registerListener) {
        super(core, ResponseRegister.class, url, request.getMessage(), request.getNonce(), loadingView);
        this.publicKey = request.getPublicKey();
        this.registerListener = registerListener;
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
                ResponseRegister response = (ResponseRegister) resp;
                response.setResponseStr(responseStr);

                Gson gson = new Gson();
                String responseStrDecrypted = getCore().getRyoLibsodium().decryptObject(response);
                ResponseRegister responseObj = gson.fromJson(responseStrDecrypted, ResponseRegister.class);
                responseObj.setResponseStr(responseStrDecrypted);

                if (responseObj.getResult().equalsIgnoreCase("True")) {
                    PersistentData.getSingleton().setUserToken(responseObj.getUserToken());
                }

                registerListener.onSuccess(responseObj);
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
