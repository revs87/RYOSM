package com.ryosm.core.com.ryosm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.CookieManager;

public class CommunicationCenter {

    /* ENDPOINTS ---------(set here ONLY.)--------- */
    public static final String TEST_SERVER_URL = "https://qkhxbpkjp45uvi7n.onion.to";
    public static final String TEST_SERVER_URL2 = "http://bim2.itsector.pt";
    public static final String QUA_SERVER_URL = "http://192.168.17.47:8080/mbim_json";
    public static final String TEST_SERVER_LOGIN = "http://192.168.17.47:8080/mbim_json2";
    public static final String PRD_SERVER_URL_HTTP = "http://smartizi.millenniumbim.co.mz/mbim_json";
    public static final String PRD_SERVER_URL_HTTPS = "https://smartizi.millenniumbim.co.mz/mbim_json";
    public static final String PRD_SERVER_URL = "https://smartizi.millenniumbim.co.mz/mbim_json_p";

    /* -------------------------------------------- */

    public static final String ServiceRegister = "register.php";
    public static final String ServiceLogin = "login.php";
    public static final String ServiceCustom = "custom.php";

    private static final CookieManager cookieManager = new CookieManager();

    public static CookieManager getCookieManager() {
        return cookieManager;
    }

    public static String getBaseUrl() {
//        if (ConfigData.getEndpoint() != null) {
//            return ConfigData.getEndpoint();
//        } else {
        return getServerPath();
//        }
    }

    public static String getDefaultBaseUrl() {
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

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected())
                || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

}
