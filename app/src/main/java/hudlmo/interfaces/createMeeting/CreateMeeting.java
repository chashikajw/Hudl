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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

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
    private String initatorID;
    private DatabaseReference mDatabase;
    private DatabaseReference Usernamedatabase;
    private ProgressDialog mProgress;

    private Toolbar mToolbar;

    private String username;
    private String  roomId;
    private String date_text;
    private String time_text;

    private long ShduletimeInMilliseconds;
    private String sheduleDateTime;


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mTimeSetListener;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateButton = (Button)findViewById(R.id.dateButton);
        timeButton = (Button)findViewById(R.id.timeButton);

        groupName = (EditText)findViewById(R.id.groupNameText);
        description = (EditText)findViewById(R.id.descriptionText);

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
                //Log.d(TAG, "onDateSet: mm/dd/yyy:"+ year + "/"+ month + "/" + day+ "/");
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
                    case 9:months="Sep";break;
                    case 10:months="Oct";break;
                    case 11:months="Nov";break;
                    case 12:months="Dec";break;

                }
                String date =months + " " + day + ", " + year;
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

    public void startTwo(View view) { startActivity(new Intent(this, Mainmenu.class)); }


    //Next button
    public void init(){
        nextButton = (Button)findViewById ( R.id.nextButton );
        nextButton.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String group_name = groupName.getText().toString().trim();
                String description_ = description.getText().toString().trim();
                date_text = dateText.getText().toString().trim();
                time_text = timeText.getText().toString().trim();

                roomId = Integer.toString((int) System.currentTimeMillis());


                //get initator userrname
                initatorID = mAuth.getCurrentUser().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(initatorID).child("username");


                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //username = (String)dataSnapshot.child("name").getValue();
                        username = (String)dataSnapshot.getValue();
                        

                        //create unique id for room
                        roomId =  roomId + username;

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                //convert shedule date time to timemills
                sheduleDateTime =   date_text+ " "+  time_text;

                long currentmilliseconds = System.currentTimeMillis();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd,yyyy HH:mm");

                try {
                    Date mDate = sdf.parse(sheduleDateTime);
                    ShduletimeInMilliseconds = mDate.getTime();


                } catch (ParseException e) {
                    e.printStackTrace();
                }



                //validation

                if(ShduletimeInMilliseconds <= System.currentTimeMillis()){
                    Toast.makeText(CreateMeeting.this, "set upcoming time to shedule",Toast.LENGTH_LONG).show();
                }


                //insert data to database
                else {
                    //mProgress.setMessage("Creating meeting....");
                    //mProgress.show();
                    Toast.makeText(CreateMeeting.this, "Creating Meeting",Toast.LENGTH_LONG).show();



                    final HashMap<String, String> meetingData = new HashMap<>();
                    meetingData.put("meetingName", group_name);
                    meetingData.put("createdDate", roomId);
                    meetingData.put("description", description_);
                    meetingData.put("initiator", username);
                    meetingData.put("sheduleDate", Long.toString(ShduletimeInMilliseconds));
                    meetingData.put("roomId", roomId);


                    String reqstUid = mAuth.getCurrentUser().getUid();

                    DatabaseReference storemeeting =  FirebaseDatabase.getInstance().getReference().child("Users").child(reqstUid).child("meetings").child("upcoming");

                    storemeeting.child(roomId).setValue(meetingData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });



                    storemeeting.child(roomId).setValue(meetingData ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    
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

                    Intent detail = new Intent ( CreateMeeting.this,AddParticipants.class);
                    detail.putExtra("MeetingName", group_name);
                    detail.putExtra("CreatedDate",roomId);
                    detail.putExtra("Description", description_);
                    detail.putExtra("Initiator", username);
                    detail.putExtra("SheduleDate", Long.toString(ShduletimeInMilliseconds));
                    detail.putExtra("RoomId", roomId);

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
 /*       if (v==timeButton){
            java.util.Calendar cal = java.util.Calendar.getInstance();
            int hour=cal.get(java.util.Calendar.HOUR);
            int miniutes=cal.get(java.util.Calendar.MINUTE);
            //int day=cal.get(java.util.Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(CreateMeeting.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                    mTimeSetListener,hour,miniutes);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
*/
/*        //set calender to find time
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
        }*/
    }


}