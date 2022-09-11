package com.example.plogging.sqlite_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteDBHelper extends SQLiteOpenHelper {
    public SqliteDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createSql = "create table if not exists my_plogging (" +
                "_id integer primary key autoincrement," +
                "location varchar(256) not null," +
                "date varchar(30) not null);";

        database.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        String upgradeSpl = "drop table if exists test;";
        database.execSQL(upgradeSpl);

        onCreate(database); // 다시 생성
    }
}
