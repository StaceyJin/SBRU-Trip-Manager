package com.example.sbru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Cancellation extends AppCompatActivity implements View.OnClickListener{
    private TextView dateTxt;
    private TextView numPeopleTxt;
    private TextView resortTxt;
    private TextView numRoomTxt;
    private EditText keyId;
    private Button confirmBtn;
    private Button displayBtn;
    private Button helpBtn;
    private DataHelper helper;
    private Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);
        dateTxt = findViewById(R.id.txtViewDate);
        numPeopleTxt = findViewById(R.id.txtViewNumP);
        resortTxt = findViewById(R.id.txtViewResort);
        numRoomTxt = findViewById(R.id.txtViewNumRoom);
        keyId = findViewById(R.id.editTextRNum);
        confirmBtn = findViewById(R.id.confirmCancel);
        displayBtn = findViewById(R.id.displayReservations);
        helpBtn = findViewById(R.id.btnHelp);
        sharedPreferences = getSharedPreferences("tripDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        helper = new DataHelper(this);
        String savedDate = sharedPreferences.getString("keyDate","no data");
        dateTxt.setText(savedDate);
        String savedPeopleNumber = sharedPreferences.getString("keyPeople","no data");
        numPeopleTxt.setText(savedPeopleNumber);
        String savedResort = sharedPreferences.getString("keyResort","no data");
        resortTxt.setText(savedResort);
        String savedRoomNumber = sharedPreferences.getString("keyRooms","no data");
        numRoomTxt.setText(savedRoomNumber);
        confirmBtn.setOnClickListener(Cancellation.this);
        displayBtn.setOnClickListener(Cancellation.this);
        intent = new Intent(Cancellation.this, MainActivity.class);
        helpBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                if (me.getAction()==MotionEvent.ACTION_DOWN) {
                    onHelpDisplay();
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.confirmCancel){
            onCancelReservation();
        }else if(v.getId() == R.id.displayReservations){
            displayAllData();
        } else if (v.getId() == R.id.backToMain){
            startActivity(intent);
        }/*else{
            onHelpDisplay();
        }*/
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
    private void displayAllData(){
        Cursor resultSet = helper.displayAllData();
        if (resultSet.getCount() == 0) {
            showToast("Display error: No row selected");
            showData("Error", "No data found");
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
            //showToast(stringBuffer.toString());
            showData("Summary", stringBuffer.toString());
        }
    }
    private void clearValues(){
        dateTxt.setText("");
        numPeopleTxt.setText("");
        resortTxt.setText("");
        numRoomTxt.setText("");
        keyId.setText("");
    }
    public void onHelpDisplay() {
        AlertDialog.Builder alBuilder;
        alBuilder=new androidx.appcompat.app.AlertDialog.Builder(Cancellation.this);
        alBuilder.setIcon(R.drawable.question_icon1);
        alBuilder.setTitle("Help");
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("Touch at the areas you want to make an entry, then select the value.\n\n" );
        sbuffer.append("Make sure to save after selecting your values. Pages without the \"Save\" button will automatically save upon selection.\n\n" );
        sbuffer.append("Navigate between screens by either using navigational buttons provided or going back on the app.\n\n");
        sbuffer.append( "If your payment isn't registered, make sure to double-check the values you've entered.\n\n");
        sbuffer.append("Be sure when you cancel a reservation: once done, it can't be recovered!\n");
        alBuilder.setMessage(sbuffer.toString());
        alBuilder.setCancelable(false);
        alBuilder.setPositiveButton("Exit Screen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();
    }
    //@Override
    public void onCancelReservation() {
        AlertDialog.Builder alBuilder;
        alBuilder=new AlertDialog.Builder(Cancellation.this);
        alBuilder.setIcon(R.drawable.question_icon1);
        alBuilder.setTitle("Alert");
        alBuilder.setMessage("Are you sure you want to cancel this reservation?");
        alBuilder.setCancelable(false);
        alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
                showToast("Deleting data...");
                String resId = keyId.getText().toString();
                // String reservId = sharedPreferences.getString("keyId", resId);
                // showToast(reservId);
                boolean isDeleted = helper.deleteData(resId);
                String reservationId = keyId.getText().toString();
                if(isDeleted){
                    showToast("Successfully deleted reservation: "+reservationId);
                    //displayAllData();
                }else{
                    showToast("Failed to delete reservation: "+reservationId);
                }
                clearValues();
                displayAllData();
            }
        });
        alBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();
    }
}