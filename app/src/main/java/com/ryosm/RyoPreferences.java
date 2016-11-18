package com.ryosm;

import android.content.Context;
import android.content.SharedPreferences;

import com.ryosm.core.com.ryosm.service.Preferences;

/**
 * Created by revs on 17-11-2016.
 */

public class RyoPreferences extends Preferences {

    public RyoPreferences(Context context) {
        super(context);
    }

    @Override
    public int getPreferencesResId() {
        return R.xml.preferences;
    }

    @Override
    public boolean startServiceOnBoot() {
        return sharedPreferences.getBoolean("start_service_on_boot", true);
    }

    @Override
    public void setStartServiceOnBoot(boolean arg) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("start_service_on_boot", arg);
        editor.commit();
    }

    @Override
    public boolean screenAlwaysOnWhenLogging() {
        return sharedPreferences.getBoolean("screen_always_on_when_logging", false);
    }

    @Override
    public boolean exitDialogDontAskAgain() {
        return sharedPreferences.getBoolean("exit_dialog_dont_ask_again", false);
    }

    @Override
    public void setExitDialogDontAskAgain(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("exit_dialog_dont_ask_again", value);
        editor.commit();
    }

    @Override
    public void setAuthenticationToken(String token) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("authentication_token", token);
        editor.commit();
    }

    @Override
    public String getAuthenticationToken() {
        return sharedPreferences.getString("authentication_token", null);
    }

    @Override
    public boolean getVerboseLoggingEnabled() {
        return sharedPreferences.getBoolean("verbose_log", false);
    }

    @Override
    public void setVerboseLoggingEnabled(boolean arg) {
        sharedPreferences.edit().putBoolean("verbose_log", arg).commit();
    }

    @Override
    public boolean getWifiActivationEnabled() {
        return sharedPreferences.getBoolean("wifi_activation", true);
    }

    @Override
    public boolean getBtActivationEnabled() {
        return sharedPreferences.getBoolean("bt_activation", true);
    }
}
