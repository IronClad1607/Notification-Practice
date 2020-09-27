package com.ironclad.notificationpractice

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Person
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.graphics.drawable.IconCompat
import com.ironclad.notificationpractice.receivers.DirectReplyReceiver
import kotlinx.android.synthetic.main.activity_internal_notification.*

class InternalNotificationActivity : AppCompatActivity() {

    private lateinit var mediaSession: MediaSessionCompat

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_notification)

        mediaSession = MediaSessionCompat(this, "tag")

        messages.add(Message("Good Morning!", "Jim"))
        messages.add(Message("Hello", null))
        messages.add(Message("Hi", "Sidhi"))
        messages.add(
            Message(
                "Hey",
                "JJ"
            )
        )
        messages.add(Message("Good Morning", "Satvik"))
        messages.add(Message("Good Morning!", null))
        messages.add(Message("How are you?", "Jim"))
        messages.add(
            Message(
                "I'm Fine",
                "JJ"
            )
        )


        val notificationManager = NotificationManagerCompat.from(this)

        btnSendOnChannel3.setOnClickListener {
            sendMessageNotification(this)
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

        btnSendOnChannel4.setOnClickListener {
            val progressMax = 100;
            val notification = NotificationCompat.Builder(this, "channel3")
                .setSmallIcon(R.drawable.ic_baseline_pause_24)
                .setContentTitle("Download")
                .setContentText("Download in Progress")
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setProgress(progressMax, 0, true)


            notificationManager.notify(3, notification.build())

            Thread(Runnable {
                SystemClock.sleep(2000)
                for (progress in 0..progressMax step 20) {
//                    notification.setProgress(progressMax, progress, false)
//                    notificationManager.notify(3, notification.build())
                    SystemClock.sleep(1000)
                }

                notification.setContentText("Download finished")
                    .setOngoing(false)
                    .setProgress(0, 0, false)

                notificationManager.notify(3, notification.build())
            }).start()
        }

    }

    companion object {
        val messages = ArrayList<Message>()

        @SuppressLint("RestrictedApi")
        @RequiresApi(Build.VERSION_CODES.P)
        fun sendMessageNotification(context: Context) {
            val activityIntent = Intent(context, InternalNotificationActivity::class.java)
            val contentIntent = PendingIntent.getActivity(
                context,
                1605,
                activityIntent,
                0
            )

            val remoteInput = RemoteInput.Builder("keyReply")
                .setLabel("Your Message ...")
                .build()

            val replyIntent = Intent(context, DirectReplyReceiver::class.java)
            val replyPendingIntent = PendingIntent.getBroadcast(context, 1605, replyIntent, 0)

            val replyAction =
                NotificationCompat.Action.Builder(
                    R.drawable.ic_reply,
                    "Reply",
                    replyPendingIntent
                ).addRemoteInput(remoteInput)
                    .build()


            val messagingStyle =
                NotificationCompat.MessagingStyle(
                    androidx.core.app.Person.Builder().setName("Me")
                        .setIcon(
                            IconCompat.createFromIcon(
                                context,
                                Icon.createWithResource(context, R.drawable.ic_launcher_background)
                            )
                        ).build()
                )
            messagingStyle.conversationTitle = "Group Chat"



            for (message in messages) {
                val person = androidx.core.app.Person.Builder()
                    .setName(message.sender)
                    .build()
                val notificationMessage = NotificationCompat.MessagingStyle.Message(
                    message.text,
                    message.timestamp,
                    person
                )
                messagingStyle.addMessage(notificationMessage)
            }

            val notification = NotificationCompat.Builder(context, "channel1")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setStyle(messagingStyle)
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(1, notification)
        }
    }
}