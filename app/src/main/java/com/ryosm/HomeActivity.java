package com.ryosm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ryosm.base.BaseActivity;
import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;
import com.ryosm.core.com.ryosm.comms.posts.LoginTask;
import com.ryosm.core.com.ryosm.utils.L;

/**
 * Created by revs on 14/10/2016.
 */

public class HomeActivity extends BaseActivity {

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
                new com.ryosm.core.com.ryosm.comms.posts.LoginTask(
//                        CommunicationCenter.getBaseUrl() + "/request.php",
                        CommunicationCenter.getBaseUrl() + "/login.php",
                        "wcrkdCGL+hhdJy0SCvrBuJ+HVSGkT5APwobYPD8zXkq9NWNyOSK0UpXY+yIrU71+DO9I65waJ54bkHVCKSnNNJvsEuhogypQUtLBoIZpbQFGS1pOj8hRZDta7Lb393syCVy3wd4mpIvQQpQ3ql4O",
                        "1NfmmgP0Jt3Edu2/HHhpcKuoisMI0j+5",
                        "hu3vlJQqK4G/JXxb2dC/sU6E6aZmmUibNBQa7T8Q50g=",
                        loadingView,
                        new LoginTask.LoginListener() {
                            @Override
                            public void onSuccess(ResponseLogin response) {
                                L.d("onSuccess", "");
                                loginTv.setText(
                                        "Response:\n\nResponse: " + response.getResponse()
                                                + "\nNonce: " + response.getNonce()
                                                + "\nUserToken: " + response.getUserToken()
                                );
                            }

                            @Override
                            public void onFail(Exception e) {
                                L.d("onFail", e.getMessage());

                            }
                        }).postTaskExecute();
            }
        });

    }
}
