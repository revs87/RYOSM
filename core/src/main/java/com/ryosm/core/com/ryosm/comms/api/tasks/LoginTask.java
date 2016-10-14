package com.ryosm.core.com.ryosm.comms.api.tasks;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.base.CoreBaseActivity;
import com.ryosm.core.com.ryosm.comms.RoboSpice.BaseRequestListener;
import com.ryosm.core.com.ryosm.comms.RoboSpice.PostBaseSpiceRequest;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;
import com.ryosm.core.com.ryosm.utils.L;

public class LoginTask extends BaseTask<ResponseLogin> {

    private static final String URL = CommunicationCenter.ServiceLogin;
    private static final String TAG = "LoginTask";

    private final CoreBaseActivity activity;
    private final LoginListener listener;

    public LoginTask(CoreBaseActivity activity, LoginListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public interface LoginListener {
        void onLoginSuccess(ResponseLogin response);
    }

    public void getLogin(RequestLogin request) {
        PostBaseSpiceRequest<RequestLogin, ResponseLogin> spiceRequest =
                new PostBaseSpiceRequest<>(ResponseLogin.class, request, URL);

        if (activity != null) {
            spiceListener = new LoginRequestListener(activity, spiceRequest);
            if (Configs.INFINITE_CACHE) {
                activity.getSpiceManager().execute(
                        spiceRequest,
                        URL,
                        DurationInMillis.ALWAYS_RETURNED,
                        spiceListener);
            } else {
                activity.getSpiceManager().execute(spiceRequest, spiceListener);
            }
        }
    }

    private class LoginRequestListener extends BaseRequestListener<ResponseLogin> {

        public LoginRequestListener(CoreBaseActivity activity, PostBaseSpiceRequest<RequestLogin, ResponseLogin> spiceRequest) {
            super(activity, null, spiceRequest);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            super.onRequestFailure(spiceException);
            L.e(TAG, "onRequestFailure");

        }

        @Override
        public void onSuccess(ResponseLogin response) {
            L.e(TAG, "onSuccess");
            listener.onLoginSuccess(response);
        }
    }
}
