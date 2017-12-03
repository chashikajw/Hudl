package hudlmo.interfaces.Video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.VidyoConnector;

import hudlmo.interfaces.loginpage.R;

public class VideoCoference extends AppCompatActivity implements VidyoConnector.IConnect{

    private VidyoConnector vc;
    private FrameLayout videoFrame;
    String token,username,room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_coference);

        Connector.SetApplicationUIContext(this);
        Connector.Initialize();
        videoFrame = (FrameLayout)findViewById(R.id.videoFrame);
    }
    //
    public void Start(View v) {
        vc = new VidyoConnector(videoFrame, VidyoConnector.VidyoConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 16, "", "", 0);
        vc.ShowViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());
    }

    public void Connect(View v) {
        String token = "cHJvdmlzaW9uAHVzZXIxQDE4ZjBiZC52aWR5by5pbwA2MzY3OTE4NTYzNgAAOWUyOTUxNzQzZDdhN2QyOWMxYzE1MWMxNjFjY2M2ODU0NGU2NTAzMzIwMTRkZTgyZjczYzVjN2EwZDI1NmRlNTQzMDBmNjQ3M2Q5NDEyNzcyMTA4NDQ3YWQ1MzJkYWI2";
        try {
            vc.Connect("prod.vidyo.io", token, "oshan", "room1", this);
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
