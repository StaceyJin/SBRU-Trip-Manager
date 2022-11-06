package com.example.sbru;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqlLiteDatabase;

    private static final String DATABASE_NAME = "Sbru.db";
    private static final String TRIP_TABLE_NAME = "trips_db";
    // private static final String PAYMENT_TABLE_NAME = "payments.db";

    private static final String ID = "_id";
    private static final String DATE = "Date";
    private static final String PEOPLE = "People";
    private static final String RESORT = "Resort";
    private static final String ROOMS = "Rooms";
    //private static final String COST = "Cost";

    private static final String CREATE_TRIP_TABLE = "CREATE TABLE " + TRIP_TABLE_NAME +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DATE + " VARCHAR(255)," +
            PEOPLE + " VARCHAR(10)," +
            RESORT  + " VARCHAR(255)," +
            ROOMS + " VARCHAR(10));";
    //   COST + " DOUBLE);";

    private static final String CREATE_TRIP_TABLE_UPD = "CREATE TABLE " + TRIP_TABLE_NAME +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DATE + " VARCHAR(255)," +
            PEOPLE + " VARCHAR(10)," +
            RESORT  + " VARCHAR(255)," +
            ROOMS + " VARCHAR(10));";
    //   COST + " DOUBLE);";

    private static final String DROP_TRIP_TABLE = "DROP TABLE IF EXISTS " + TRIP_TABLE_NAME;

    private static final String SELECT_ALL_TRIP = "SELECT * FROM " + TRIP_TABLE_NAME;

    private static final int VERSION_NUMBER = 1;
    private Context context;

    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TRIP_TABLE);
            showToast("Called onCreate()");

        }catch (Exception e){
            showToast("Exception: " + e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            sqlLiteDatabase.execSQL(DROP_TRIP_TABLE);
            showToast("Called dropTable()");
        }catch (Exception e){
            showToast("Exception: " + e);
        }

        try {
            sqlLiteDatabase.execSQL(CREATE_TRIP_TABLE_UPD);
            showToast("Called CREATE_TRIP_TABLE_UPD");
        }catch (Exception e){
            showToast("Exception: " + e);
        }

    }

    public long insertTable(String date, String people, String resort, String rooms){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", date);
        contentValues.put("People", people);
        contentValues.put("Resort", resort);
        contentValues.put("Rooms", rooms);
        // contentValues.put("Cost", Double.parseDouble(cost));

        showToast(contentValues.toString());
        sqlLiteDatabase = this.getWritableDatabase();
        long rowID = sqlLiteDatabase.insert(TRIP_TABLE_NAME, null, contentValues);

        //sqLiteDatabase.close();
        return rowID;
    }

    public Cursor displayAllData(){
        sqlLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqlLiteDatabase.rawQuery(SELECT_ALL_TRIP, null);
        //sqLiteDatabase.close();
        return cursor;
    }

    public boolean updateData(String sId, String date, String people, String resort, String rooms){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", sId);
        contentValues.put("Date", date);
        contentValues.put("People", people);
        contentValues.put("Resort", resort);
        contentValues.put("Rooms", rooms);
        //contentValues.put("Cost", Double.parseDouble(cost));

        showToast(contentValues.toString());
        sqlLiteDatabase = this.getWritableDatabase();
        try {
            int rowNumUpd = sqlLiteDatabase.update(TRIP_TABLE_NAME, contentValues, "_id = ?", new String[]{sId});

            if (rowNumUpd >= 0) {

                return true;
            } else {
                return false;
            }
        } catch(Exception e){
            showToast("Exception: " + e);
            return false;
        }
    }

    public boolean deleteData(String sId){
        sqlLiteDatabase = this.getWritableDatabase();

        try{
            int delRowNo = sqlLiteDatabase.delete(TRIP_TABLE_NAME, "_id = ?", new String[]{sId});

            if (delRowNo >= 0) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e){
            showToast("Exception: " + e);
            return false;
        }

    }

    private void showToast(String message){
        Toast toast;
        toast = Toast.makeText(this.context, message, Toast.LENGTH_LONG);
        toast.show();
    }
}