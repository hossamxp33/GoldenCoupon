package com.codesroots.goldencoupon.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.codesroots.goldencoupon.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.JsonSyntaxException


import org.json.JSONObject
import java.lang.Exception
import java.lang.NullPointerException





class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            var myJson: JSONObject? = null
            var title: String? = ""
            var description: String? = ""
            var click = ""
            val id1 = remoteMessage.data["company_id"]
            if (remoteMessage.data != null) {
                myJson = JSONObject(remoteMessage.data as Map<*, *>?)
                title = remoteMessage.notification!!.title
                description = remoteMessage.notification!!.body
                click = myJson.optString("click_action").toString()
                sendNotification(title, description, click, id1)
            } else if (remoteMessage.notification != null) {
//                title = remoteMessage.getNotification().getTitle();
//                description = remoteMessage.getNotification().getBody();
//                click = remoteMessage.getNotification().getClickAction();
//                sendNotification(title, description, click, "0");
            }
        } catch (a: JsonSyntaxException) {
            a.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDeletedMessages() {}

    //    @Override
    //    public void onNewToken(String s) {
    //        super.onNewToken(s);
    //    }
    private fun sendNotification(title: String?, messageBody: String?, click: String, id: String?) {
        try {
            val intent = Intent(click)
            intent.putExtra("companyUserid", id!!.toInt())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT
            )
            val bitmap1 = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            val channelId = getString(R.string.default_notification_channel_id)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, channelId)
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



            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    title,
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

