package com.codesroots.goldencoupon.helper

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val default_notification_channel_id = "default"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        var myJson: JSONObject? = null
        val title = ""
        val description = ""
        val id1 = remoteMessage.data["company_id"]
        if (remoteMessage.notification != null){
            myJson = JSONObject(remoteMessage!!.data!! as Map<*, *>?)
           var click = myJson!!.optString("click_action").toString()
            val title =    remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            NotificationHelper().sendNotification(applicationContext,title!!,body!!,click,id1!!)

        }

    }



    override fun onNewToken(s: String) {
        super.onNewToken(s)
    }


}