package com.example.sbru;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;

public class Payment extends AppCompatActivity implements View.OnClickListener {

    private String owner;
    private String cardNumber;
    private String expiration;
    private String cvc;
    private EditText editOwner;
    private EditText editNumber;
    private EditText editExpiration;
    private EditText editCvc;
    private Intent intent;
    private Button process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        editOwner = findViewById(R.id.editOwner);
        editNumber = findViewById(R.id.editCardNum);
        editExpiration = findViewById(R.id.editExpCard);
        editCvc = findViewById(R.id.editcvc);
        process = findViewById(R.id.process);
        process.setOnClickListener(Payment.this);

        intent = new Intent(Payment.this, Reservation.class);
    }

    public boolean validateNullInputs(){
        owner = editOwner.getText().toString();
        cardNumber = editNumber.getText().toString();
        expiration = editExpiration.getText().toString();
        cvc = editCvc.getText().toString();

        if(TextUtils.isEmpty(owner) || TextUtils.isEmpty(cardNumber)|| TextUtils.isEmpty(expiration)|| TextUtils.isEmpty(cvc)){
            return false;
        }
        else{
            return true;
        }


    }

    public boolean validateInputPattern(){
        owner = editOwner.getText().toString();
        cardNumber = editNumber.getText().toString();
        expiration = editExpiration.getText().toString();
        cvc = editCvc.getText().toString();

        String nameRegex = "[a-zA-Z]{2,}\\s[a-zA-Z]{2,}";
        String cardNumRegex = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
        String expirationRegex = "([1-9]|1[012])/([0-9][0-9])";
        String cvcRegex = "[0-9][0-9][0-9]";

        if(owner.matches(nameRegex) && cardNumber.matches(cardNumRegex) && expiration.matches(expirationRegex) && cvc.matches(cvcRegex)){
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        boolean resultNull = validateNullInputs();
        boolean resultValid = validateInputPattern();

        if(resultNull == true && resultValid == true){
            startActivity(intent);
        }
        else if(resultNull == false){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("ERROR! Missing inputs! Please fill all fields.");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }


                    });
            AlertDialog alert = builder.create();
            alert.show();



        }

        else if(resultValid == false){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("ERROR! Invalid inputs! Please check entries.");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }


                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
