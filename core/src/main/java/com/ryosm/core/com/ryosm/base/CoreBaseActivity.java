package com.ryosm.core.com.ryosm.base;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.comms.RoboSpice.CacheableSpringAndroidSpiceService;
import com.ryosm.core.com.ryosm.comms.RoboSpice.SpiceManagerEncrypted;
import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.service.CoreAccessProvider;
import com.ryosm.core.com.ryosm.service.CoreActivityServiceConnection;
import com.ryosm.core.com.ryosm.service.CoreService;
import com.ryosm.core.com.ryosm.service.ICoreAvailableHandler;
import com.ryosm.core.com.ryosm.service.IServiceBindHandler;
import com.ryosm.core.com.ryosm.service.Preferences;
import com.ryosm.core.com.ryosm.utils.L;

import java.util.concurrent.atomic.AtomicBoolean;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class CoreBaseActivity extends AppCompatActivity implements IServiceBindHandler {

    private static final String TAG = CoreBaseActivity.class.getSimpleName();

    /* Restore Foreground protection screen */
    public static final String SHOW_PROTECTION = "SHOW_PROTECTION";
    private static final String LOG_TAG = "CoreBaseActivity";
    public static boolean isInBackground = false;
    public static boolean showRestoreForegroundLoginScreen = false;
    public static boolean pushedBackButtonBackToolbar = false;
    private Integer layoutId;
    private boolean showMandatoryProtection;
    private SpiceManagerEncrypted spiceManager;
    private Bundle savedInstanceState;
    protected final CoreAccessProvider coreAccessProvider = new CoreAccessProvider();

    public static CoreBaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(CoreBaseActivity currentActivity) {
        CoreBaseActivity.currentActivity = currentActivity;
    }

    protected static CoreBaseActivity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentActivity = this;

        showRestoreForegroundLoginScreen = false;

        if (spiceManager == null) {
            spiceManager = new SpiceManagerEncrypted(
                    CacheableSpringAndroidSpiceService.class);
        }

        /*
        * Case scenario:
        *    Events: TaskExecute -> Minimize -> IntentInflateActivityOnSuccess (opens unprotected activity)
        *    Usage: CoreBaseIntent
        * */
        if (getIntent() != null) {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                showMandatoryProtection = b.getBoolean(CoreBaseActivity.SHOW_PROTECTION, false);
                if (showMandatoryProtection) {
                    showRestoreForegroundLoginScreen = true;
                }
            }
        }


        /* DARKSCREEN preview and SCREENSHOTS blockage */
        if (Configs.SHOULD_BLOCK_SCREENSHOTS) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        L.v(TAG, "CoreBaseActivity onCreate");

        spiceManager.start(this);

        /**
         *
         *
         *
         *
         *
         *
         *
         *
         * */
        Preferences.setAppContext(getApplicationContext());

        this.savedInstanceState = savedInstanceState;
        if (layoutId != null) {
            setContentView(layoutId);
        }
        isActivityPaused.set(true);
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    public void setupToolbar(@Nullable Toolbar toolbar) {
        if (toolbar != null) {
//            toolbar.setNavigationIcon(R.drawable.ic_launcher);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pushedBackButtonBackToolbar = true;
    }

    @Override
    public void finish() {
        super.finish();
        pushedBackButtonBackToolbar = true;
    }

    /* Whenever the click occurs outside the soft-keyboard, the soft-keyboard closes. */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ev.getAction() == MotionEvent.ACTION_UP) {
            final View view = getCurrentFocus();

            if (view != null) {
                final boolean consumed = super.dispatchTouchEvent(ev);

                final View viewTmp = getCurrentFocus();
                final View viewNew = viewTmp != null ? viewTmp : view;

                if (viewNew.equals(view)) {
                    final Rect rect = new Rect();
                    final int[] coordinates = new int[2];

                    view.getLocationOnScreen(coordinates);

                    rect.set(coordinates[0], coordinates[1], coordinates[0]
                                    + view.getWidth(),
                            coordinates[1] + view.getHeight());

                    final int x = (int) ev.getX();
                    final int y = (int) ev.getY();

                    if (rect.contains(x, y)) {
                        return consumed;
                    }
                } else if (viewNew instanceof EditText) {
                    return consumed;
                }

                final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(
                        viewNew.getWindowToken(), 0);

                viewNew.clearFocus();

                return consumed;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        L.v(TAG, "CoreBaseActivity onDestroy");
        try {
            spiceManager.shouldStop();
        } catch (Exception e) {
            L.w(TAG, "SpiceManager couldn't stop", e);
        }
        super.onDestroy();
        /**
         *
         *
         *
         *
         *
         * */
        unbindService();
        isActivityPaused.set(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isActivityPaused.set(true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        CoreLauncherActivity.setCurrentActivity(this);
        super.onResume();
        L.v(TAG, "CoreBaseActivity onResume");
        isInBackground = false;

        if ((Configs.RESTORE_FOREGROUND_PROTECTION
                && showMandatoryProtection)
                || (Configs.RESTORE_FOREGROUND_PROTECTION
                && showRestoreForegroundLoginScreen
                && !pushedBackButtonBackToolbar
//                && (CoreLauncherActivity.getCurrentActivity() instanceof PrivateActivity
                || CoreLauncherActivity.getCurrentActivity() instanceof ProtectedCoreBaseActivity)) {

            /*
            * Restore Foreground
            * Background to Foreground DialogFragment
            * */
//            if (restoreForegroundDialogFragment == null) {
//                restoreForegroundDialogFragment = RestoreForegroundDialogFragment.newInstance();
//                restoreForegroundDialogFragment.setCancelable(false);
//            }
//
//            if (!restoreForegroundDialogFragment.isVisible()) {
//                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//                restoreForegroundDialogFragment.show(fragmentManager, TAG);
//            }
        }

        if (pushedBackButtonBackToolbar) {
            pushedBackButtonBackToolbar = false;
        }

        /**
         *
         *
         *
         *
         *
         *
         *
         * */
        setupService();
        isActivityPaused.set(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.v(TAG, "onPause");
        isInBackground = true;
        showRestoreForegroundLoginScreen = true;
        isActivityPaused.set(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.v(TAG, "onActivityResult");
        showRestoreForegroundLoginScreen = false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public SpiceManagerEncrypted getSpiceManager() {
        return spiceManager;
    }

    /**
     *
     *
     *
     *
     * */

    private final Class<? extends CoreService> serviceClass;
    private ServiceConnection serviceConnection;
    private CoreService service;
    private AtomicBoolean isBound = new AtomicBoolean(false);
    private Core core = null;
    private AtomicBoolean isActivityPaused = new AtomicBoolean(false);

    public AtomicBoolean isPaused() {
        return isActivityPaused;
    }

    public CoreBaseActivity(Class<? extends CoreService> serviceClass) {
        this(serviceClass, null);
    }

    public CoreBaseActivity(Class<? extends CoreService> serviceClass, final Integer layoutId) {
        L.d(LOG_TAG, "Construct()");
        this.serviceClass = serviceClass;
        this.layoutId = layoutId;
    }

    public void getCore(ICoreAvailableHandler coreAvailableHandler) {
        L.d(LOG_TAG, "getCore requested");
        coreAccessProvider.requestCore(coreAvailableHandler);
    }

    public CoreService getService() {
        if (service == null) {
            throw new RuntimeException("Service is not set. Either the service failed to init or the context does not propagate onCreate and onDestroy events to the superclass correctly!");
        }
        return service;
    }

    private void setupService() {
        if (getExternalFilesDir(null) != null) {
            if (serviceConnection != null) {
                L.d(LOG_TAG, "Not required serviceConnection is pending");
                return;
            }

            serviceConnection = new CoreActivityServiceConnection(this);
            startService(new Intent(CoreBaseActivity.this, serviceClass));
            bindService();
        } else {
            showExternalStorageErrorDialog(true);
        }
    }

    public abstract void showExternalStorageErrorDialog(final boolean finish);

    void bindService() {
        if (bindService(new Intent(CoreBaseActivity.this, serviceClass), serviceConnection, Context.BIND_AUTO_CREATE)) {
            //TODO: Log failure
        } else {
            L.e(LOG_TAG, "Failed to init to service");
        }
    }

    void unbindService() {
        if (isBound.get()) {
            // Detach our existing connection.
            unbindService(serviceConnection);
            serviceConnection = null;
        }
    }

    @Override
    public void serviceBound() {

        core = service.getCore();
        core.setCurrentActivity(this);

        isBound.set(true);

        onServiceBound(savedInstanceState, core);

        coreAccessProvider.coreAvailable(core);
    }

    @Override
    public void serviceUnbound(CoreService service) {
        coreAccessProvider.coreUnavailable();
        isBound.set(false);
        onServiceUnbound();
        core = null;
        core.setCurrentActivity(null);
    }

    @Override
    public void setService(final CoreService service) {
        this.service = service;
    }

    protected void onServiceBound(Bundle savedInstanceState, final Core core) {
    }

    protected abstract void onServiceUnbound();

    protected Core getCore() {
        return core;
    }
}
