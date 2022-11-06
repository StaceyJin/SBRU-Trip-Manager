package com.example.sbru;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Reservation extends AppCompatActivity implements View.OnClickListener {

    private Intent intentMain;
    private Intent intentCancel;
    private Button main;
    private Button cancel;
    private Button save;
    private TextView dateTxt;
    private TextView numPeopleTxt;
    private TextView resortTxt;
    private TextView numRoomTxt;

    private DataHelper dataHelper;
    private SQLiteDatabase sqLiteDatabase;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        main = findViewById(R.id.backToMain);
        main.setOnClickListener(Reservation.this);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(Reservation.this);
        save = findViewById(R.id.btnSaveToTable);
        save.setOnClickListener(Reservation.this);

        dateTxt = findViewById(R.id.txtViewResDate);
        numPeopleTxt = findViewById(R.id.txtViewNumP);
        resortTxt = findViewById(R.id.txtViewResort);
        numRoomTxt = findViewById(R.id.txtViewNumRoom);

        dataHelper = new DataHelper(this);
        sqLiteDatabase = dataHelper.getWritableDatabase();

        intentMain = new Intent(Reservation.this, MainActivity.class);

        intentCancel = new Intent(Reservation.this, Cancellation.class);

        sharedPreferences = getSharedPreferences("tripDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String date = sharedPreferences.getString("keyDate", "no data");
        dateTxt.setText("Reservation Date: " + date);
        String people = sharedPreferences.getString("keyPeople", "no data");
        numPeopleTxt.setText("Number of People: " + people);
        String resort = sharedPreferences.getString("keyResort", "no data");
        resortTxt.setText("Your Resort: " + resort);
        String rooms = sharedPreferences.getString("keyRooms", "no data");
        numRoomTxt.setText("Number of Rooms: " + rooms);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnSaveToTable){
            //String sId =
            String sDate = sharedPreferences.getString("keyDate", "no data");
            String sPeople = sharedPreferences.getString("keyPeople", "no data");
            String sResort = sharedPreferences.getString("keyResort", "no data");
            String sRooms = sharedPreferences.getString("keyRooms", "no data");
            long rowId = dataHelper.insertTable(sDate, sPeople, sResort, sRooms);
            if (rowId == -1){
                showToast("Row insertion failed");
            } else{
                showToast("Row: " + rowId + " insertion worked");
                displayAllData();
            }


        }else if(v.getId()==R.id.backToMain) {
            startActivity(intentMain);
        } else if(v.getId()==R.id.cancel) {
            startActivity(intentCancel);
        }
    }

    private void displayAllData(){

        Cursor resultSet = dataHelper.displayAllData();
        if (resultSet.getCount() == 0) {
            showToast("Display error: No row selected");
            showData("Error", "No data found");
            return;
        }
        else {
            StringBuffer stringBuffer = new StringBuffer();

            while (resultSet.moveToNext()) {
                stringBuffer.append("\nID: " + resultSet.getString(0));
                stringBuffer.append("\nDate: " + resultSet.getString(1));
                stringBuffer.append("\nPeople: " + resultSet.getString(2));
                stringBuffer.append("\nResort: " + resultSet.getString(3));
                stringBuffer.append("\nRooms: " + resultSet.getString(4) + "\n\n");
            }
            showToast(stringBuffer.toString());
            showData("Summary", stringBuffer.toString());
        }
    }
    private void showData(String title, String data) {

        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle(title);
        alBuilder.setMessage(data);
        alBuilder.setCancelable(true);
        alBuilder.show();

    }

    public void showToast(String s1) {
        Toast tst;
        tst = Toast.makeText(this, s1, Toast.LENGTH_LONG);
        tst.show();
    }
}


