package com.example.amigo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class Notification_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String medName=intent.getStringExtra("medicineName");
        String pillsNum=intent.getStringExtra("pillsNum");
        String warning=intent.getStringExtra("warning");

        NotificationManager notificationManager = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(context,MedNotification.class);
        Bundle extras = new Bundle();
        extras.putString("medicineName",medName);
        extras.putString("pillsNum",pillsNum);
        extras.putString("warning",warning);
        repeating_intent.putExtras(extras);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.mednot)
                .setContentTitle("Medicine Notification")
                .setContentText("Notification To Take Medicine :)")
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());

    }


}