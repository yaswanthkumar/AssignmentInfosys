package com.vaibhavapps.cpwslogs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper  {

    //Table name
    public static final String TABLE_NAME = "SHED_ENTRY";

    //Table columns
    public static final String _ID = "_id";
    public static final String SHED_LOG_ID = "shed_log";
    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";
    public static final String AMMONIUM = "ammonium";
    public static final String TREATMENT = "treatment";
    public static final String DATE = "date";



    //Database Information
    static final String DB_NAME = "SHED.DB";

    //Database version
    static final int DB_VERSION = 1;

    //Table query

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SHED_LOG_ID + " TEXT, " + TREATMENT + " TEXT, " + DATE + " NUMERIC, " + TEMPERATURE + " NUMERIC, " + HUMIDITY + " NUMERIC, " + AMMONIUM + " NUMERIC)";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
