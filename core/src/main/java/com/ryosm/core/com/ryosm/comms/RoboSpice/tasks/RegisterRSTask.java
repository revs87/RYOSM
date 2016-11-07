package com.ryosm.core.com.ryosm.comms.RoboSpice.tasks;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.base.CoreBaseActivity;
import com.ryosm.core.com.ryosm.comms.RoboSpice.BaseRequestListener;
import com.ryosm.core.com.ryosm.comms.RoboSpice.PostBaseSpiceRequest;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseRegister;
import com.ryosm.core.com.ryosm.utils.L;

public class RegisterRSTask extends BaseRSTask<ResponseRegister> {

    private static final String URL = CommunicationCenter.ServiceRegister;
    private static final String TAG = "RegisterRSTask";

    private final CoreBaseActivity activity;
    private final RegisterListener listener;

    public RegisterRSTask(CoreBaseActivity activity, RegisterListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public interface RegisterListener {
        void onRegisterSuccess(ResponseRegister response);
    }

    public void getRegister(RequestLogin request) {
        PostBaseSpiceRequest<RequestLogin, ResponseRegister> spiceRequest =
                new PostBaseSpiceRequest<>(ResponseRegister.class, request, URL);

        if (activity != null) {
            spiceListener = new RegisterRequestListener(activity, spiceRequest);
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

    private class RegisterRequestListener extends BaseRequestListener<ResponseRegister> {

        public RegisterRequestListener(CoreBaseActivity activity, PostBaseSpiceRequest<RequestLogin, ResponseRegister> spiceRequest) {
            super(activity, null, spiceRequest);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            super.onRequestFailure(spiceException);
            L.e(TAG, "onRequestFailure");

        }

        @Override
        public void onSuccess(ResponseRegister response) {
            L.e(TAG, "onSuccess");
            listener.onRegisterSuccess(response);
        }
    }
}
