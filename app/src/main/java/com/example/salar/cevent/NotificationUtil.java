package com.example.salar.cevent;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by shayan4shayan on 4/4/18.
 */

public class NotificationUtil {

    public static final String ID = "uTask";
    public static final String NAME = "Remember";
    public static final int REMINDER_ID = 0x12000;

    public static void sendNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("رزرو غذای این هفته یادت نره!")
                .setContentTitle("uTask")
                .setLargeIcon(bitmap)
                .setContentIntent(getPendingIntent(context))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setPriority(100);
        Notification notification = builder.build();
        if (manager != null) {
            manager.notify(ID, REMINDER_ID, notification);
        }
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://dining.ut.ac.ir"));
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
