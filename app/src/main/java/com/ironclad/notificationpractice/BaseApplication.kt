package com.ironclad.notificationpractice

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class BaseApplication : Application() {
    private val channel1ID = "channel1"
    private val channel2ID = "channel2"
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                channel1ID,
                "Tutorial",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is for the tutorial for Notifications!"

            val channel2 = NotificationChannel(
                channel2ID,
                "Implementation",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "This is for the implementation for Notifications!"
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannels(listOf(channel1, channel2))
        }
    }
}