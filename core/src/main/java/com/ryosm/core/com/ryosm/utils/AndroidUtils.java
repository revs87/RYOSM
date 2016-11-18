package com.ryosm.core.com.ryosm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.format.Time;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by revs on 17-11-2016.
 */

public class AndroidUtils {

    private static String LOG_TAG = "Dc/AndroidUtils";

    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int MILLISECONDS_IN_MINUTE = 60 * MILLISECONDS_IN_SECOND;
    private static final int MILLISECONDS_IN_HOUR = 60 * MILLISECONDS_IN_MINUTE;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;
    private static final long MILLIS_PER_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR * 60 * 1000;
    private static final String dateFormat = "dd/MM/yyyy";
    private static final String timeFormat = "HH:mm";
    private static final String dateTimeFormat = "HH:mm:ss dd/MM/yyyy";


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


    public static String getStackTrace(Throwable t) {
        String stackTrace;

        try {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            stackTrace = sw.toString();
        } catch (Throwable tr) {
            return "unable to get stacktrace";
        }
        return stackTrace;
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE == null ? "unknown_release" : Build.VERSION.RELEASE;
    }

    public static String getDevice() {
        String brand = getMake();
        String model = getModel();
        return brand + "_" + model;
    }

    public static String getMake() {
        return Build.BRAND == null ? "unknown" : Build.BRAND;
    }

    public static String getModel() {
        return Build.MODEL == null ? "unknown_model" : Build.MODEL;
    }


    public static String getTimeUTC() {
        Time now = new Time();
        now.setToNow();
        now.switchTimezone(android.text.format.Time.TIMEZONE_UTC);

        return now.toString();
    }

    public static String SHA1(String text) {
        byte[] sha1hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
        } catch (Exception ex) {
        }
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static long getTimeInMillis() {
        return getCalendar().getTimeInMillis();
    }

    private static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar;
    }

    private static Calendar getCalendar(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date(timeInMillis));
        return calendar;
    }

    private static SimpleDateFormat getSimpleDateFormat(String formatString) {
        return new SimpleDateFormat(formatString, Locale.getDefault());
    }

    public static long getNTPTimeInMillis() {
        long now = 0;

        NTP_UTC_Time client = new NTP_UTC_Time();

        if (client.requestTime("pool.ntp.org", 2000)) {
            now = client.getNtpTime();
        }
        return now;
    }

    public static String getNTPUTCDatetime(long timeStamp) {

        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();

            int tzt = tz.getOffset(System.currentTimeMillis());

            timeStamp -= tzt;

            Date netDate = new Date(timeStamp);
            return getSimpleDateFormat(dateTimeFormat).format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }
}
