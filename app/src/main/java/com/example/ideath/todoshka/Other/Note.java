package com.example.ideath.todoshka.Other;


public class Note  {

    public static final String TABLE_NAME = "todos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_THEMA = "thema";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CHECKEN = "checken";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_HOURS = "hours";
    public static final String COLUMN_MINUTE = "minute";
    public static final String COLUMN_TIMEINMILLIS = "timeInMillis";

    public static final String CREATE_TABLE =
            "CREATE TABLE " +  TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_THEMA + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_CHECKEN + " NUMERIC,"
                    + COLUMN_DATE + " DATETIME DEFAULT CURRENT_DATE,"
                    + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIME,"
                    + COLUMN_YEAR + " INTEGER,"
                    + COLUMN_MONTH + " INTEGER,"
                    + COLUMN_DAY + " INTEGER,"
                    + COLUMN_HOURS + " INTEGER,"
                    + COLUMN_MINUTE + " INTEGER,"
                    + COLUMN_TIMEINMILLIS + " INTEGER"
                    + ")";

    private long id;
    private String thema;
    private String description;
    private boolean checken;
    private String date;
    private String time;
    private int year;
    private int month;
    private int day;
    private int hours;
    private int minute;
    private long timeInMillis;


    public Note(){}


    public Note(long id,String thema,String description,boolean checken,String date,String time
            ,int year,int month,int day,int hours,int minute,long timeInMillis){
        this.id = id;
        this.thema = thema;
        this.description = description;
        this.checken = checken;
        this.date = date;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.minute = minute;
        this.timeInMillis = timeInMillis;
    }


    public long getTimeInMillis() {
        return timeInMillis;
    }


    public long getId() {
        return id;
    }

    public String getThema() {
        return thema;
    }

    public String getDescription() {
        return description;
    }

    public boolean isChecken() {
        return checken;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
    public int getHours(){return hours;}

    public int getMinute() {
        return minute;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChecken(boolean checken) {
        this.checken = checken;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }


}
