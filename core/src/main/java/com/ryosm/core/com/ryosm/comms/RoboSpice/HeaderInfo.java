package com.ryosm.core.com.ryosm.comms.RoboSpice;


public class HeaderInfo {

    private static String appVersion;

    public static String getApiLevel() {
        return String.valueOf(android.os.Build.VERSION.SDK_INT);
    }

}
