package com.example.user.proyecto_final.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.proyecto_final.R;
import com.example.user.proyecto_final.util.FileUtils;

public class DbHelper extends SQLiteOpenHelper {
    private Resources resources;
    private static final String DATABASE_NAME = "sjl_app.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context, Resources resources) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.resources = resources;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FileUtils.readRawFile(resources, R.raw.create));
        db.execSQL(FileUtils.readRawFile(resources, R.raw.initial_data));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(FileUtils.readRawFile(resources, R.raw.destroy_the_world));
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDowngrade(db, oldVersion, newVersion);
    }
}
