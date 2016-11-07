package com.ryosm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.base.CoreLauncherActivity;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseRegister;
import com.ryosm.core.com.ryosm.comms.posts.LoginTask;
import com.ryosm.core.com.ryosm.comms.posts.RegisterTask;
import com.ryosm.core.com.ryosm.utils.L;

/**
 * Created by revs on 14/10/2016.
 */

public class HomeActivity extends CoreLauncherActivity {

    private TextView messageTv;
    private Button registerBtn;
    private Button loginBtn;
    private View loadingView;
    private EditText usernameEt;
    private EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadingView = findViewById(R.id.loading_view);
        usernameEt = (EditText) findViewById(R.id.home_username_et);
        passwordEt = (EditText) findViewById(R.id.home_password_et);
        messageTv = (TextView) findViewById(R.id.home_message_tv);
        loginBtn = (Button) findViewById(R.id.home_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 *
                 * */
                messageTv.setText("");

                RequestLogin request = getCore().getRyoLibsodium().getRequestLogin(
                        usernameEt.getText().toString().trim(),
                        passwordEt.getText().toString().trim());

                new com.ryosm.core.com.ryosm.comms.posts.LoginTask(
                        CommunicationCenter.getBaseUrl() + "/" + CommunicationCenter.ServiceLogin,
                        usernameEt.getText().toString().trim(),
                        request,
                        loadingView,
                        new LoginTask.LoginListener() {
                            @Override
                            public void onSuccess(final ResponseLogin response) {
                                L.d("onSuccess", "");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        messageTv.setText(
                                                "Response:\n" + response.getResponseStr()
                                        );
                                    }
                                });
                            }

                            @Override
                            public void onFail(Exception e) {
                                L.d("onFail", "");
                                e.printStackTrace();

                            }
                        }).postTaskExecute();
            }
        });


        registerBtn = (Button) findViewById(R.id.home_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *
                 * */
                messageTv.setText("");

                RequestLogin request = getCore().getRyoLibsodium().getRequestLogin(
                        usernameEt.getText().toString().trim(),
                        passwordEt.getText().toString().trim());

                new com.ryosm.core.com.ryosm.comms.posts.RegisterTask(
                        CommunicationCenter.getBaseUrl() + "/" + CommunicationCenter.ServiceRegister,
                        request,
                        loadingView,
                        new RegisterTask.RegisterListener() {
                            @Override
                            public void onSuccess(final ResponseRegister response) {
                                L.d("onSuccess", "");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        messageTv.setText(
                                                "Response:\n" + response.getResponseStr()
                                        );
                                    }
                                });
                            }

                            @Override
                            public void onFail(Exception e) {
                                L.d("onFail", "");
                                e.printStackTrace();

                            }
                        }).postTaskExecute();

            }
        });
    }
}
