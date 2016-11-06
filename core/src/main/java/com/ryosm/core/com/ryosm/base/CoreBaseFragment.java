package com.ryosm.core.com.ryosm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.utils.L;
import com.ryosm.core.com.ryosm.comms.RoboSpice.SpiceManagerEncrypted;

public class CoreBaseFragment extends Fragment {
    private static final String TAG = CoreBaseFragment.class.getSimpleName();

    private SpiceManagerEncrypted spiceManager;

    public CoreBaseActivity getBaseActivity() {
        return CoreLauncherActivity.getCurrentActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getDashboardSpiceManager() == null) {
//            spiceManager = new SpiceManagerEncrypted(
//                    CacheableSpringAndroidSpiceService.class);
//            spiceManager.start(getBaseActivity());
//        }
    }

    /* TAPJACKING override */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (Configs.SHOULD_BLOCK_TAPJACKING) {
            if (view != null) {
                view.setFilterTouchesWhenObscured(true);
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.v(TAG, "onActivityCreated");

    }

    @Override
    public void onDestroy() {
        if (spiceManager != null) {
            try {
                spiceManager.shouldStop();
            } catch (Exception e) {
                L.w(TAG, "Couldn't stop spiceManager", e);
            }
        }
        try {
            super.onDestroy();
        } catch (Exception e) {
            L.e(TAG, "Error calling super on destroy");
        }

    }

//    public SpiceManagerEncrypted getSpiceManager() {
//
//        SpiceManagerEncrypted dbSpiceManager = getDashboardSpiceManager();
//        if (dbSpiceManager != null) {
//            return dbSpiceManager;
//        }
//
//        if (spiceManager == null) {
//            spiceManager = new SpiceManagerEncrypted(
//                    CacheableSpringAndroidSpiceService.class);
//            spiceManager.start(getBaseActivity());
//        }
//
//        return spiceManager;
//    }
//
//    // If the fragment is in the dashboard context,
//    // it return the DashboardFragment own spiceManager
//    private SpiceManagerEncrypted getDashboardSpiceManager() {
//        Fragment frag = null;
//        try {
//            frag = getBaseActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame);
//        } catch (Exception e) {
//            if (spiceManager == null) {
//                spiceManager = new SpiceManagerEncrypted(
//                        CacheableSpringAndroidSpiceService.class);
//                spiceManager.start(getBaseActivity());
//            }
//
//            return spiceManager;
//        }
////        if (frag != null && frag instanceof DashboardFragment) {
////            return ((DashboardFragment) frag).getSpiceManager();
////        }
//
//        if (spiceManager == null) {
//            spiceManager = new SpiceManagerEncrypted(
//                    CacheableSpringAndroidSpiceService.class);
//            spiceManager.start(getBaseActivity());
//        }
//
//        return spiceManager;
//    }

    public ActionBar getSupportActionBar() {
        return CoreLauncherActivity.getCurrentActivity().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getBaseActivity().setSupportActionBar(toolbar);
    }

}
