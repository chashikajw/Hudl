package hudlmo.interfaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.VidyoConnector;

import hudlmo.interfaces.loginpage.R;


public class VideoMain extends AppCompatActivity implements VidyoConnector.IConnect {

    private VidyoConnector vc;
    private FrameLayout videoFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_main);

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
        String token = "cHJvdmlzaW9uAGNoYXNoaWthancwMDdAZ21haWwuY29tQDE4ZjBiZC52aWR5by5pbwA2NDY3NDQ0NTExNQAAMThhZTEzZjFlMTUwM2JiZGE5NDk3YmNjYzhkZTk4ZGI3NWM0OGNmM2FhMDI1NDcwMDVmMjY0MmM4N2QxZWQwNGNmMDRkYzc5NjFiYTE0NzAzZTY4YThlYWE1NzQxMzk5";
        vc.Connect("prod.vidyo.io", token, "chashikajw007@gmail.com", "DemoRoom", this);
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
