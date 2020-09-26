package com.ironclad.notificationpractice.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.ironclad.notificationpractice.InternalNotificationActivity.Companion.messages
import com.ironclad.notificationpractice.InternalNotificationActivity.Companion.sendMessageNotification
import com.ironclad.notificationpractice.Message

class DirectReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val replyText = remoteInput.getCharSequence("keyReply")
            val answer = Message(replyText!!, null)
            messages.add(answer)

            sendMessageNotification(context!!)
        }
    }
}