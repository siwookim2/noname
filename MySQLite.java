package com.example.owner.hanieum_project.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.owner.hanieum_project.Activity.MainActivity;

/**
 * Created by samsung on 2016-08-03.
 */
public class MySQLite extends SQLiteOpenHelper {

    String sql = "create table if not exists member" +
            "(seq integer PRIMARY KEY  autoincrement," +
            " ID text NOT NULL," +
            " Password text NOT NULL," +
            " Email text NOT NULL," +
            " CP text NOT NULL," +
            " Address text NOT NULL," +
            " cooler integer," +
            " heater integer," +
            " dehumidifier integer," +
            " humidifier integer);";

    public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertDB(String name, String ID, String Password, String Email, String CP, String Address, boolean cooler, boolean heater, boolean dryer, boolean humidifier) {

        //DB내부의 Table에 데이터를 추가하기 위한 메소드
        ContentValues values = new ContentValues();
        SQLiteDatabase db = MainActivity.DB.getWritableDatabase();

        values.put("ID", ID);
        values.put("Password", Password);
        values.put("Email", Email);
        values.put("CP", CP);
        values.put("Address", Address);
        values.put("cooler", cooler);
        values.put("heater", heater);
        values.put("dehumidifier", dryer);
        values.put("humidifier", humidifier);

        db.insert(name, null, values);
    }
}