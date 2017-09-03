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

    private static int id_not = 0;

    @Override
    //Qué hacer cuando el método ser recibe desde FCM
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getData().get("idradicado"),
                remoteMessage.getData().get("cod_evento"),
                remoteMessage.getData().get("via"),
                remoteMessage.getData().get("kilo_sector"));
    }

    private void showNotification(String idradicado,
                                  String cod_evento,
                                  String via,
                                  String kilo_sector) {
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
                .setContentTitle(cod_evento + " " + idradicado)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentText(via + "-" + kilo_sector)
                .setGroup(Constantes.GROUP_TEST)
                .setLights(Color.CYAN, 3000, 3000)
                .setSmallIcon(R.drawable.notification_icon2)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        manager.notify(0, groupBuilder.build());
        manager.notify(id_not++,builder.build());
    }


}
