package com.cco.cristiancarlosjohn.ccogestion.WEB;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.NotificationCompat;

import com.cco.cristiancarlosjohn.ccogestion.R;
import com.cco.cristiancarlosjohn.ccogestion.Tools.Constantes;
import com.cco.cristiancarlosjohn.ccogestion.UI.Activities.ConfirmActivity;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by cristian.zapata on 27/08/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    private static int id_not = 0;

    @Override
    //Qué hacer cuando el método ser recibe desde FCM
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getData().get(Constantes.RADICADO),
                remoteMessage.getData().get(Constantes.COD_EVENTO),
                remoteMessage.getData().get(Constantes.VIA),
                remoteMessage.getData().get(Constantes.SECTOR));
    }

    private void showNotification(String idradicado,
                                  String cod_evento,
                                  String via,
                                  String kilo_sector) {
        Intent i = new Intent(this,ConfirmActivity.class);
        i.putExtra(Constantes.RADICADO, idradicado);
        i.putExtra(Constantes.COD_EVENTO, cod_evento);
        i.putExtra(Constantes.VIA, via);
        i.putExtra(Constantes.SECTOR, kilo_sector);
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
                .setContentTitle(cod_evento + " " + "Radicado: " + idradicado)
                .setVibrate(new long[] { 500, 500, 500, 500, 500 })
                .setContentText(via + " - " + kilo_sector)
                .setLights(Color.CYAN, 3000, 3000)
                .setSmallIcon(R.drawable.notification_icon3)
                .setColor(Color.WHITE)
                .setContentIntent(pendingIntent);

        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        manager.notify(0, groupBuilder.build());
        manager.notify(id_not++,builder.build());
    }


}
