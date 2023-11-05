package eu.javimar.firebasepoc.features.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import eu.javimar.coachpoc.R
import eu.javimar.firebasepoc.MainActivity
import eu.javimar.firebasepoc.core.logd
import eu.javimar.firebasepoc.core.logi
import kotlin.random.Random

class MyFirebaseService: FirebaseMessagingService() {

    private val random = Random

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            logi("FCM Title, ${it.title}")
            logi("FCM Body, ${it.body}")
            sendNotification(it)
        }
    }

    private fun sendNotification(message: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val channelId = this.getString(R.string.defaul_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_google)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)
        manager.notify(random.nextInt(), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        logd("FCM new token: $token")
    }

    companion object {
        const val CHANNEL_NAME = "Firebase PoC channel"
    }
}