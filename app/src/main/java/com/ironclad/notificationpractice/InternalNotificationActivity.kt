package com.ironclad.notificationpractice

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.MediaSessionManager
import kotlinx.android.synthetic.main.activity_internal_notification.*

class InternalNotificationActivity : AppCompatActivity() {

    private lateinit var mediaSession: MediaSessionCompat

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_notification)

        mediaSession = MediaSessionCompat(this, "tag")

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

            val picture = BitmapFactory.decodeResource(resources, R.drawable.bell)

            val notification = NotificationCompat.Builder(this, getString(R.string.channel1))
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(picture)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(null)
                )
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

            notificationManager.notify(1, notification)
        }

        btnSendOnChannel2.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()

            val artwork = BitmapFactory.decodeResource(resources, R.drawable.bell)

            val notification = NotificationCompat.Builder(this, getString(R.string.channel2))
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle(title)
                .setLargeIcon(artwork)
                .addAction(R.drawable.ic_baseline_thumb_down, "Dislike", null)
                .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", null)
                .addAction(R.drawable.ic_baseline_pause_24, "Play/Pause", null)
                .addAction(R.drawable.ic_baseline_skip_next_24, "Next", null)
                .addAction(R.drawable.ic_baseline_thumb_up_24, "Like", null)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                        .setMediaSession(mediaSession.sessionToken)
                )
                .setSubText("Sub Text 1")
                .setContentText(message)
                .build()

            notificationManager.notify(2, notification)
        }
    }
}