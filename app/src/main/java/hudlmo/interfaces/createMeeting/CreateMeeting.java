package hudlmo.interfaces.createMeeting;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;
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

    Button nextButton;
    EditText groupName,description,duration;
    TextView dateText,timeText,dateButton,timeButton;
    private int day,month,year,hour,minutes;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private static final String TAG = "CreateMeeting";
    private TextView mDisplayDate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        dateButton = (TextView) findViewById(R.id.dateButton);
        timeButton = (TextView) findViewById(R.id.timeButton);

        groupName = (EditText)findViewById(R.id.groupNameText);
        description = (EditText)findViewById(R.id.descriptionText);
        duration = (EditText)findViewById(R.id.durationText);
        dateText = (TextView) findViewById(R.id.dateText);
        timeText = (TextView)findViewById(R.id.timeText);

        dateText.setOnClickListener ( this );
        timeText.setOnClickListener ( this );

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Meeting");
        mProgress = new ProgressDialog(this);

        init ();

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


                    username_userDB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String username = dataSnapshot.getValue(String.class);
                            currnt_userDB.child("initiator").setValue(username);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mProgress.dismiss();

                    //startActivity(new Intent(CreateMeeting.this, Mainmenu.class));

                    Intent toy = new Intent ( CreateMeeting.this,AddParticipants.class);
                    startActivity ( toy );


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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        //set Calender to find date
        if (v==dateText){
            mDisplayDate=(TextView) findViewById(R.id.dateText);

            mDisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    int year=cal.get(java.util.Calendar.YEAR);
                    int month=cal.get(java.util.Calendar.MONTH);
                    int day=cal.get(java.util.Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog=new DatePickerDialog(CreateMeeting.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                            mDateSetListener,year,month,day);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month+1;
                    Log.d(TAG, "onDateSet: mm/dd/yyy:"+ year + "/"+ month + "/" + day+ "/");

                    String date =month + "/" + day + "/" + year;
                    mDisplayDate.setText(date);


                }
            };
        }

        //set calender to find time
        if (v==timeText){
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