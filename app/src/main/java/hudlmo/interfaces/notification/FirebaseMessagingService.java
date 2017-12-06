package hudlmo.interfaces.notification;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import hudlmo.interfaces.loginpage.R;

/**
 * Created by admin on 12/6/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String NotificationTitle = remoteMessage.getNotification().getTitle();
        String NotificatnMsg = remoteMessage.getNotification().getBody();


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle(NotificationTitle)
                        .setContentText(NotificatnMsg);


        int mNotificationId = (int)System.currentTimeMillis();

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
