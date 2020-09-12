package com.ironclad.notificationpractice

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_internal_notification.*

class InternalNotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_notification)

        val notificationManager = NotificationManagerCompat.from(this)

        btnSendOnChannel1.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()

            val activityIntent = Intent(this, InternalNotificationActivity::class.java)
            val contentIntent = PendingIntent.getActivity(
                this,
                1605,
                activityIntent,
                0
            )
            val notification = NotificationCompat.Builder(this, getString(R.string.channel1))
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

            notificationManager.notify(1, notification)
        }

        btnSendOnChannel2.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val notification = NotificationCompat.Builder(this, getString(R.string.channel2))
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText(message)
                .build()

            notificationManager.notify(2, notification)
        }
    }
}