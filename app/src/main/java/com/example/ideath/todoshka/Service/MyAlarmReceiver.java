package com.example.ideath.todoshka.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;

public class MyAlarmReceiver extends WakefulBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                String date  = intent.getExtras().getString("date");
                String time  = intent.getExtras().getString("time");
                String thema  = intent.getExtras().getString("thema");
                long id = intent.getExtras().getLong("id");
                Intent serviceIntent = new Intent(context, TodoMessageService.class);
                serviceIntent.putExtra("date", date);
                serviceIntent.putExtra("time",time);
                serviceIntent.putExtra("thema",thema);
                serviceIntent.putExtra("id",id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent);
                }else {
                    context.startService(serviceIntent);
                }

        }
}