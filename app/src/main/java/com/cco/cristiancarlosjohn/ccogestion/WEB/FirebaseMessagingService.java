package com.cco.cristiancarlosjohn.ccogestion.WEB;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Activities.MainActivity;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by cristian.zapata on 27/08/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    private int id_not = 0;

    @Override
    //Qué hacer cuando el método ser recibe desde FCM
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("customKey"));
    }

    private void showNotification(String message, String customKey) {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

//        NotificationCompat.Builder groupBuilder =
//                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                        .setContentTitle(customKey)
//                        .setContentText("Evento")
//                        .setGroupSummary(true)
//                        .setGroup(Constantes.GROUP_TEST)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Evento"))
//                        .setContentIntent(pendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true)
                .setContentTitle(customKey)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentText(message)
                .setGroup(Constantes.GROUP_TEST)
                .setLights(Color.CYAN, 3000, 3000)
                .setSmallIcon(R.drawable.splash_logo)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        manager.notify(0, groupBuilder.build());
        manager.notify(id_not++,builder.build());
    }


}
