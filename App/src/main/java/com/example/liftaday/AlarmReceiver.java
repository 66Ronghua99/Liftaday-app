package com.example.liftaday;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String channelId = "default";
    private static final String channelName = "default";
    NotificationChannel notificationChannel;
    NotificationCompat.Builder notification;
    NotificationManager manager;
    private static final int NOTIFICATION_FLAG = 40384;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("tag","broadcast received");
        if (intent.getAction().equals("com.example.liftaday.Notification_Alarm")) {
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setDescription("default");
                manager.createNotificationChannel(notificationChannel);
                notification = new NotificationCompat.Builder(context,channelId);
            }else {
                notification = new NotificationCompat.Builder(context,channelId);
            }
            notification.setAutoCancel(true);
//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_nobg);
            Intent intent1 = new Intent(context, SplashScreen.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            notification
                    .setSmallIcon(R.drawable.logo_nobg) // 设置状态栏中的小图片，尺寸一般建议在24×24
                    .setTicker("LiftADay") // 设置显示的提示文字
                    .setContentTitle("LiftADay") // 设置显示的标题
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentText("Kick start a brand new day!!") // 消息的详细内容
                    .setContentIntent(pendingIntent); // 关联PendingIntent
            manager.notify(NOTIFICATION_FLAG, notification.build());
        }
    }
}

//作者：LeiHolmes
//        链接：https://juejin.im/post/59f7f53c5188257ad639db3e
//        来源：掘金
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。