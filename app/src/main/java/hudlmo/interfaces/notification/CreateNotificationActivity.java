package hudlmo.interfaces.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import hudlmo.interfaces.loginpage.R;

/**
 * Created by Shalini PC on 12/1/2017.
 */

public class CreateNotificationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);
    }

    public void createNotification(View view){
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New Notificatio " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .addAction(R.drawable.logo ,"");
    }
}
