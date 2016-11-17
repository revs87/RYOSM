package com.ryosm.core.com.ryosm.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.ryosm.core.com.ryosm.db.tables.shared.TableUtils;
import com.ryosm.core.com.ryosm.objects.User;
import com.ryosm.core.com.ryosm.utils.L;

/**
 * Created by revs on 16-11-2016.
 */

public class UserTable {
    private static final String TABLE_NAME = "user";
    private static final String IDENTIFIER = "identifier";
    private static final String PUBLIC_KEY = "public_key";

    private static final String CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    " (" +
                    IDENTIFIER + " TEXT, " +
                    PUBLIC_KEY + " TEXT " +
                    ");";

    private static final String SELECT =
            "SELECT * FROM " + TABLE_NAME + ";";

    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME +
                    " VALUES ( NULL, NULL )";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE);
        database.execSQL(INSERT);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // No upgrades yet
    }

    public static void onClear(SQLiteDatabase database) {
        TableUtils.onClear(database, TABLE_NAME);
    }

    public static void insert(final SQLiteDatabase database, final User user) {
        if (user == null
                || TextUtils.isEmpty(user.getIdentifier())
                || TextUtils.isEmpty(user.getPublicKey())
                ) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(IDENTIFIER, user.getIdentifier());
        values.put(PUBLIC_KEY, user.getPublicKey());


        database.beginTransaction();
        try {
            if (values != null) {
                database.insert(TABLE_NAME, null, values);
                database.setTransactionSuccessful();
            }
        } finally {
            database.endTransaction();
        }
    }

    public static void update(final SQLiteDatabase database, final User user) {
        if (user == null
                || TextUtils.isEmpty(user.getIdentifier())
                || TextUtils.isEmpty(user.getPublicKey())
                ) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(IDENTIFIER, user.getIdentifier());
        values.put(PUBLIC_KEY, user.getPublicKey());

        database.update(TABLE_NAME, values, IDENTIFIER + " = ?", new String[]{user.getIdentifier() + ""});
    }

    public static void delete(final SQLiteDatabase database, final String identifier) {
        if (TextUtils.isEmpty(identifier)) {
            return;
        }

        database.delete(TABLE_NAME, IDENTIFIER + " = ?", new String[]{identifier + ""});
    }

    public static void print(SQLiteDatabase database) {
        final Cursor cur = database.rawQuery(SELECT, null);
        if (cur != null && cur.moveToFirst()) {
            int i = 0;
            L.d("print", "UserDB");
            do {
                String identifier = cur.getString(0);
                String publicKey = cur.getString(1);

                L.d("print", "User:" + i++ + ", ID:" + identifier + ", PK:" + publicKey);
            } while (cur.moveToNext());
        }
        if (cur != null) {
            cur.close();
        }
    }
}

