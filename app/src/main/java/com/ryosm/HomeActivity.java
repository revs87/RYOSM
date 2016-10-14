package com.ryosm;

import android.os.Bundle;

import com.ryosm.base.BaseActivity;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;
import com.ryosm.core.com.ryosm.comms.api.tasks.LoginTask;

/**
 * Created by revs on 14/10/2016.
 */

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginTask loginTask = new LoginTask(this, new LoginTask.LoginListener() {
            @Override
            public void onLoginSuccess(ResponseLogin response) {

            }
        });
        RequestLogin requestLogin = new RequestLogin("", "", "");
        loginTask.getLogin(requestLogin);
    }
}
