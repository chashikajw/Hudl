package hudlmo.interfaces.Video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.VidyoConnector;

import hudlmo.interfaces.loginpage.R;

public class VideoCoference extends AppCompatActivity implements VidyoConnector.IConnect{

    private VidyoConnector vc;
    private FrameLayout videoFrame;


    private  String roomID;
    private String username;
    private FirebaseAuth mAuth;
    private String token;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_coference);

        Connector.SetApplicationUIContext(this);
        Connector.Initialize();
        videoFrame = (FrameLayout)findViewById(R.id.videoFrame);

        Bundle bundle = getIntent().getExtras();
        roomID = bundle.getString("roomId");

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("username");
        token = "cHJvdmlzaW9uAGNqdzAwN0AxOGYwYmQudmlkeW8uaW8ANjM2Nzk5MDg3MjMAAGRlNDg0ZTBmYTIxMTI1YzgzNjg2Yzg3OWUwZThiOTJiMTY3Mjk2YWQzMWViMThhZjIxNTE3NmM2NzNlYTMwNzAwMzJlNzliMDg5MzMxZjFhNDU1NTgxZGVhNmNkN2Q4OA==";

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
