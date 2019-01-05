package com.example.ideath.todoshka.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import com.example.ideath.todoshka.R;
import com.example.ideath.todoshka.activity.CreateTodoActivity;

public class TodoMessageService extends Service{

    public int onStartCommand(Intent intent,int flags,int startId) {
            String date = intent.getExtras().getString("date");
            String time  = intent.getExtras().getString("time");
            String thema  = intent.getExtras().getString("thema");
            long id = intent.getExtras().getLong("id");
            String Channel_iD = "Channels";
            NotificationChannel notificationChannel = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(Channel_iD, "Service",
                        NotificationManager.IMPORTANCE_HIGH);
            }
            assert date != null;
            assert time != null;
            assert thema!= null;

            NotificationManager notificationManager =
                             (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

             //Создание построителя уведомлений
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, Channel_iD)
                            .setSmallIcon(R.mipmap.note)
                            .setContentTitle(time+"\t"+Html.fromHtml("&#9830;")+"\t"+date)
                            .setContentText(thema)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[]{1000})
                            .setAutoCancel(true);

            Intent actionIntents = new Intent(this, CreateTodoActivity.class);
            actionIntents.putExtra("id",id);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        this,
                        (int)id,
                        actionIntents,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notificationManager.notify((int)id, builder.build());


        return START_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
