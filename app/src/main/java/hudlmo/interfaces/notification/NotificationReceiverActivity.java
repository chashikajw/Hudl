package hudlmo.interfaces.notification;

import android.app.Activity;
import android.os.Bundle;

import hudlmo.interfaces.loginpage.R;

/**
 * Created by Shalini PC on 12/1/2017.
 */

public class NotificationReceiverActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_result);
    }
}
