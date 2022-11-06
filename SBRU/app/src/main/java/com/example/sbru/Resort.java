package com.example.sbru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Resort extends AppCompatActivity implements View.OnClickListener {
    private Button cancun, miami, puntaCana, santaMaria, losAngeles;
    private Intent intent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort);

        cancun = findViewById(R.id.btnCancun);
        miami = findViewById(R.id.btnMiami);
        puntaCana = findViewById(R.id.btnPuntaCana);
        santaMaria = findViewById(R.id.btnSantaMaria);
        losAngeles = findViewById(R.id.btnLosAngeles);

        cancun.setOnClickListener(Resort.this);
        miami.setOnClickListener(Resort.this);
        puntaCana.setOnClickListener(Resort.this);
        santaMaria.setOnClickListener(Resort.this);
        losAngeles.setOnClickListener(Resort.this);

        intent = new Intent(Resort.this, ConfirmResort.class);

        sharedPreferences = getSharedPreferences("tripDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnCancun){
            intent.putExtra("name", "cancun");
            String resort = "Cancun";
            editor.putString("keyResort", resort);
            editor.commit();
            showToast("Data stored successfully: " + resort);

        } else if(v.getId()==R.id.btnMiami) {
            intent.putExtra("name", "miami");
            String resort = "Miami";
            editor.putString("keyResort", resort);
            editor.commit();
            showToast("Data stored successfully: " + resort);
        } else if(v.getId()==R.id.btnPuntaCana) {
            intent.putExtra("name", "punta cana");
            String resort = "Punta Cana";
            editor.putString("keyResort", resort);
            editor.commit();
            showToast("Data stored successfully: " + resort);
        }else if(v.getId()==R.id.btnSantaMaria) {
            intent.putExtra("name", "santa maria");
            String resort = "Santa Maria";
            editor.putString("keyResort", resort);
            editor.commit();
            showToast("Data stored successfully: " + resort);
        }else if(v.getId()==R.id.btnLosAngeles) {
            intent.putExtra("name", "los angeles");
            String resort = "Los Angeles";
            editor.putString("keyResort", resort);
            editor.commit();
            showToast("Data stored successfully: " + resort);
        }
        startActivity(intent);

    }

    public void showToast (String s1){
        Toast tst;
        tst = Toast.makeText(this, s1, Toast.LENGTH_LONG);
        tst.show();
    }


}
