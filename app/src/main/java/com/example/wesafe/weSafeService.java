package com.example.wesafe;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.wesafe.notification_class.CHANNEL_ID;

public class weSafeService extends android.app.Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
//        String input = intent.getStringExtra("inputExtra") ;
        RemoteViews collapsedView = new RemoteViews(getPackageName(),
                R.layout.notification_layout);
        Intent notificationIntent = new Intent(this,MainActivity.class) ;
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("We Safe Service")
//                .setContentText(input)
                .setCustomContentView(collapsedView)
                .setSmallIcon(R.drawable.ic_wesafe)
                .setContentIntent(pendingIntent)
//                .setColor(2)
                .build() ;

        startForeground(1,notification);
        // do heavy work in thread
        //After work is done then we should call this
//        stopSelf();
        return START_NOT_STICKY ;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
