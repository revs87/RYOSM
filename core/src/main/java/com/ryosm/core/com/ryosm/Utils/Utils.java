package com.ryosm.core.com.ryosm.utils;

import android.app.Activity;
import android.content.Intent;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by revs on 03/03/2016.
 */
public class Utils {

    /**
     * Generate a value suitable for use in {@link #setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /*
    * Prompts Messaging dialog
    * */
    public static void sendSMS(Activity activity, String smsContent) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.putExtra("sms_body", smsContent);
        sendIntent.putExtra("compose_mode", true);
        activity.startActivity(Intent.createChooser(sendIntent, "SMS: "));
    }

    /*
    * Prompts Email dialog
    * */
    public static void sendEmail(Activity activity, String emailSubject, String emailContent) {
        Intent mEmail = new Intent(Intent.ACTION_SEND);
        mEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{});
        mEmail.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        mEmail.putExtra(Intent.EXTRA_TEXT, emailContent);
        // prompts to choose email client
        mEmail.setType("message/rfc822");
        activity.startActivity(Intent.createChooser(mEmail, "Choose an email client to send your"));
    }

    public static boolean isBuildVersionOnInterval_16_19() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN
                && currentapiVersion <= android.os.Build.VERSION_CODES.KITKAT) {
            return true;
        }
        return false;
    }
}
