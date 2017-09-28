package webrtc.notify.hudl;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by chashika on 9/5/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    @Override
    public  void onMessageReceived(RemoteMessage reomoteMessage){
        if(reomoteMessage.getData().size() >0){
            Map<String, String> payload = reomoteMessage.getData();

            showNotification(payload);
        }
    }

    private void showNotification(Map<String, String> payload){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(payload.get("username"));
        builder.setContentText(payload.get("email"));

        Intent resultIntent = new Intent(this, Notificationsend.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent
                .FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context
                .NOTIFICATION_SERVICE);

        notificationManager.notify(0,builder.build());
    }
}
