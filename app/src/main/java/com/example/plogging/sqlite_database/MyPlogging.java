package com.example.plogging.sqlite_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyPlogging {
    private SqliteDBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public MyPlogging(Context context) {
        this.context = context;
        dbHelper = new SqliteDBHelper(context, "my_plogging.db", null, 1);
        database =dbHelper.getWritableDatabase();
    }

    public void createDB(){
        dbHelper.onCreate(database);
    }

    public List<MyPloggingData> getMyPlogging(int year, int month){
        List<MyPloggingData> list = new ArrayList<>();

        String findDate = "" + year + "-" + month + "%"; // year-month로 시작하는 거
        System.out.println("findata: " + findDate);
        String selectSql = "select * from my_plogging where date like"
                + "'" + findDate + "'" + ";";

        Cursor c = database.rawQuery(selectSql, null);
        while (c.moveToNext()){
            String date = c.getString(1);
            String location = c.getString(2);
            MyPloggingData data = new MyPloggingData(date, location);
            list.add(data);
        }
        return list;
    }

    public void inserData(String date, String location){
        String insertSql = "insert into my_plogging('date', 'location') values('" + date + "'"
                + "," + "'" + location + "'" + ");";
        database.execSQL(insertSql);
    }

    public void deleteAll(){
        String deleteSql = "delete from my_ploggin;";
    }
}
