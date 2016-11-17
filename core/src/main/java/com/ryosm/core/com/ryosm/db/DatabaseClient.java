package com.ryosm.core.com.ryosm.db;

import android.database.sqlite.SQLiteDatabase;

import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.db.tables.UserTable;
import com.ryosm.core.com.ryosm.objects.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by revs on 16-11-2016.
 */

public class DatabaseClient {

    private ExecutorService executor;
    private SQLiteCoreHelper dbHelper;
    private SQLiteDatabase db;
    private Core core;

    public DatabaseClient(final Core core) {
        this.core = core;
        executor = Executors.newCachedThreadPool();
        dbHelper = new SQLiteCoreHelper(core);
        db = dbHelper.getWritableDatabase();
    }

    public void clear() throws Exception {
        dbHelper.onClear(db);
    }

    public void saveUser(final User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                UserTable.insert(db, user);
            }
        });
    }

    public void removeUser(final String identifier) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                UserTable.delete(db, identifier);
            }
        });
    }

    public void printUsers() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                UserTable.print(db);
            }
        });
    }
}
