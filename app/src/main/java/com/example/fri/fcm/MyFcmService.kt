package com.example.fri.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.fri.MainActivity
import com.example.fri.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "MyFcmService"

class MyFcmService : FirebaseMessagingService() {

    companion object {
        fun logToken() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Getting FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                Log.d(TAG, "FCM registration token: ${task.result}")
            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MyFcmService instance created")
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM registration token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement this method to send token to your app server.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Message received: ${remoteMessage.messageId}")

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message notification title: \"${it.title}\", body: \"${it.body}\"")
        }

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        // If you intend on generating your own notifications as a result of a received FCM message,
        // here is where you should do it...
        if (!isSilentMessage(remoteMessage)) {
            showNotification(extractTitle(remoteMessage), extractBody(remoteMessage))
        } else {
            // TODO: Handle within 10 seconds
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private fun showNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntentflags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_ONE_SHOT
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentflags)

        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setColor(ContextCompat.getColor(this, R.color.colorAccent))
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since Android 8.0 (API level 26) notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun isSilentMessage(remoteMessage: RemoteMessage): Boolean {
        return remoteMessage.data["silent"].toBoolean()
    }

    private fun extractTitle(remoteMessage: RemoteMessage): String {
        return remoteMessage.notification?.title ?: remoteMessage.data["title"] ?: resources.getString(R.string.app_name)
    }

    private fun extractBody(remoteMessage: RemoteMessage): String {
        return remoteMessage.notification?.body ?: remoteMessage.data["body"] ?: ""
    }
}