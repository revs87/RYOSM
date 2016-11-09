package com.ryosm.core.com.ryosm;

import com.octo.android.robospice.persistence.DurationInMillis;

public class Configs {

    // ----------- ENDPOINTS ---------------

    public static final String TEST_SERVER_URL = CommunicationCenter.TEST_SERVER_URL;
    public static final String QUA_SERVER_URL = CommunicationCenter.TEST_SERVER_URL;
    public static final String PRD_SERVER_URL = CommunicationCenter.PROD_SERVER_URL;

    public static final boolean IS_TEST_SERVER = true;
    public static final boolean IS_QUA_SERVER = false;
    public static final boolean IS_PRD_SERVER = false;


    // ----------- GENERIC ---------------

    // Flag that determinates if app if for production version
    public static final boolean HAS_ENDPOINT_SELECTOR = true;
    // Splash screen duration time (ms)
    public static final int SPLASH_SCREEN_DURATION_TIME = 3000;
    // Session time duration - > 180 000 = 3 minutes
    // Session time duration - > 480 000 = 8 minutes
    public static final int SESSION_TIMEOUT = 480000;

    // ----------- LOGGING ---------------

    // Enables the use of the crashlytics plugin
    public static final boolean CRASHLYTICS = true; //TODO
    // Enables the use of the log to file on devices
    public static final boolean LOG_TO_FILE = false;
    // Enables mocked Login
    public static final boolean MOCKED_LOGIN = false;
    public static final String MOCKED_LOGIN_USER = "";
    public static final String MOCKED_LOGIN_PASSWORD = "";

    // ----------- COMMUNICATIONS ---------------

    // Cache is permanent (use this to enable offline mode)
    public static final boolean INFINITE_CACHE = false;
    // Adds a 5s delay to the communications
    public static final boolean SLOW_COMMUNICATIONS = false;
    // Enables the use of the local server on the wwwd folder
    public static final boolean LOCAL_SERVER = false;
    // Number of threads running in parallel
    public static final int NBR_OF_THREADS_TO_PROCESS_REQUESTS = 8;
    // Connection timeout duration
    public static final int TIMEOUT_CONNECTION = 60000;
    // Default cache duration time
    public static final long DEFAULT_CACHE_DURATION = DurationInMillis.ONE_MINUTE * 5;

    //DEPRECATED
    // Should RSA public key be validated and DER encoded public key
    public static final boolean SHOULD_CHECK_RSA_KEY = false;
    public static final String RSA_PUBLIC_KEY = "";

    // ----------- LOGIN CAMPAIGNS ---------------
    // Login campaigns cache duration time (days)
    public static final long DEFAULT_LOGIN_CAMPAIGN_DURATION = DurationInMillis.ONE_DAY * 1;
    // Interval for the slideshow in seconds
    public static final int SLIDESHOW_INTERVAL = 3;
    // For accept the selection in navigation drawer menu.
    public static final boolean DRAWER_MENU_SELECTED = false;
    // Number of items on super recycle view
    public static final int RECYCLER_VIEW_MORE_TRIGGER = 10;

    // ----------- SECURITY ---------------
    // Secure cache
    public static final boolean ENCRYPT_CACHE = true;

    // Secure screen
    public static final boolean SHOULD_BLOCK_TAPJACKING = true;

    // Secure screen
    public static final boolean SHOULD_BLOCK_SCREENSHOTS = false;

    // Should detect rooted devices
    public static final boolean SHOULD_DETECT_ROOTED = true;
    // Should block rooted devices
    public static final boolean SHOULD_BLOCK_ROOTED = false;

    // Should check public key in case of a https connection.
    public static final boolean SHOULD_CHECK_SSL_PINNING = true;
    // Should check certificate not server trusted
    public static final boolean SHOULD_CHECK_TRUSTED_ON_SSL_PINNING_FAIL = false;
    // Should block certificate not server trusted
    public static final boolean SHOULD_BLOCK_TRUSTED_ON_SSL_PINNING_FAIL = false;
    // Should block public key
    public static final boolean SHOULD_BLOCK_CONNECTION_ON_SSL_PINNING_FAIL = false;

    // Should check connectivity status
    public static final boolean SHOULD_DETECT_CONNECTIVITY_STATUS = true;
    // Should block connectivity
    public static final boolean SHOULD_BLOCK_CONNECTIVITY_STATUS = true;

    // Logs handling
    public static final boolean HIDE_VERBOSE_LOGGING = false; //TODO

    // Shows Login prompt dialog after app minimized thus put to foreground
    public static final boolean RESTORE_FOREGROUND_PROTECTION = true;

    // Device support: false -> only phone is allowed
    public static final boolean DEVICE_ALLOW_ALL = true;

}