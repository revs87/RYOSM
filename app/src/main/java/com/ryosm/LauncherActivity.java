package com.ryosm;

import android.content.Intent;

import com.ryosm.core.com.ryosm.base.CoreLauncherActivity;

/**
 * Created by revs on 13/10/2016.
 */
public class LauncherActivity extends CoreLauncherActivity {

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
