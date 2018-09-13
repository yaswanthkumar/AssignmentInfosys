package com.vaibhavapps.cpwslogs;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager  {
        private DatabaseHelper dbHelper;

        private Context context;

        private SQLiteDatabase database;

        public DBManager(Context c) {
        context = c;
        }

        public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
        }

        public void close() {
        dbHelper.close();
        }
    public void insert(String shed_log, String temperature, String humidity, String ammonia, String treatment, String date) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SHED_LOG_ID, shed_log);
        contentValue.put(DatabaseHelper.TEMPERATURE, temperature);
        contentValue.put(DatabaseHelper.HUMIDITY, humidity);
        contentValue.put(DatabaseHelper.AMMONIUM, ammonia);
        contentValue.put(DatabaseHelper.TREATMENT, treatment);
        contentValue.put(DatabaseHelper.DATE, date);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }
    public Cursor fetch() {
            String[] cols = new String[]{DatabaseHelper._ID, DatabaseHelper.SHED_LOG_ID, DatabaseHelper.TEMPERATURE, DatabaseHelper.HUMIDITY, DatabaseHelper.AMMONIUM, DatabaseHelper.TREATMENT, DatabaseHelper.DATE};
            Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, cols, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }
    public ArrayList<LogDataHelper> readAllItems(String ids) {
        ArrayList<LogDataHelper> items = new ArrayList<LogDataHelper>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] cols = new String[]{DatabaseHelper.SHED_LOG_ID, DatabaseHelper.TEMPERATURE, DatabaseHelper.HUMIDITY, DatabaseHelper.AMMONIUM, DatabaseHelper.TREATMENT, DatabaseHelper.DATE};

        String selection = DatabaseHelper.SHED_LOG_ID + " = ?";
        String[] selectionArgs = {ids};

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, cols, selection, selectionArgs, null, null,null);

        if (cursor != null ) {
            while (cursor.moveToNext()) {
                LogDataHelper item = new LogDataHelper();
                item.id = cursor.getString(0);
                item.temperature = cursor.getString(1);
                item.humidity = cursor.getString(2);
                item.ammonium = cursor.getString(3);
                item.treatment = cursor.getString(4);
                item.date = cursor.getString(5);
                items.add(item);
            }
        }
        return items;
        }
    public void delete() {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String deleteQueries = "DELETE FROM " + DatabaseHelper.TABLE_NAME;
            db.execSQL(deleteQueries);
            db.close();
    }
}
