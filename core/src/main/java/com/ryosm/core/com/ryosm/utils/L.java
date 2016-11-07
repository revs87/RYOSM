package com.ryosm.core.com.ryosm.utils;


import android.text.TextUtils;
import android.util.Log;

import com.ryosm.core.com.ryosm.Configs;


/**
 * Created by revs on 02/09/2015.
 */
public final class L {

    public static void v(String a, String b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.v(a, b);
        }
    }

    public static void v(String a, String b, Throwable tr) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.v(a, b, tr);
        }
    }

    public static void i(String a, String b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.i(a, b);
        }
    }

    public static void i(String a, String b, Throwable tr) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.i(a, b, tr);
        }
    }

    public static void d(String a, String b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.d(a, b);
        }
    }

    public static void debug(String b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            b = check(b);
            Log.d("", b);
        }
    }

    public static void d(String a, String b, Throwable tr) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.d(a, b, tr);
        }
    }

    public static void e(String a, String b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.e(a, b);
        }
    }

    public static void e(String a, String b, Throwable tr) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.e(a, b, tr);
        }
    }

    public static void w(String a, String b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.w(a, b);
        }
    }

    public static void w(String a, String b, Throwable tr) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.w(a, b, tr);
        }
    }

    public static void wtf(String a, String b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.wtf(a, b);
        }
    }

    public static void wtf(String a, String b, Throwable tr) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            b = check(b);
            Log.wtf(a, b, tr);
        }
    }

    public static boolean isLoggable(String a, int b) {
        if (!Configs.HIDE_VERBOSE_LOGGING) {
            a = check(a);
            return Log.isLoggable(a, b);
        }
        return false;
    }

    private static String check(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "-";
        }
        return str;
    }

}
