package com.ryosm.core.com.ryosm.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ryosm.core.com.ryosm.utils.L;

import java.util.HashMap;


/**
 * Created by revs on 09-11-2016.
 */
public abstract class Preferences {

    protected final SharedPreferences sharedPreferences;

    private static final String LOG_TAG = "Dc/Preferences";
    protected static Context appContext = null;

    public static Context getAppContext() {
        if (appContext == null) {
            L.d(LOG_TAG, "Null app context in sharedpreferences", new RuntimeException("Null App Context"));
        }
        return appContext;
    }

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public Preferences(final Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void save(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor = editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor = editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor = editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor = editor.putBoolean(key, (Boolean) value);
        } else {
            return;
        }
        editor.commit();
    }

    public abstract int getPreferencesResId();


    public abstract void setAuthenticationToken(final String token);

    public abstract String getAuthenticationToken();

    public abstract String getSecret();

    public abstract void setSecret(final String secret);

    public abstract String getPublicKey();

    public abstract void setPublicKey(final String key);

    public abstract void setCsrf(String csrf);

    public abstract String getCsrf();


    public abstract boolean startServiceOnBoot();

    public abstract void setStartServiceOnBoot(boolean arg);

    public abstract boolean screenAlwaysOnWhenLogging();

    public abstract boolean exitDialogDontAskAgain();

    public abstract void setExitDialogDontAskAgain(boolean value);

    public abstract boolean getVerboseLoggingEnabled();

    public abstract void setVerboseLoggingEnabled(boolean arg);

    public abstract boolean getWifiActivationEnabled();

    public abstract boolean getBtActivationEnabled();


    public HashMap<String, String> toHashMap() {
        HashMap<String, String> prefs = new HashMap<String, String>();
        prefs.put("start_service_on_boot", startServiceOnBoot() ? "1" : "0");
        prefs.put("screen_always_on_when_logging", screenAlwaysOnWhenLogging() ? "1" : "0");
        prefs.put("exit_dialog_dont_ask_again", exitDialogDontAskAgain() ? "1" : "0");
        return prefs;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setUid(String s) {
        sharedPreferences.edit().putString("ryo_uid", s).commit();
    }

    public String getUid() {
        return sharedPreferences.getString("ryo_uid", "NIL_UID");
    }

    public void wipe() {
        sharedPreferences.edit().clear().commit();
        L.e(LOG_TAG, "Shared preferences wiped");
    }

}
