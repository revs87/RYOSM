package com.ryosm.core.com.ryosm.db.tables.shared;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.ryosm.core.com.ryosm.utils.L;

/**
 * Created by revs on 16-11-2016.
 */

public class TableUtils {

    public static void onClear(SQLiteDatabase database, final String TABLE_NAME) {
        SQLiteStatement stmt = database.compileStatement(
                "DELETE FROM " + TABLE_NAME + ";");
        stmt.executeUpdateDelete();
        stmt.close();

        final Cursor c = database.rawQuery("SELECT * from " + TABLE_NAME + ";", null);
        final int extras = c.getCount();
        L.v("Dc/TableUtils", "deleted " + TABLE_NAME + ", how many left:" + extras);
        c.close();
    }
    
}
