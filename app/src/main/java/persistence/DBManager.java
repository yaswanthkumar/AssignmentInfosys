package persistence;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import static persistence.DatabaseHelper.DB_NAME;

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
    public void insert(String title, String description, String url) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TITLE, title);
        contentValue.put(DatabaseHelper.DESCRIPTION, description);
        contentValue.put(DatabaseHelper.IMAGE_URL, url);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
            String[] cols = new String[]{DatabaseHelper._ID, DatabaseHelper.TITLE, DatabaseHelper.DESCRIPTION, DatabaseHelper.IMAGE_URL};
            Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, cols, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }

    //it check data base exists or not
    public boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            File database=context.getDatabasePath(DB_NAME);

            if (database.exists()) {

                Log.i("Database", "Found");

                String myPath = database.getAbsolutePath();

                Log.i("Database Path", myPath);

                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            } else {

                // Database does not exist so copy it from assets here
                Log.i("Database", "Not Found");

            }

        } catch(SQLiteException e) {

            Log.i("Database", "Not Found");

        } finally {

            if(checkDB != null) {

                checkDB.close();

            }

        }

        return checkDB != null ? true : false;
    }




    //retrive all data from table in this cursor object is used th retrive the data row by row
    public ArrayList<ItemDatabaseHelper> readAllItems() {
        ArrayList<ItemDatabaseHelper> list = new ArrayList<ItemDatabaseHelper>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] cols = new String[]{DatabaseHelper.TITLE, DatabaseHelper.DESCRIPTION, DatabaseHelper.IMAGE_URL};

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, cols, null, null, null, null,null);

        if (cursor != null ) {
            while (cursor.moveToNext()) {
                ItemDatabaseHelper item = new ItemDatabaseHelper();
                //item.id = cursor.getString(0);
                item.Title = cursor.getString(0);
                item.Description = cursor.getString(1);
                item.url = (String) cursor.getString(2);
                list.add(item);
            }
        }
        return list;
        }

    //delete data from table
    public void delete() {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String deleteQueries = "DELETE FROM " + DatabaseHelper.TABLE_NAME;
            db.execSQL(deleteQueries);
            //db.close();
    }


}
