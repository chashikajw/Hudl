package hudlmo.interfaces.createMeeting;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;




import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import hudlmo.interfaces.loginpage.R;
import hudlmo.interfaces.mainmenu.Mainmenu;
import hudlmo.interfaces.registerPage.Register;


public class CreateMeeting extends AppCompatActivity implements View.OnClickListener {

    Button dateButton,timeButton,nextButton;
    EditText dateText,timeText,groupName,description,duration;
    private int day,month,year,hour,minutes;
    String format;
    Calendar currentTime;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mTimeSetListener;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        dateButton = (Button)findViewById(R.id.dateButton);
        timeButton = (Button)findViewById(R.id.timeButton);

        groupName = (EditText)findViewById(R.id.groupNameText);
        description = (EditText)findViewById(R.id.descriptionText);
        duration = (EditText)findViewById(R.id.durationText);
        dateText = (EditText)findViewById(R.id.dateText);
        timeText = (EditText)findViewById(R.id.timeText);

        dateButton.setOnClickListener ( this );
        timeButton.setOnClickListener ( this );

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Meeting");
        mProgress = new ProgressDialog(this);

        init ();

        //Date Picker
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;

                String months = "";
                switch (month){
                    case 1:months="Jan";break;
                    case 2:months="Feb";break;
                    case 3:months="Mar";break;
                    case 4:months="Apr";break;
                    case 5:months="May";break;
                    case 6:months="Jun";break;
                    case 7:months="Jul";break;
                    case 8:months="Aug";break;
                    case 9:months="Sept";break;
                    case 10:months="Oct";break;
                    case 11:months="Nov";break;
                    case 12:months="Dec";break;

                }
                String date =months + " " + day + " ," + year;
                dateText.setText(date);


            }
        };

        timeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


    }


    //Next button
    public void init(){
        nextButton = (Button)findViewById ( R.id.nextButton );
        nextButton.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String group_name = groupName.getText().toString().trim();
                String description_ = description.getText().toString().trim();
                String date_text = dateText.getText().toString().trim();
                String time_text = timeText.getText().toString().trim();
                String duration_ = duration.getText().toString().trim();

                //validation
                if(TextUtils.isEmpty(group_name)/*||TextUtils.isEmpty(duration_)||TextUtils.isEmpty(description_)*/){
                    Toast.makeText(CreateMeeting.this, "Fields are empty",Toast.LENGTH_LONG).show();
                }

                //insert data to database
                else {
                    mProgress.setMessage("Creating meeting....");
                    mProgress.show();

                    String userId = mAuth.getCurrentUser().getUid();

                    //mDatabase.child(group_name).setValue(userId);
                    final DatabaseReference currnt_userDB = mDatabase.child(userId).child("meetings").child("upcoming");
                    final DatabaseReference username_userDB = mDatabase.child(userId).child("username");

                    currnt_userDB.child("meetingName").setValue(group_name);
                    currnt_userDB.child("description").setValue(description_);
                    currnt_userDB.child("createdDate").setValue("vfdvdv");
                    currnt_userDB.child("sheduleDate").setValue("12133234343");

                    currnt_userDB.child("date").setValue(date_text);
                    currnt_userDB.child("time").setValue(time_text);

                    mProgress.dismiss();

                    //startActivity(new Intent(CreateMeeting.this, Mainmenu.class));

                    Intent detail = new Intent ( CreateMeeting.this,AddParticipants.class);
                    detail.putExtra("group_name", group_name);
                    detail.putExtra("description_",description_);
                    detail.putExtra("date_text", date_text);
                    detail.putExtra("time_text", time_text);
                    startActivity(detail);
                }


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


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        //set Calender to find date
        if (v==dateButton){
            java.util.Calendar cal = java.util.Calendar.getInstance();

            int year=cal.get(java.util.Calendar.YEAR);
            int month=cal.get(java.util.Calendar.MONTH);
            int day=cal.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog=new DatePickerDialog(CreateMeeting.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                    mDateSetListener,year,month,day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }

    }


}


