package com.satyasetu.app.data.remote

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.satyasetu.app.SatyaSetuApp
import com.satyasetu.app.ui.main.MainActivity

/**
 * Firebase Cloud Messaging service for push notifications
 */
class SatyaSetuMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: remoteMessage.data["title"] ?: "SatyaSetu"
        val body = remoteMessage.notification?.body ?: remoteMessage.data["body"] ?: ""
        val channel = remoteMessage.data["channel"] ?: SatyaSetuApp.CHANNEL_GENERAL

        showNotification(title, body, channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send token to server if needed for targeted notifications
    }

    private fun showNotification(title: String, body: String, channel: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, channel)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
