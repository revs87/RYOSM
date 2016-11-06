package com.ryosm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.base.CoreLauncherActivity;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestObject;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;
import com.ryosm.core.com.ryosm.comms.posts.LoginTask;
import com.ryosm.core.com.ryosm.utils.L;

/**
 * Created by revs on 14/10/2016.
 */

public class HomeActivity extends CoreLauncherActivity {

    private TextView loginTv;
    private Button loginBtn;
    private View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadingView = findViewById(R.id.loading_view);
        loginTv = (TextView) findViewById(R.id.home_login_tv);
        loginBtn = (Button) findViewById(R.id.home_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 *
                 * */
                loginTv.setText("");

                RequestLogin request = getCore().getRyoLibsodium().getRequestLogin("rui", "lala");

                new com.ryosm.core.com.ryosm.comms.posts.LoginTask(
                        CommunicationCenter.getBaseUrl() + "/" + CommunicationCenter.ServiceLogin,
                        request.getMessage(),
                        request.getNonce(),
                        request.getPublicKey(),
                        loadingView,
                        new LoginTask.LoginListener() {
                            @Override
                            public void onSuccess(final ResponseLogin response) {
                                L.d("onSuccess", "");
                                final String responseStr = getCore().getRyoLibsodium().decryptObject(response);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loginTv.setText(
                                                "All:\n\nResponseEncrypted: \n" + response.getResponse()
                                                        + "\n\n" +
                                                        "Nonce: \n" + response.getNonce()
                                                        + "\n\n" +
                                                        "Response: \n" + responseStr
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
