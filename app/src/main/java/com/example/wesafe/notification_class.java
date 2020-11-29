package com.example.wesafe;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class notification_class extends android.app.Application {
    public static final String CHANNEL_ID = "weSafeServiceChannel" ;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel() ;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "We Safe Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class) ;
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
