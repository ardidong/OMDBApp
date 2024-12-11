package com.ardidong.omdbapp.data.service.notification

import android.app.NotificationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.ardidong.omdbapp.Channel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(
            message.notification?.title.orEmpty(),
            message.notification?.body.orEmpty()
        )
    }

    private fun showNotification(
        title: String,
        message: String
    ) {
        val builder = NotificationCompat.Builder(this, Channel.GENERAL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(1, builder) // Always use the same ID
    }

    companion object {
        const val TOPIC = "general"
    }
}