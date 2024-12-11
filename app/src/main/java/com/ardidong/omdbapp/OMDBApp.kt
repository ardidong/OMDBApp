package com.ardidong.omdbapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OMDBApp: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = "General"
            val descriptionText = "General Notification of the app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(Channel.GENERAL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}

object Channel {
    const val GENERAL_ID = "id_general"
}