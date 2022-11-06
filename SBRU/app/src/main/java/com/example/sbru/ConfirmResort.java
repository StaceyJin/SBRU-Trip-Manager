package com.example.sbru;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmResort extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView imageView;
    private TextView txtView;
    private Button btnPay;
    private Intent intent;
    private Spinner spinner;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_resort);

        imageView = findViewById(R.id.imageViewId);
        txtView = findViewById(R.id.txtViewId);
        btnPay=findViewById(R.id.confirm);
        btnPay.setOnClickListener(this);

        Bundle bundle;
        bundle = getIntent().getExtras();
        if(bundle!=null){
            String resortName = bundle.getString("name");
            showDetails(resortName);
        }

        intent = new Intent(ConfirmResort.this, Payment.class);

        spinner = findViewById(R.id.selectNumR);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numRooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        int index = spinner.getSelectedItemPosition();

        sharedPreferences = getSharedPreferences("tripDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void showDetails(String rName) {
        if (rName.equals("cancun")) {
            txtView.setText(R.string.cancun_info);
            txtView.setText(R.string.miami_info);
        } else if (rName.equals("punta cana")) {
            txtView.setText(R.string.puntacana_info);
        } else if (rName.equals("santa maria")) {
            txtView.setText(R.string.santamaria_info);
        }else if(rName.equals("los angeles")) {
            txtView.setText(R.string.losangeles_info);
        }

/*        Intent intent = new Intent(this, activity_reservation.class);
        intent.putExtra("ROOM_COUNT", index);
        startActivity(intent);*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        editor.putString("keyRooms", choice);
        editor.commit();

        showToast("Saved: " + choice);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showToast (String s1){
        Toast tst;
        tst = Toast.makeText(this, s1, Toast.LENGTH_LONG);
        tst.show();
    }
}
