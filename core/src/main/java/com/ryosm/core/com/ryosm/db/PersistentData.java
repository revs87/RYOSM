package com.ryosm.core.com.ryosm.db;

import android.content.Context;

/**
 * @description Class used to memory cache management.
 */

public class PersistentData {

    private static final String TAG = PersistentData.class.getSimpleName();

    // Persistent Data
    private static PersistentData persistentData;
    private String username;
    private SQLiteHelper databaseHelper;

    /**
     * Constructor
     */
    private PersistentData() {
    }

    /*
     * Get Singleton
     */
    public static synchronized PersistentData getSingleton() {
        if (persistentData == null) {
            persistentData = new PersistentData();
        }
        return persistentData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void initDatabaseHelper(Context context) {
        databaseHelper = new SQLiteHelper(context);
    }

    public SQLiteHelper getDatabaseHelper() {
        return databaseHelper;
    }

}
