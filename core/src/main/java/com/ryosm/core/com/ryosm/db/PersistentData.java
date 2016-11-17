package com.ryosm.core.com.ryosm.db;

/**
 * @description Class used to memory cache management.
 */

public class PersistentData {

    private static final String TAG = PersistentData.class.getSimpleName();

    // Persistent Data
    private static PersistentData persistentData;
    private SQLiteCoreHelper databaseHelper;
    private String username;
    private String userToken;
    private String csrf;

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

//    public void initDatabaseHelper(Context context) {
//        databaseHelper = new SQLiteCoreHelper(context);
//    }


    public SQLiteCoreHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }
}
