package com.example.ideath.todoshka.Other;


import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLBases extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 2;

    private SQLiteDatabase db;
    // private static  String DB_PATH;
    // название таблицы

    //private Context myContext;
    public SQLBases(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        myDatabase(db,oldVersion,newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        myDatabase(db,oldVersion,newVersion);
    }

    private void myDatabase(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        onCreate(db);
    }


    public void insertTodo(Note note){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Note.COLUMN_THEMA,note.getThema());
        values.put(Note.COLUMN_DESCRIPTION,note.getDescription());
        values.put(Note.COLUMN_CHECKEN,note.isChecken());
        values.put(Note.COLUMN_DATE,note.getDate());
        values.put(Note.COLUMN_TIME,note.getTime());
        values.put(Note.COLUMN_YEAR,note.getYear());
        values.put(Note.COLUMN_MONTH,note.getMonth());
        values.put(Note.COLUMN_DAY,note.getDay());
        values.put(Note.COLUMN_HOURS,note.getHours());
        values.put(Note.COLUMN_MINUTE,note.getMinute());
        values.put(Note.COLUMN_TIMEINMILLIS,note.getTimeInMillis());

        db.insert(Note.TABLE_NAME,null,values);

        db.close();
    }


    public Note getTodo(long id){
        db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Note.TABLE_NAME + " WHERE "
                + Note.COLUMN_ID + "=?", new String[]{String.valueOf(id)} );

        Note note = null;
        if(cursor.moveToFirst()) {
            String thema = cursor.getString(cursor.getColumnIndex(Note.COLUMN_THEMA));
            String description = cursor.getString(cursor.getColumnIndex(Note.COLUMN_DESCRIPTION));
            boolean checken = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_CHECKEN))==1;
            String date = cursor.getString(cursor.getColumnIndex(Note.COLUMN_DATE));
            String time = cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME));
            int year = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_YEAR));
            int month = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MONTH));
            int day = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_DAY));
            int hours = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HOURS));
            int minute = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MINUTE));
            long timeinmillis = cursor.getLong(cursor.getColumnIndex(Note.COLUMN_TIMEINMILLIS));
            note = new Note(id,thema,description,checken,date,time,year,month,day,hours,minute,timeinmillis);
        }
        cursor.close();
        db.close();

        return note;
    }


    public List<Note> getAllTodo(int i){
        db = this.getReadableDatabase();
        List<Note> notes = new ArrayList<>();

        Cursor cursor = null;
        if(i == 1) {
            cursor = db.rawQuery("SELECT * FROM " + Note.TABLE_NAME + " WHERE  " + Note.COLUMN_CHECKEN + " = " + i, null);
        }else if(i == 0){
            cursor = db.rawQuery("SELECT * FROM " + Note.TABLE_NAME + " WHERE " + Note.COLUMN_CHECKEN + " = " + i, null);
        }

        if(cursor.moveToFirst()){

            do{
                int id = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID));
                String thema = cursor.getString(cursor.getColumnIndex(Note.COLUMN_THEMA));
                String description = cursor.getString(cursor.getColumnIndex(Note.COLUMN_DESCRIPTION));
                boolean checken = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_CHECKEN))==1;
                String date = cursor.getString(cursor.getColumnIndex(Note.COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME));
                int year = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_YEAR));
                int month = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MONTH));
                int day = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_DAY));
                int hours = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HOURS));
                int minute = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MINUTE));
                int timeinmillis = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_TIMEINMILLIS));
                notes.add(new Note(id,thema,description,checken,date,time,year,month,day,hours,minute,timeinmillis));

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }


    public void updateTodo(Note note){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_THEMA,note.getThema());
        values.put(Note.COLUMN_DESCRIPTION,note.getDescription());
        values.put(Note.COLUMN_CHECKEN,note.isChecken());
        values.put(Note.COLUMN_DATE,note.getDate());
        values.put(Note.COLUMN_TIME,note.getTime());
        values.put(Note.COLUMN_YEAR,note.getYear());
        values.put(Note.COLUMN_MONTH,note.getMonth());
        values.put(Note.COLUMN_DAY,note.getDay());
        values.put(Note.COLUMN_HOURS,note.getHours());
        values.put(Note.COLUMN_MINUTE,note.getMinute());
        values.put(Note.COLUMN_TIMEINMILLIS,note.getTimeInMillis());

        db.update(Note.TABLE_NAME,values,
                Note.COLUMN_ID + "=" + note.getId(),null);
        db.close();
    }

    public void deleteTodo(long id){
        db = this.getWritableDatabase();

        db.delete(Note.TABLE_NAME,Note.COLUMN_ID + "= ?",
                                    new String[]{String.valueOf(id)});
        db.close();
    }

    public int getTodosCount(int i){
        db = this.getReadableDatabase();
        Cursor cursor = null;
        if(i == 1) {
            cursor = db.rawQuery("SELECT * FROM " + Note.TABLE_NAME + " WHERE " +
                    Note.COLUMN_CHECKEN + " = " + i, null);
        }else if (i == 0){
            cursor = db.rawQuery("SELECT * FROM " + Note.TABLE_NAME + " WHERE " +
                    Note.COLUMN_CHECKEN + " = " + i, null);
        }
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

}
