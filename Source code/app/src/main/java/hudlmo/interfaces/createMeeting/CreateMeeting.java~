package com.example.sanu.webrtc;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TimePicker;

public class CreateMeeting extends AppCompatActivity implements OnClickListener {

    Button dateButton,timeButton,nextButton;
    EditText dateText,timeText;
    private int day,month,year,hour,minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        dateButton = (Button)findViewById(R.id.dateButton);
        timeButton = (Button)findViewById(R.id.timeButton);
        dateText = (EditText)findViewById(R.id.dateText);
        timeText = (EditText)findViewById(R.id.timeText);
        dateButton.setOnClickListener ( this );
        timeButton.setOnClickListener ( this );
        init ();//next button

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    //Next button
    public void init(){
        nextButton = (Button)findViewById ( R.id.nextButton );
        nextButton.setOnClickListener ( new OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent ( CreateMeeting.this,AddParticipants.class);
                startActivity ( toy );
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        //set Calender to find date
        if (v==dateButton){
            final Calendar c = Calendar.getInstance ();
            day = c.get ( Calendar.DAY_OF_MONTH );
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog ( this, new DatePickerDialog.OnDateSetListener () {


                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateText.setText ( dayOfMonth+"/"+(month+1)+"/"+year );
                }
            }
                    ,day,month,year);
            datePickerDialog.show ();
        }

        //set calender to find time
        if (v==timeButton){
            final Calendar c = Calendar.getInstance ();
            hour = c.get ( Calendar.HOUR_OF_DAY );
            minutes = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog ( this, new TimePickerDialog.OnTimeSetListener () {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeText.setText ( hourOfDay+":"+minute );
                }
            },hour,minutes,false);
            timePickerDialog.show ();
        }
    }


}


