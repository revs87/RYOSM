package com.ryosm.activity;

import android.content.Intent;

import com.ryosm.RyoService;
import com.ryosm.core.com.ryosm.base.CoreLauncherActivity;

/**
 * Created by revs on 13/10/2016.
 */
public class LauncherActivity extends CoreLauncherActivity {

    public LauncherActivity() {
        super(RyoService.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
