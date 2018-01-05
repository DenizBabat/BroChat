package com.babat.deniz.brochat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by deniz on 05.01.2018.
 */

public class SQLDatabase extends SQLiteOpenHelper {
    private String user = new String();
    private String email = new String();

    private static final String dbname = "dbdatas.db";
    public static final int DATABASE_VER= 1;
    public static final String TABLE_NAME = "DATAS";

    public static final  String  ROW_ID = "id"; // row id
    public static final  String EMAIL = "email"; //  gmail
    public static final  String USER = "user"; //user


    public SQLDatabase(Context context){
        super(context, dbname, null, DATABASE_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EMAIL + " TEXT NOT NULL," +
                USER + " TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXITS %s", TABLE_NAME));
        onCreate(db);
    }

    public boolean add(String user, String email){
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(EMAIL, email.trim());
            cv.put(USER, user.trim());

            db.insert(TABLE_NAME, null, cv);

            db.close();
            return true;
        }catch (Exception ex){
            System.out.println("exception write");
        }
        return false;
    }

    public List<Stack>  getALl(){
        List<Stack> datas  = new ArrayList<Stack>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String column[] = {ROW_ID, EMAIL, USER};
            Cursor cursor = db.query(TABLE_NAME, column, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Stack<String> stack = new Stack<>();
                stack.add(cursor.getString(1));
                stack.add(cursor.getString(2));
                datas.add(stack);
            }
        }catch (Exception ex){
            System.out.println("exception when is found");
        }
        return datas;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
