package com.ryosm.core.com.ryosm.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.db.tables.UserTable;

public class SQLiteCoreHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 100;
    public static final String DATABASE_NAME = "core.db";

    public SQLiteCoreHelper(final Core core) {
        super(core.getService(), DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        UserTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        UserTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);

    }

    public void onClear(SQLiteDatabase sqLiteDatabase) {
        UserTable.onClear(sqLiteDatabase);
    }


}
