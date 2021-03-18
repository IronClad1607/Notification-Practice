package com.ironclad.notificationpractice.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d("PUI", "From:${p0}")

        if (p0.data.isNotEmpty()) {
            Log.d("PUI", "Message data Payload: ${p0.data}")
        }

        p0.notification?.let {
            Log.d("PUI", "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(p0: String) {
        Log.d("PUI", "Refreshed Token: $p0")
    }
}