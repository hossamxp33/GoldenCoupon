package com.codesroots.goldencoupon.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codesroots.goldencoupon.R


class NotificationHelper {

    fun sendNotification(context: Context,title: String, messageBody: String, click: String, id: String) {
        try {
            val intent = Intent(click)
            intent.putExtra("companyUserid", id.toInt())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT
            )
            val bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo)
            val channelId: String = "1001"
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(context, channelId)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(messageBody)
                    )
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.drawable.logo)
                notificationBuilder.setLargeIcon(bitmap1)
            } else {
                notificationBuilder.setSmallIcon(R.drawable.logo)
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    title,
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager!!.createNotificationChannel(channel)
            }
            notificationManager!!.notify(0 /* ID of notification */, notificationBuilder.build())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}