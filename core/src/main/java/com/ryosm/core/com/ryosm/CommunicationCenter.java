package com.ryosm.core.com.ryosm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.CookieManager;

public class CommunicationCenter {


    public static final String TEST_SERVER_URL = "https://messenger.penagil.com/";
    public static final String PROD_SERVER_URL = "https://messenger-dev.hackersanonymous.net";

    /* -------------------------------------------- */

    public static final String ServiceRegister = "register.php";
    public static final String ServiceLogin = "login.php";
    public static final String ServiceCustom = "custom.php";

    private static final CookieManager cookieManager = new CookieManager();

    public static CookieManager getCookieManager() {
        return cookieManager;
    }

    public static String getBaseUrl() {
        return getServerPath();
    }

    private static String getServerPath() {
        if (Configs.IS_TEST_SERVER) {
            return Configs.TEST_SERVER_URL;
        } else if (Configs.IS_QUA_SERVER) {
            return Configs.QUA_SERVER_URL;
        } else {//if (Configs.IS_PRD_SERVER) {
            return Configs.PRD_SERVER_URL;
        }
    }


}
