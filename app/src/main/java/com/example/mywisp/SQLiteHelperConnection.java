package com.example.mywisp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mywisp.utilities.Tables;

import androidx.annotation.Nullable;

public class SQLiteHelperConnection extends SQLiteOpenHelper {

    public SQLiteHelperConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Tables.CREATE_CUSTOMERS_TABLE);
        db.execSQL(Tables.CREATE_SERVICES_TABLE);
        db.execSQL(Tables.CREATE_INSTALATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Tables.CUSTOMERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.SERVICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Tables.INSTALATIONS_TABLE);
    }
}
