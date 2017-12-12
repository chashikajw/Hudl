package hudlmo.interfaces.Video;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.VidyoConnector;

import java.text.SimpleDateFormat;
import java.util.Date;

import hudlmo.interfaces.loginpage.R;

public class VideoCoference extends AppCompatActivity implements VidyoConnector.IConnect{

    private VidyoConnector vc;
    private FrameLayout videoFrame;
    private TextView timerCountdown;
    private TextView start_in;

    //show timer
    private long timeInMilliseconds;
    private String countdown;


    private  String roomID;
    private String username;
    private FirebaseAuth mAuth;
    private String token;

    private Button disconnctBtn;
    private Button connectBtn;
    private Button startBtn;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_coference);

        disconnctBtn = (Button) findViewById(R.id.disconnectbtn);
        connectBtn = (Button) findViewById(R.id.connectbtn);;
        startBtn = (Button) findViewById(R.id.startbtn);

        Connector.SetApplicationUIContext(this);
        Connector.Initialize();
        videoFrame = (FrameLayout)findViewById(R.id.videoFrame);

        Bundle bundle = getIntent().getExtras();
        roomID = bundle.getString("roomid");

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("username");
        token = "cHJvdmlzaW9uAGNoYXNoRmluYWxAMThmMGJkLnZpZHlvLmlvADYzNzMwMTI0NjQ4AAA4M2QwMWY3MGQ3Yjk4YmYyYmY5MjdlMmU2OTFiODliYjQ0OWJkYWNmYzdiYmVjMTY3NzdjNWNiYWY5ZjNlYjAwYmU5ZWRlNGM5OGY5MTMyMmU5MTc1N2VhNDA4YjM0ZTk=";

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uname = (String) dataSnapshot.getValue();
                username = uname;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        timerCountdown = (TextView)findViewById(R.id.user_single_timer);
        start_in =  (TextView)findViewById(R.id.start_in);

        //countdown to start meeting
        //setcountdown to show
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);
        System.out.println(sdf.format(resultdate));


        timeInMilliseconds = bundle.getLong("sheduletime");

        long times = timeInMilliseconds - yourmilliseconds;

        CountDownTimer timer=new CountDownTimer(times, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                timerCountdown.setText(String.format("%d:%d:%d",hours,minutes,seconds));
                startBtn.setEnabled(false);
                connectBtn.setEnabled(false);
                disconnctBtn.setEnabled(false);
            }
            public void onFinish() {
                timerCountdown.setVisibility(View.INVISIBLE);
                start_in.setVisibility(View.INVISIBLE);

                //button enabled when time is up
                startBtn.setEnabled(true);
                connectBtn.setEnabled(true);
                disconnctBtn.setEnabled(true);
            }
        };


        timer.start();


    }
    //
    public void Start(View v) {
        vc = new VidyoConnector(videoFrame, VidyoConnector.VidyoConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 16, "", "", 0);
        vc.ShowViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());
    }

    public void Connect(View v) {

        try {
            vc.Connect("prod.vidyo.io", token, username, roomID, this);
            Toast.makeText( VideoCoference.this, "connecting succesfully",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText( VideoCoference.this, "connection error",Toast.LENGTH_LONG).show();
        }

    }

    public void Disconnect(View v) {
        vc.Disconnect();
    }

    public void OnSuccess() {

    }

    public void OnFailure(VidyoConnector.VidyoConnectorFailReason vidyoConnectorFailReason) {

    }

    public void OnDisconnected(VidyoConnector.VidyoConnectorDisconnectReason vidyoConnectorDisconnectReason) {

    }
}
