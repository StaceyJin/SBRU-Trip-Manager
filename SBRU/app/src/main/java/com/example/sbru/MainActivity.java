package com.example.sbru;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener , View.OnTouchListener{
    private Intent intent;

    private Button search;
    private Button save;
    private Button helpBtn;

    private TextView txtViewDate;
    private Spinner spinner;
    private TextView palmtree;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String TAG = "MainActivity";
    private static final String DEBUG_TAG = "Velocity";
    private VelocityTracker mVelocityTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search);
        search.setOnClickListener(MainActivity.this);

        save = findViewById(R.id.buttonSaveDp);
        save.setOnClickListener(MainActivity.this);

        intent = new Intent(MainActivity.this, Resort.class);

        txtViewDate = findViewById(R.id.txtViewDate);
        palmtree = findViewById(R.id.palmtree);
        palmtree.setOnTouchListener(MainActivity.this);

        helpBtn = findViewById(R.id.btnHelp);

        helpBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                if (me.getAction()==MotionEvent.ACTION_DOWN) {
                    onHelpDisplay();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.btnShowdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences("tripDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        spinner = findViewById(R.id.selectNumP);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.numPeople, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        /*        //Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG).show();
         */        editor.putString("keyPeople", choice);
        editor.commit();
        showToast("Saved: " + choice);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSaveDp) {
            String date = txtViewDate.getText().toString();
            String numPeople = spinner.getSelectedItem().toString();
            if (TextUtils.isEmpty(date) || TextUtils.isEmpty(numPeople)) {
                showToast("Must select date and number of people");
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("keyDate", date);
                editor.putString("keyPeople", numPeople);
                editor.commit();
                checkPrefs();
                showToast("Data stored successfully");
            }
        }
        if (v.getId() == R.id.search) {
            startActivity(intent);
        }
    }

    public void onHelpDisplay() {
        AlertDialog.Builder alBuilder;
        alBuilder=new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
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

    public void showDatePickerDialog() {
        DatePicker datePicker = new DatePicker(this);
        int currentDay = datePicker.getDayOfMonth();
        int currentMonth = datePicker.getMonth();
        int currentYear = datePicker.getYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtViewDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, currentYear, currentMonth, currentDay);
        datePickerDialog.show();
    }

    public void showToast(String s1) {
        Toast tst;
        tst = Toast.makeText(this, s1, Toast.LENGTH_LONG);
        tst.show();
    }

    public void checkPrefs() {
        if (sharedPreferences.contains("keyDate") && sharedPreferences.contains("keyPeople")) {
            String rDate = sharedPreferences.getString("keyDate", "data not found");
            String rPeople = sharedPreferences.getString("keyPeople", "data not found");
            Log.d(TAG, "Success: date: " + rDate + " people: " + rPeople);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //*
        Context context = getApplicationContext();
        CharSequence text = "Help";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        //*
        startActivity(new Intent(MainActivity.this,Pop.class));
        return false;
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the
                    // velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                Log.d("", "X velocity: " + mVelocityTracker.getXVelocity(pointerId));
                Log.d("", "Y velocity: " + mVelocityTracker.getYVelocity(pointerId));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker.recycle();
                break;
        }
        return true;
    }
*/
}